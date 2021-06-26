package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.Estado
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mis_servicios_detalle.*

class MisServiciosDetalleActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_servicios_detalle)
        if (intent.hasExtra(ID)) {
            verServicio(intent.extras!!.getString(ID, ""))
        } else {
            Toast.makeText(this@MisServiciosDetalleActivity,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG).show()
        }
        btn_cancel_servicio.setOnClickListener {
            cancelarServicio(cardInfoId.text.toString())
        }


    }

    private fun cancelarServicio(id:String) {
        db.child("Service").child(id).child("estado").setValue(Estado.CANCELADO.ordinal)
            .addOnCompleteListener {
            Toast.makeText(
                this,
                "Tu Servicio ha sido cancelado exitosamente",
                Toast.LENGTH_LONG
            )
                .show()

            irMisServiciosConfirmadosActivity()
        }
    }

    private fun irMisServiciosConfirmadosActivity() {
        val serviceActivity = Intent(this, MisServiciosConfirmadosActivity::class.java)

        this.startActivity(serviceActivity)
        this@MisServiciosDetalleActivity.finish()
        startActivity(serviceActivity)
    }

    private fun verServicio(id:String){
        db.child("Service").child(id).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                detalle_activity_cantidad_de_bolsas_amarillo.text=snapshot.child("envasesPlasticos").value.toString()
                detalle_activity_cantidad_bolsas_verdes.text=snapshot.child("envasesVidrio").value.toString()
                detalle_activity_cantidad_de_bolsas_azul.text=snapshot.child("envasesCarton").value.toString()
                detalle_activity_cardInfo_date.text=snapshot.child("date").value.toString()
                detalle_activity_cardInfo_direccion.text=snapshot.child("address").value.toString()
                detalle_activity_turno.text=snapshot.child("time").value.toString()
                cardInfoId.text=snapshot.key
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MisServiciosDetalleActivity,
                    "No se encontraron los datos especificados",Toast.LENGTH_LONG).show()
            }


        })

    }
companion object{
 val ID:String="ID"
}
}