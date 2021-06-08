package ar.com.unlam.enlazar.ui.recolector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisServiciosRecolectorAdapter
import ar.com.unlam.enlazar.databinding.ActivityMiRutaBinding
import ar.com.unlam.enlazar.model.Services
import ar.com.unlam.enlazar.model.clasesDePrueba.Direccion
import ar.com.unlam.enlazar.model.clasesDePrueba.PuntoLatLong
import ar.com.unlam.enlazar.model.clasesDePrueba.Recolector
import ar.com.unlam.enlazar.model.clasesDePrueba.Servicio
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_mi_ruta.*

class MiRutaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiRutaBinding
    private lateinit var adapter: MisServiciosRecolectorAdapter
    private val viewModelServices: MiRutaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_ruta)
        binding = ActivityMiRutaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MisServiciosRecolectorAdapter { service -> toOnItemViewClick(service) }
        with(binding.rvRutaServiciosList) {
            // layoutManager = GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false) // Para implementar en con otro estilo
            layoutManager =
                LinearLayoutManager(this@MiRutaActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@MiRutaActivity.adapter
        }
        setListeners()

    }

    override fun onStart() {
        getServicesResponde()
        //viewModelServices.getServices()
        setObservers()
        super.onStart()
    }

    private fun getServicesResponde() {
        viewModelServices.responseLiveData.observe(this, {
            it.listService?.let { it1 -> adapter.submitList(it1) }
            adapter.notifyDataSetChanged()
        })
    }

    private fun setObservers() {
        viewModelServices.misServicios.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setListeners() {
        buttonIrMapa.setOnClickListener {

        }
        buttonRutas.setOnClickListener {

        }
    }

    private fun toOnItemViewClick(servicio: Services) {
        val intent = Intent(this, MapRutaRecolectorActivity::class.java)
        intent.putExtra("idNote", servicio.Id)
        startActivity(intent)
    }

    fun generarRecolector() {
        var recolector = Recolector("Iduno", "NombreRecolector1")
        val puntoLatLOng = PuntoLatLong(-34.744774, -58.695204)
        val direccion = Direccion("", "Pontevedra", "Azul", 4097, puntoLatLOng)
        val servicioUno = Servicio("", recolector.id, "IdUsuarioUno", 0, direccion)
    }
/*  val analytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message", "integracion de firebase completa")
        analytics.logEvent("InitScreen",bundle)*/
}