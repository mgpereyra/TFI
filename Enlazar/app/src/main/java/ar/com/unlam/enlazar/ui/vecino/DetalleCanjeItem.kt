package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CuponCanje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalle_canje_item.*
import kotlinx.android.synthetic.main.activity_mis_servicios_detalle.*
import kotlinx.android.synthetic.main.activity_mis_servicios_detalle.cardInfoId

class DetalleCanjeItem : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference
    private val id = FirebaseAuth.getInstance().currentUser!!.uid
    var im = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_canje_item)
        if (intent.hasExtra(ID_CANJE)) {
            verItem(intent.extras!!.getString(ID_CANJE, ""))
        } else {
            Toast.makeText(
                this@DetalleCanjeItem,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG
            ).show()
        }

        btn_canjear_item.setOnClickListener {
            canjearItem()

        }
    }

    private fun canjearItem() {
        var puntos = getPuntos(id)

        if ((puntos - costo_cupon_detalle.text.toString().toInt()) >= 0 && cantidad_diponible.text.toString().toInt()>0) {
            var cantidad=cantidad_diponible.text.toString().toInt()-1
            var cuponId = db.push().key.toString()
            var cupon = CuponCanje(
                cuponId,
                id_item_detalle.text.toString(),
                cupon_titulo_detalle.text.toString(),
                description_item.text.toString(),
                im, false
            )
            db.child("User").child(id).child("Cupones").setValue(cupon).addOnCompleteListener {
                Toast.makeText(
                    this,
                    "Tu Canjeo ha sido registrado correctamente",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            db.child("Item").child(ID_CANJE).child("amount").setValue(cantidad)


        } else {
            Toast.makeText(
                this,
                "Puntos insuficientes o cupones agotados",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private fun getPuntos(idUser: String): Int {
        var p = 0
        db.child("User").child(idUser).child("puntos")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    p = snapshot.value.toString().toInt()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        return p
    }

    private fun verItem(id_item: String) {
        db.child("Item").child(id_item).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                id_item_detalle.text = id_item
                cupon_titulo_detalle.text = snapshot.child("title").value.toString()
                costo_cupon_detalle.text = snapshot.child("pointCost").value.toString()
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
        })
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