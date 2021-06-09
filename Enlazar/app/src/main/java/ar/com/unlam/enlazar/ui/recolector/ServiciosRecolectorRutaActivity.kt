package ar.com.unlam.enlazar.ui.recolector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisServiciosRecolectorAdapter
import ar.com.unlam.enlazar.databinding.ActivityServiciosRecolectorRutaBinding
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.model.clasesDePrueba.Direccion
import ar.com.unlam.enlazar.model.clasesDePrueba.PuntoLatLong
import ar.com.unlam.enlazar.model.clasesDePrueba.Recolector
import ar.com.unlam.enlazar.model.clasesDePrueba.Servicio
import ar.com.unlam.enlazar.ui.Estado
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_servicios_recolector_ruta.*

class ServiciosRecolectorRutaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiciosRecolectorRutaBinding
    private lateinit var adapter: MisServiciosRecolectorAdapter
    private val viewModelServices: ServiciosRecolectorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios_recolector_ruta)
        binding = ActivityServiciosRecolectorRutaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MisServiciosRecolectorAdapter { service -> toOnItemViewClick(service) }
        with(binding.rvRutaServiciosList) {
            // layoutManager = GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false) // Para implementar en con otro estilo
            layoutManager =
                LinearLayoutManager(this@ServiciosRecolectorRutaActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@ServiciosRecolectorRutaActivity.adapter
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
   /*     buttonIrMapa.setOnClickListener {

        }*/
        binding.buttonRutas.setOnClickListener {
    createServicesPruebas()
        }
    }

    private fun toOnItemViewClick(servicio: Service) {
        val intent = Intent(this, RutaRecolectorMapActivity::class.java)
        intent.putExtra("idService", servicio.id)
        intent.putExtra("lat", servicio.latitud)
        intent.putExtra("lon", servicio.longitud)
        startActivity(intent)
    }

    fun generarRecolector() {
        var recolector = Recolector("Iduno", "NombreRecolector1")
        val puntoLatLOng = PuntoLatLong(-34.744774, -58.695204)
        val direccion = Direccion("", "Pontevedra", "Azul", 4097, puntoLatLOng)
        val servicioUno = Servicio("", recolector.id, "IdUsuarioUno", 0, direccion)
    }


    val ser1= LatLng(-34.743781, -58.697613)
    val ser3= LatLng(-34.745782, -58.699168)
    val ser2= LatLng( -34.745570, -58.696870)

    private fun createServicesPruebas() {
        /*val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")*/
        val db= FirebaseDatabase.getInstance().getReference()

        var serviceId = db.push().key.toString()
        var service = Service(
            "Avenida Vélez 1526",
            serviceId,
            "-34.742716",
            "-58.696146",
            3,
            4,
            5,
            "14/5/2021",
            "10:00",
            "fUSJ6q9xbfdYODe3jvP0eG5ksN23",
            "IogPUzpZOGXzBJxJJeP24IWVSA73",
            Estado.PENDIENTE.ordinal
        )
        var serviceId1 = db.push().key.toString()
        var service1 = Service(
            "Avenida Vélez 1526",
            serviceId1,
            ser1.latitude.toString(),
            ser1.longitude.toString(),
            3,
            4,
            5,
            "14/5/2021",
            "10:00",
            "fUSJ6q9xbfdYODe3jvP0eG5ksN23",
            "IogPUzpZOGXzBJxJJeP24IWVSA73",
            Estado.PENDIENTE.ordinal
        )
        var serviceId2 = db.push().key.toString()

        var service2 = Service("Avenida Díaz 526", serviceId2, ser2.latitude.toString(), ser2.longitude.toString(),3,
            4,5,    "14/5/2021",  "10:00", "fUSJ6q9xbfdYODe3jvP0eG5ksN23","IogPUzpZOGXzBJxJJeP24IWVSA73",
            Estado.PENDIENTE.ordinal
        )
        var serviceId3 = db.push().key.toString()

        var service3 = Service(
            "Vélez 1526",
            serviceId3,
            ser3.latitude.toString(),
            ser3.longitude.toString(),
            3,
            4,
            5,
            "14/5/2021",
            "10:00",
            "fUSJ6q9xbfdYODe3jvP0eG5ksN23",
            "IogPUzpZOGXzBJxJJeP24IWVSA73",
            Estado.PENDIENTE.ordinal
        )
        if (serviceId != null) {
            db.child("Service").child(serviceId).setValue(service).addOnCompleteListener {
            }
            db.child("Service").child(serviceId1).setValue(service1).addOnCompleteListener {
            }
            db.child("Service").child(serviceId2).setValue(service2).addOnCompleteListener {
            }
            db.child("Service").child(serviceId3).setValue(service3).addOnCompleteListener {
            }
        }
    }
/*  val analytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message", "integracion de firebase completa")
        analytics.logEvent("InitScreen",bundle)*/
}