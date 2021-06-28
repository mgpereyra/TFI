package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CardInfo
import ar.com.unlam.enlazar.ui.TipoConsejo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
//import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class DashboardUserActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().getReference()
    var userId:String=""
    var puntosUser=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
getPuntos()
        if (intent.hasExtra(IDKEY)) {
            userId = intent.extras!!.getString(IDKEY, "").toString()
        }
        toolbar()

        cardView_proximo_servicio.setOnClickListener{
            val intent: Intent = Intent(this, DetalleServicioActivity::class.java)
            startActivity(intent)
        }
        cardView_mis_servicios.setOnClickListener{
    val intent: Intent = Intent(this, MisServiciosConfirmadosActivity::class.java)
    startActivity(intent)
                    }

        cardView_mis_puntos.setOnClickListener {
            val intent= Intent(this, MisPuntosActivity::class.java)
            intent.putExtra(MisPuntosActivity.PUNTOS, puntosUser)
            startActivity(intent)
        }
       cardView_mis_encuentros.setOnClickListener{
            val intent: Intent = Intent(this, MisEncuentrosActivity::class.java)
            startActivity(intent)

        }
        cardView_seccion_informativa.setOnClickListener {
            val intent: Intent = Intent(this, SeccionInformativaActivity::class.java)
            startActivity(intent)
        }
        btn_new_service.setOnClickListener{

            val intent= Intent(this, NuevoServicioActivity::class.java)
            intent.putExtra(NuevoServicioActivity.ID, userId)
            userId
            this.startActivity(intent)

        }

/*//DESARROLLAR BOTON DE LOG OUT
        btn_log_out.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }*/

    }
fun toolbar(){
setSupportActionBar(toolbar)
    var ab:ActionBar?=supportActionBar
    if (ab!=null){
        ab.setHomeAsUpIndicator(R.drawable.menu)
        ab.setDisplayHomeAsUpEnabled(true)
    }

}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           android.R.id.home->{
                drawer.openDrawer(GravityCompat.START)
            }

        }
        return super.onOptionsItemSelected(item)
    }
    private fun getPuntos(){
        db.child("User").child(FirebaseAuth.getInstance().currentUser!!.uid).child("puntos").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                puntosUser=snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
    companion object {
        val IDKEY: String = "id"
    }


}