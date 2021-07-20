package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalle_canje_item.*
import kotlinx.coroutines.launch

class DetalleCanjeItemActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference
    private val id = FirebaseAuth.getInstance().currentUser!!.uid
    var im = ""
    var p = 0
    val detalleCanjeViewmodel: DetalleCanjeItemViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_canje_item)
        if (intent.hasExtra(ID_CANJE)) {
            verItem(intent.extras!!.getString(ID_CANJE, ""))
        } else {
            Toast.makeText(
                this@DetalleCanjeItemActivity,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG
            ).show()
        }
        getPuntos(id)
        btnVolver_de_canjes.setOnClickListener {
            finish()
        }

        btn_canjear_item.setOnClickListener {
            //canjearItem()

            canjearItem2(
                costo_cupon_detalle.text.toString().toInt(),
                cantidad_diponible.text.toString().toInt(),
                cardInfoIdDetalleItem.text.toString(),
                cupon_titulo_detalle.text.toString(),
                description_item.text.toString(),
                im, id
            )
        }
    }

    private fun canjearItem2(
        costo: Int,
        cantidad: Int,
        id_item_detalle: String,
        cuponTitulo: String,
        descripcion: String,
        im: String,
        id: String
    ) {
        if (cantidad > 0) {
            if (costo <= detalleCanjeViewmodel.puntos.value!!.toInt()) {
                val restantes = detalleCanjeViewmodel.puntos.value?.minus(costo)
                var cant = cantidad - 1
                var cuponId = db.push().key.toString()
                var cupon = CuponCanje(
                    cuponId,
                    id_item_detalle,
                    cuponTitulo,
                    descripcion,
                    im, false
                )
                db.child("User").child(id).child("puntos").setValue(restantes)
                db.child("Item").child(id_item_detalle).child("amount").setValue(cant)
                db.child("User").child(id).child("Cupon").child(cuponId).setValue(cupon)
                Toast.makeText(this,"Se ha realizado el caje correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {

                Toast.makeText(this,"No tienes puntos suficientes para el producto deseado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, "No hay stock disponible", Toast.LENGTH_SHORT).show()
        }

    }

    private fun canjearItem() {
        detalleCanjeViewmodel.canjearItem(
            costo_cupon_detalle.text.toString().toInt(),
            cantidad_diponible.text.toString().toInt(),
            cardInfoIdDetalleItem.text.toString(),
            cupon_titulo_detalle.text.toString(),
            description_item.text.toString(),
            im, id
        )
        /*  var restantes=p-costo_cupon_detalle.text.toString().toInt()
          if (restantes >= 0 && cantidad_diponible.text.toString().toInt()>0) {
              var cantidad=cantidad_diponible.text.toString().toInt()-1
              var cuponId = db.push().key.toString()
              var cupon = CuponCanje(
                  cuponId,
                  id_item_detalle.text.toString(),
                  cupon_titulo_detalle.text.toString(),
                  description_item.text.toString(),
                  im, "false"
              )
              db.child("User").child(id).child("puntos").setValue(restantes)
              db.child("Item").child(id_item_detalle.text.toString()).child("amount").setValue(cantidad)
              db.child("User").child(id).child("Cupon").child(cuponId).setValue(cupon).addOnCompleteListener {
                  Toast.makeText(
                      this,
                      "Tu Canjeo ha sido registrado correctamente",
                      Toast.LENGTH_LONG
                  )
                      .show()
              }


          } else {
              Toast.makeText(
                  this,
                  "Puntos insuficientes o cupones agotados",
                  Toast.LENGTH_LONG
              )
                  .show()
          }*/
        finish()
    }

    private fun getPuntos(idUser: String) {
        detalleCanjeViewmodel.getPuntosUsuario(idUser)
        detalleCanjeViewmodel.puntos.observe(this, { observePoints(it) })
        //p = detalleCanjeViewmodel.puntos.value!!
        /*db.child("User").child(idUser).child("puntos")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    p = snapshot.value.toString().toInt()


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })*/

    }

    private fun observePoints(it: Int) {
        p = it

    }

    private fun verItem(id_item: String) {
        detalleCanjeViewmodel.verItem(id_item)
        detalleCanjeViewmodel.itemLiveData.observe(this, {
            setObservers(it)

        })
        /*  db.child("Item").child(id_item).addValueEventListener(object : ValueEventListener {
              override fun onDataChange(snapshot: DataSnapshot) {
                  id_item_detalle.text = snapshot.child("id").value.toString()
                  cupon_titulo_detalle.text = snapshot.child("title").value.toString()
                  costo_cupon_detalle.text = snapshot.child("pointsCost").value.toString()
                  description_item.text = snapshot.child("description").value.toString()
                  cargarImagen(
                      snapshot.child("image").value.toString(),
                      snapshot.child("imageCode").value.toString()
                  )
                  cantidad_diponible.text = snapshot.child("amount").value.toString()
                  cardInfoId.text = snapshot.key
              }

              override fun onCancelled(error: DatabaseError) {
                  TODO("Not yet implemented")
              }
          })*/
    }

    private fun setObservers(s: Item) {
        cargarImagen(s.image, s.imageCode)
        cupon_titulo_detalle.setText(s.title)
        cantidad_diponible.setText(s.amount.toString())
        costo_cupon_detalle.setText(s.pointsCost.toString())
        description_item.setText(s.description)
        cardInfoIdDetalleItem.setText(s.id)


    }

    private fun cargarImagen(img: String, qr: String) {
        im = qr
        Picasso.get()
            .load(img)
            .error(R.drawable.error)
            .into(codigo)
    }

    companion object {
        var ID_CANJE = "ID"
    }
}