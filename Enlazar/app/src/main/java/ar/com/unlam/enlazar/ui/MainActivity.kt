package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            val intent: Intent = Intent(this, MisPuntosActivity::class.java)
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
            this@MainActivity.finish()
            val intent: Intent = Intent(this, NuevoServicioActivity::class.java)
            startActivity(intent)

        }

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
}