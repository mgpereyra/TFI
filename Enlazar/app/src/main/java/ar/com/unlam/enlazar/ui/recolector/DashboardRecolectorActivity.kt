package ar.com.unlam.enlazar.ui.recolector

import android.content.Context
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

  /*      val bundle = intent.extras
        val idUser = bundle?.getString("idUser")
        val email = bundle?.getString("email")
        val typeUser = bundle?.getInt("typeUser")
        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE).edit()
        pref.putString("idUser", idUser)
        pref.putString("email", email)
        pref.putInt("typeUser", typeUser!!)
        pref.apply()*/

        cardView_ir_mis_servicios.setOnClickListener {
            val intent: Intent = Intent(this, ServiciosRecolectorRutaActivity::class.java)
            startActivity(intent)
        }

        cardView_ir_hist_mis_servicios.setOnClickListener {

            Toast.makeText(this,"Proximamente", Toast.LENGTH_SHORT).show()


        }

    }
}