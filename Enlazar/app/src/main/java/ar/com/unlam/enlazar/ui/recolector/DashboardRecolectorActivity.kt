package ar.com.unlam.enlazar.ui.recolector

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.LoginActivity
import ar.com.unlam.enlazar.ui.vecino.DashboardUserActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard_recolector.*
import kotlinx.android.synthetic.main.activity_dashboard_usuario.*

class DashboardRecolectorActivity : AppCompatActivity() {

    var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_recolector)
        if (intent.hasExtra(DashboardUserActivity.IDKEY)) {
            userId = intent.extras!!.getString(DashboardUserActivity.IDKEY, "").toString()
        }

        setCardClickListeners()
        toolbar()
    }

    private fun setCardClickListeners() {
        cardView_ir_mis_servicios.setOnClickListener {
            val intent = Intent(this, ServiciosRecolectorRutaActivity::class.java)
            startActivity(intent)
        }

        cardView_ir_hist_mis_servicios.setOnClickListener {
            val intent = Intent(this, ServiciosFinalizadosRecolectorActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recolector, menu)
        //menu?.findItem(R.id.mis_direcciones_user)?.setVisible(false)
        toolbar_DashboardRecolector.setNavigationIcon(null)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.mis_canjes_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.invita_amigos_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.guardado_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }

            R.id.mi_cuenta_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.logout_rec -> {
                logOut()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun logOut() {
        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE).edit()
        pref.clear()
        pref.apply()
        FirebaseAuth.getInstance().signOut()
        val mainIntent = Intent(this, LoginActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    fun toolbar() {
        setSupportActionBar(toolbar_DashboardRecolector)
        var ab: ActionBar? = supportActionBar
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.menu)
            ab.setDisplayHomeAsUpEnabled(true)
        }

    }
}