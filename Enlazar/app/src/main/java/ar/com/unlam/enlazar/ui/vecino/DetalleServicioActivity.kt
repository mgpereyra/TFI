package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_detalle_servicio.*

class DetalleServicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_servicio)

        btn_nuevo_servicio.setOnClickListener {
            val intent: Intent = Intent(this, NuevoServicioActivity::class.java)
            startActivity(intent)
        }
    }
}