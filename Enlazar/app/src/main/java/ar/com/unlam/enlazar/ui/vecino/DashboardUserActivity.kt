package ar.com.unlam.enlazar.ui.vecino

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard_usuario.*

class DashboardUserActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().getReference()

    enum class ProviderType{
        BASIC
    }
    var userId:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_usuario)

        if (intent.hasExtra(IDKEY)) {
            userId = intent.extras!!.getString(IDKEY, "").toString()
        }
        toolbar()

        setCardOnClickListerners()

/*//DESARROLLAR BOTON DE LOG OUT
        btn_log_out.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }*/


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bis,menu)
        //menu?.findItem(R.id.mis_direcciones_user)?.setVisible(false)
        toolbar_DashboardUsuario.setNavigationIcon(null)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio_user  -> {
            Toast.makeText(this,"Próximamente",Toast.LENGTH_SHORT).show()
            }
            R.id.mis_canjes_user  -> {
                Toast.makeText(this,"Próximamente",Toast.LENGTH_SHORT).show()
            }
            R.id.invita_amigos_user  -> {
                Toast.makeText(this,"Próximamente",Toast.LENGTH_SHORT).show()
            }
            R.id.guardado_user  -> {
                Toast.makeText(this,"Próximamente",Toast.LENGTH_SHORT).show()
            }

            R.id.mi_cuenta_user  -> {
                Toast.makeText(this,"Próximamente",Toast.LENGTH_SHORT).show()
            }
            R.id.logout_user  -> {
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

    private fun setCardOnClickListerners() {
    /*    cardView_proximo_servicio.setOnClickListener {
            val intent: Intent = Intent(this, DetalleServicioActivity::class.java)
            startActivity(intent)
        }*/
        cardView_mis_servicios.setOnClickListener {
            val intent: Intent = Intent(this, MisServiciosActivity::class.java)
            startActivity(intent)
        }

        cardView_mis_puntos.setOnClickListener {
            val intent: Intent = Intent(this, MisPuntosActivity::class.java)
            startActivity(intent)
        }
        cardView_mis_encuentros.setOnClickListener {
            val intent: Intent = Intent(this, MisEncuentrosActivity::class.java)
            startActivity(intent)
        }
        cardView_seccion_informativa.setOnClickListener {
            val intent: Intent = Intent(this, SeccionInformativaActivity::class.java)
            startActivity(intent)
        }
        btn_new_service.setOnClickListener {

            val intent = Intent(this, NuevoServicioActivity::class.java)
            intent.putExtra(NuevoServicioActivity.ID, userId)
            userId
            this.startActivity(intent)
        }
    }

    fun toolbar(){
setSupportActionBar(toolbar_DashboardUsuario)
    var ab:ActionBar?=supportActionBar
    if (ab!=null){
        ab.setHomeAsUpIndicator(R.drawable.menu)
        ab.setDisplayHomeAsUpEnabled(true)
    }

}

/*    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           android.R.id.home->{
                drawer.openDrawer(GravityCompat.START)
            }

        }
        return super.onOptionsItemSelected(item)
    }*/
    companion object {
        val IDKEY: String = "id"
    }


}