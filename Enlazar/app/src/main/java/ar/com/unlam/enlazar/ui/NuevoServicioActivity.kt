package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.btnVolver
import org.koin.android.viewmodel.ext.android.viewModel


class NuevoServicioActivity : AppCompatActivity() {
    val newServiceViewModel: NewServiceViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_servicio)
     //   setObservers()
        toolbar()
btnVolver.setOnClickListener {
    btnVolver.setOnClickListener {
        this@NuevoServicioActivity.finish()
    }
}

    }
    fun toolbar(){
        setSupportActionBar(toolbar2)
        var ab: ActionBar?=supportActionBar
        if (ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.menu)
            ab.setDisplayHomeAsUpEnabled(true)

        }

    }
    private fun setObservers() {
        newServiceViewModel.estados.observe(this,{estado(it)})

    }
    private fun estado(status: NewServiceViewModel.EstadoNewService) {
        when (status) {
            NewServiceViewModel.EstadoNewService.SUCCESS -> Toast.makeText(this@NuevoServicioActivity,
                getString(R.string.succes), Toast.LENGTH_LONG).show()
            NewServiceViewModel.EstadoNewService.ERROR -> Toast.makeText(this@NuevoServicioActivity,
                getString(R.string.error), Toast.LENGTH_LONG).show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                drawerNewService.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}