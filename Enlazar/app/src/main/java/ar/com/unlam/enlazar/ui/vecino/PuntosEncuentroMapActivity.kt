package ar.com.unlam.enlazar.ui.vecino

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.data.retrofit.GoogleMapsApiImpl
import ar.com.unlam.enlazar.model.utils.DecodePointsJavaUtils
import ar.com.unlam.mapexample.geoClases.RouteResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_map_ruta_recolector.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PuntosEncuentroMapActivity : AppCompatActivity() , OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
    private lateinit var map: GoogleMap
    private val puntosEncuentroMapViewModel: PuntosEncuentroMapViewModel by viewModels()
    private var rutasResult: RouteResult? = null
    private var mPolylineList = listOf<LatLng>()
    private lateinit var mPolylineOptions: PolylineOptions
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mCurrentLatLng: LatLng = LatLng(0.0, 0.0)
    private lateinit var database: FirebaseDatabase

    private var listaPuntos: List<LatLng> = listOf(
        LatLng(-34.744774, -58.695204),
        LatLng(-34.746859, -58.717010),
        LatLng(-34.757320, -58.711366),
        LatLng(-34.762085, -58.706405)
    )

    companion object {
        const val REQUEST_CODE_LOCATION = 0
        const val PE_ID = "idPuntoEncuentro"
        const val PE_LAT = "lat"
        const val PE_LONG = "lon"
        const val CURRENT_USER_LAT = "currentlat"
        const val CURRENT_USER_LONG = "currentlon"
        const val PE_ADDRESS = "address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntos_encuentro_map)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createFragment()
    }

    override fun onStart() {
        getMeetingPoints()
        super.onStart()
    }
    private fun getMeetingPoints() {
        puntosEncuentroMapViewModel.misPuntosEncuentro.observe(this, {

            createMarkers()
        })
    }

    private fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapPuntosDeEncuentro) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLastLocation()
        map.setOnMyLocationClickListener(this)
        enableLocation()

   /*     service = intent.getSerializableExtra("pe") as Service
        idService = intent.getStringExtra(PE_ID).toString()
        serviceLat = intent.getStringExtra(PE_LAT).toString()
        serviceLon = intent.getStringExtra(PE_LONG).toString()
        driverLat = intent.getStringExtra(CURRENT_USER_LAT).toString()
        driverLon = intent.getStringExtra(CURRENT_USER_LONG).toString()*/
        //  trazarRutasLista(listaPuntos)

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
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(mCurrentLatLng.latitude, mCurrentLatLng.longitude),
                            15f
                        ),
                        2000, null
                    )
                }
        }
    }



    private fun trazarRuta(origen: LatLng, destino: LatLng) {
        val serviceAddress = intent.getStringExtra(PE_ADDRESS)
        CoroutineScope(Dispatchers.IO).launch {

            GoogleMapsApiImpl().getRoutesAp(origen, destino, object : Callback<RouteResult> {
                override fun onResponse(call: Call<RouteResult>, response: Response<RouteResult>) {
                    when (response.code()) {
                        in 200..299 -> {
                            rutasResult = response.body()!!
                            mPolylineList =
                                DecodePointsJavaUtils.decodePoly(rutasResult!!.routes[0].overview_polyline.points) as List<LatLng>

                            mPolylineOptions = PolylineOptions()
                            mPolylineOptions.color(
                                ContextCompat.getColor(
                                    this@PuntosEncuentroMapActivity,
                                    R.color.green2
                                )
                            )
                            mPolylineOptions.width(13f)
                            mPolylineOptions.startCap(SquareCap())
                            mPolylineOptions.jointType(JointType.ROUND)
                            mPolylineOptions.addAll(mPolylineList)
                            val polyLine = map.addPolyline(mPolylineOptions)
                            polyLine.jointType
                            var marker: MarkerOptions =
                                MarkerOptions().position(destino).title(serviceAddress)
                            // .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_service_v_uno
                            map.addMarker(marker)
                            map.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(origen.latitude, origen.longitude),
                                    15f
                                ),
                                2000, null
                            )
                            Toast.makeText(
                                this@PuntosEncuentroMapActivity,
                                "Trazado exitoso",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this@PuntosEncuentroMapActivity,
                                getString((response.code())),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }


                override fun onFailure(call: Call<RouteResult>, t: Throwable) {
                    Toast.makeText(
                        this@PuntosEncuentroMapActivity,
                        getString(R.string.search_call_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }
    }


    private fun createMarkers() {
        val coordinates = LatLng(-34.744774, -58.695204)
        puntosEncuentroMapViewModel.misPuntosEncuentro.value?.forEach {
            if (it.latitud.isNullOrEmpty()||it.longitud.isNullOrEmpty()){
               Toast.makeText(this,it.calle.toString(),Toast.LENGTH_SHORT).show()
            }else{
                    val marker: MarkerOptions = MarkerOptions()
                        .position(LatLng(it.latitud?.toDouble()!!,it.longitud?.toDouble()!!))
                        .title(it.title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pe_marker_uno_round))
                      //  .anchor(0.0f,1.0f)
                map.addMarker(marker)
            }

        }
        //map.addMarker(marker)
        /*      map.animateCamera(
                  CameraUpdateFactory.newLatLngZoom(LatLng(-34.744774, -58.695204), 18f),
                  4000,
                  null
              )*/
    }

    private fun isLocarionPermissionGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::map.isInitialized) return//si el mapa no esta inicializado sale
        if (isLocarionPermissionGranted()) {//si los permisos estan activos activa la loc en tiempo real sino pide los permisos
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
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    //Si se han pedido antes y los rechazo anteriormente habilitalos en ajustes
    //si no se pidieron antes se piden
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    //si los acepta se activa sino se abre dialogo para pedir que los acepte
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localizacion ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return//si el mapa no esta inicializado sale
        if (!isLocarionPermissionGranted()) {
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
            map.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localizacion ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Tú ubicacion", Toast.LENGTH_SHORT).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

}

/*    fun getLastLocation() {
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
            val servDestination =
                LatLng(service.latitud!!.toDouble(), service.longitud!!.toDouble())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->

                    mCurrentLatLng = LatLng(location!!.latitude, location.longitude)
                    GoogleMapsApiImpl().getRoutesAp(
                        mCurrentLatLng,
                        servDestination,
                        object : Callback<RouteResult> {
                            override fun onResponse(
                                call: Call<RouteResult>,
                                response: Response<RouteResult>
                            ) {
                                when (response.code()) {
                                    in 200..299 -> {
                                        //rutasResult = response.body()!!
                                     //   mCurrentDistance = rutasResult!!.routes[0].legs[0].distance.value
                                        *//*          Toast.makeText(
                                                      this@RutaRecolectorMapActivity,
                                                      "Current Distance: " + mCurrentDistance,
                                                      Toast.LENGTH_SHORT
                                                  ).show()*//*

                                    }
                                    else -> {
                                        Toast.makeText(
                                            this@PuntosEncuentroMapActivity,
                                            getString((response.code())),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }
                            }

                            override fun onFailure(call: Call<RouteResult>, t: Throwable) {
                                Toast.makeText(
                                    this@PuntosEncuentroMapActivity,
                                    getString(R.string.search_call_error),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        })
                }
        }
    }*/