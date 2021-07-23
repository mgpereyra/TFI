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
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisServiciosRecolectorAdapter
import ar.com.unlam.enlazar.databinding.ActivityServiciosFinalizadosRecolectorBinding
import ar.com.unlam.enlazar.databinding.ActivityServiciosRecolectorRutaBinding
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.LoginActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_servicios_finalizados_recolector.*
import kotlinx.android.synthetic.main.activity_servicios_recolector_ruta.*
import kotlinx.android.synthetic.main.activity_servicios_recolector_ruta.no_serv_asignados_rec
import kotlinx.android.synthetic.main.activity_servicios_recolector_ruta.toolbar_serv_list_rec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiciosFinalizadosRecolectorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiciosFinalizadosRecolectorBinding
    private lateinit var adapter: MisServiciosRecolectorAdapter
    private val viewModelServices: ServiciosFinalizadosRecolectorViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mCurrentLatLng: LatLng = LatLng(0.0, 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios_finalizados_recolector)
        binding = ActivityServiciosFinalizadosRecolectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MisServiciosRecolectorAdapter { service -> toOnItemViewClick(service) }
        with(binding.rvRutaServiciosFinalizadosList) {
            layoutManager =
                LinearLayoutManager(
                    this@ServiciosFinalizadosRecolectorActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            this.adapter = this@ServiciosFinalizadosRecolectorActivity.adapter
        }
        toolbar()
        setListeners()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
    override fun onStart() {
        getServicesResponde2()
        setObservers()
        super.onStart()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recolector, menu)
        //menu?.findItem(R.id.mis_direcciones_user)?.setVisible(false)
        toolbar_serv_list_rec.setNavigationIcon(null)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
          /*  R.id.inicio_rec -> {
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
            }*/

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
        viewModelServices.misServicios.observe(this, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setObservers() {
        viewModelServices.misServicios.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            if(it.isEmpty()){
                no_serv_finalizados_rec.visibility = View.VISIBLE
            }else{
                no_serv_finalizados_rec.visibility = View.GONE
            }
        })
    }

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
            Toast.makeText(this, "El servicio seleccionado está finalizado", Toast.LENGTH_LONG).show()
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