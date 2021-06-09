package ar.com.unlam.enlazar.ui.recolector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_dashboard_recolector.*

class DashboardRecolectorActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_recolector)


        cardView_ir_mis_servicios.setOnClickListener {
            val intent: Intent = Intent(this, ServiciosRecolectorRutaActivity::class.java)
            startActivity(intent)
        }

        cardView_ir_hist_mis_servicios.setOnClickListener {

            Toast.makeText(this,"Proximamente", Toast.LENGTH_SHORT).show()


        }

    }
}