package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        cardView_mis_servicios.setOnClickListener{
    val intent: Intent = Intent(this, misServicios::class.java)
    startActivity(intent)
}


        cardView_proximo_servicio.setOnClickListener{
            val intent: Intent = Intent(this, DetalleServicioActivity::class.java)
            startActivity(intent)
        }
    }

}