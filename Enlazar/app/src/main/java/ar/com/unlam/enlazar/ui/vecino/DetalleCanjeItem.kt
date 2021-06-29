package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_canje_item)
        if (intent.hasExtra(ID_CANJE)) {
            verItem(intent.extras!!.getString(ID_CANJE, ""))
        } else {
            Toast.makeText(this@DetalleCanjeItem,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG).show()
        }

    }

    private fun verItem(id_item: String) {
        db.child("Item").child(id_item).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               cupon_titulo_detalle.text=snapshot.child("title").value.toString()
                costo_cupon_detalle.text=snapshot.child("pointCost").value.toString()
                description_item.text=snapshot.child("description").value.toString()
                cargarImagen(snapshot.child("image").value.toString())
                cantidad_diponible.text=snapshot.child("amount").value.toString()
                cardInfoId.text=snapshot.key
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun cargarImagen(img:String) {
        Picasso.get()
            .load(img)
            .error(R.drawable.error)
            .into(codigo)
    }

    companion object  {
var ID_CANJE="ID"
    }
}