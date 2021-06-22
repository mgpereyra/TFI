package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_mis_servicios.*

class MisServiciosConfirmadosActivity : AppCompatActivity() {
    private val db= FirebaseDatabase.getInstance().reference
     var id= FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_servicios)
        servicios_pendientes.setOnClickListener {

            //otra pantalla o una query que actualice la lista a las que estan pendientes?
            val intent: Intent = Intent(this, MisServiciosPendientesActivity::class.java)
            startActivity(intent)

        }
        servicios_confirmados.setOnClickListener {
            //query para servicios confirmados

        }



    }


}