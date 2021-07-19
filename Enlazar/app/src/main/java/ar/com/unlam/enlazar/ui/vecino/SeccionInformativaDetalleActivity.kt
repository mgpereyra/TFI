package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.TipoConsejo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalle_card_info_detalle.*

class SeccionInformativaDetalleActivity : AppCompatActivity() {
    var id: Long = 0
    private val db = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_card_info_detalle)
        btnVolver_a_canjes.setOnClickListener { this@SeccionInformativaDetalleActivity.finish() }
        if (intent.hasExtra(ID)) {
            verConsejo(intent.extras!!.getString(ID, ""))
        } else {
            Toast.makeText(
                this@SeccionInformativaDetalleActivity,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun verConsejo(id: String) {
        db.child("Advice").child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cargarImagen(snapshot.child("uri").value.toString())
                title_detalle_Advice.text=snapshot.child("title").value.toString()
                content_detalle_Advice.text=snapshot.child("content").value.toString()
                setTextTipoConsejo(snapshot.child("tipe").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SeccionInformativaDetalleActivity,
                    getString(R.string.valor_perdido), Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun cargarImagen(img: String) {
        Picasso.get()
            .load(img)
            .into(picture_detalle_Advice)

    }
    private fun setTextTipoConsejo(tipo_consejo:String){

        when (tipo_consejo){
            TipoConsejo.COMO_RECICLAR_BIEN.toString()->labelTipoDeInfo.setText(R.string.como_reciclar)
            TipoConsejo.CONSEJO_DE_LA_SEMANA.toString()->labelTipoDeInfo.setText(R.string.consejo_semana)
            TipoConsejo.ECOINFORME.toString()->labelTipoDeInfo.setText(R.string.eco_info)
        }
    }

    companion object {
        val ID: String = "id"
    }
}