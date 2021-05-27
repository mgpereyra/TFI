package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.btnVolver


class NuevoServicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_servicio)
        toolbar("Enlazar")
btnVolver.setOnClickListener {
    btnVolver.setOnClickListener {
        this@NuevoServicioActivity.finish()
    }
}

    }
    fun toolbar(title:String){
        setSupportActionBar(toolbar2)
        var ab: ActionBar?=supportActionBar
        if (ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.menu)
            ab.setDisplayHomeAsUpEnabled(true)
            ab.title=title
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