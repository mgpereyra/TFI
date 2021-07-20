package ar.com.unlam.enlazar.ui.vecino

import android.app.Application
import android.content.ContentProvider
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetalleCanjeItemViewModel() : ViewModel() {
    private val id = FirebaseAuth.getInstance().currentUser!!.uid
    private val db = FirebaseDatabase.getInstance().reference
    var puntos = MutableLiveData<Int>()
    var itemLiveData = MutableLiveData<Item>()

    /*  init {
          getPuntosUsuario(id)
      }*/

    fun getPuntosUsuario(id: String) {
        viewModelScope.launch {
            db.child("User").child(id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        puntos.value = snapshot.child("puntos").value.toString().toInt()


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Cancel", error.toString())
                    }

                })

        }
    }

    fun canjearItem(
        costo: Int,
        cantidad: Int,
        id_item_detalle: String,
        cuponTitulo: String,
        descripcion: String,
        im: String,
        id: String
    ) {
        if (cantidad > 0) {
            if (costo <= puntos.value!!.toInt()) {
                viewModelScope.launch {
                    val restantes = puntos.value?.minus(costo)
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

                }
            } else {
      /*          Toast.makeText(
                    this,
                    "No tienes puntos suficientes para el producto deseado",
                    Toast.LENGTH_SHORT
                ).show()*/
            }
        } else {
            //  Toast.makeText(this, "No hay stock disponible", Toast.LENGTH_SHORT).show()
        }


    }

    fun verItem(id_item: String) {
        viewModelScope.launch {
            db.child("Item").child(id_item).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var model = snapshot.getValue(Item::class.java)
                    itemLiveData.value = model
                    /*  id_item_detalle.text = snapshot.child("id").value.toString()
                      cupon_titulo_detalle.text = snapshot.child("title").value.toString()
                      costo_cupon_detalle.text = snapshot.child("pointsCost").value.toString()
                      description_item.text = snapshot.child("description").value.toString()
                      cargarImagen(
                          snapshot.child("image").value.toString(),
                          snapshot.child("imageCode").value.toString()
                      )
                      cantidad_diponible.text = snapshot.child("amount").value.toString()
                      cardInfoId.text = snapshot.key*/
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel", error.toString())
                }
            })
        }
    }

}
