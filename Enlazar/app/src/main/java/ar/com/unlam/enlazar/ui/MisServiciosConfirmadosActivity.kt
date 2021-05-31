package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_mis_servicios.*

class MisServiciosConfirmadosActivity : AppCompatActivity() {
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