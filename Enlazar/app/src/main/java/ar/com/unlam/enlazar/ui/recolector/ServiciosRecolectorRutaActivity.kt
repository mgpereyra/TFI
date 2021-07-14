package ar.com.unlam.enlazar.ui.recolector

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
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
import ar.com.unlam.enlazar.ui.LoginActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dashboard_recolector.*
import kotlinx.android.synthetic.main.activity_servicios_recolector_ruta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiciosRecolectorRutaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiciosRecolectorRutaBinding
    private lateinit var adapter: MisServiciosRecolectorAdapter
    private val viewModelServices: ServiciosRecolectorViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mCurrentLatLng: LatLng = LatLng(0.0, 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios_recolector_ruta)
        binding = ActivityServiciosRecolectorRutaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MisServiciosRecolectorAdapter { service -> toOnItemViewClick(service) }
        with(binding.rvRutaServiciosList) {
            layoutManager =
                LinearLayoutManager(
                    this@ServiciosRecolectorRutaActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            this.adapter = this@ServiciosRecolectorRutaActivity.adapter
        }
toolbar()
        setListeners()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
       // getServicesResponde2()

    }

    override fun onStart() {
        getServicesResponde2()
       // viewModelServices.getServices()
       // setObservers()
        super.onStart()
    }

    override fun onResume() {
        getServicesResponde2()

        super.onResume()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recolector, menu)
        //menu?.findItem(R.id.mis_direcciones_user)?.setVisible(false)
        toolbar_serv_list_rec.title= "Servicios pendientes"
        toolbar_serv_list_rec.setNavigationIcon(null)
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
        setSupportActionBar(toolbar_serv_list_rec)
        var ab: ActionBar? = supportActionBar
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.menu)
            ab.setDisplayHomeAsUpEnabled(true)
        }

    }

    private fun getServicesResponde2() {
        viewModelServices.misServicios.observe(this, Observer{
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

/*    private fun setObservers() {
        viewModelServices.misServicios.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }*/

    private fun setListeners() {
        /*     buttonIrMapa.setOnClickListener {

             }
             binding.buttonRutas.setOnClickListener {
         createServicesPruebas()
             }*/


    }

    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    mCurrentLatLng = LatLng(location!!.latitude, location.longitude)
                }
        }

    }

    private fun toOnItemViewClick(servicio: Service) {
        if(servicio.estado==3){
            Toast.makeText(this, "el servicio seleccionado está finalizado",Toast.LENGTH_LONG).show()
        }else{

            val intent = Intent(this, RutaRecolectorMapActivity::class.java)
            intent.putExtra("idService", servicio.id)
            intent.putExtra("lat", servicio.latitud)
            intent.putExtra("lon", servicio.longitud)
            intent.putExtra("currentlat", mCurrentLatLng.latitude.toString())
            intent.putExtra("currentlon", mCurrentLatLng.longitude.toString())
            intent.putExtra("address", servicio.address)
            intent.putExtra("Service",servicio)
            startActivity(intent)
        }

    }
}
/*  val analytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message", "integracion de firebase completa")
        analytics.logEvent("InitScreen",bundle)*/
/*  fun generarRecolector() {
        var recolector = Recolector("Iduno", "NombreRecolector1")
        val puntoLatLOng = PuntoLatLong(-34.744774, -58.695204)
        val direccion = Direccion("", "Pontevedra", "Azul", 4097, puntoLatLOng)
        val servicioUno = Servicio("", recolector.id, "IdUsuarioUno", 0, direccion)
    }*/
/*
private fun createServicesPruebas() {
    */
/*val date = getCurrentDateTime()
    val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")*//*

    val db = FirebaseDatabase.getInstance().getReference()

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

    var service2 = Service(
        "Avenida Díaz 526",
        serviceId2,
        ser2.latitude.toString(),
        ser2.longitude.toString(),
        3,
        4,
        5,
        "14/5/2021",
        "10:00",
        "fUSJ6q9xbfdYODe3jvP0eG5ksN23",
        "IogPUzpZOGXzBJxJJeP24IWVSA73",
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
*/
