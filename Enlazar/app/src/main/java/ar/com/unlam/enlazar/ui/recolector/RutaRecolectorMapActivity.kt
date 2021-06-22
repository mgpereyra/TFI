package ar.com.unlam.enlazar.ui.recolector

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.data.retrofit.Constants
import ar.com.unlam.enlazar.data.retrofit.GoogleMapsApiImpl
import ar.com.unlam.enlazar.data.retrofit.ServiceFields
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.model.utils.DecodePointsJavaUtils
import ar.com.unlam.mapexample.geoClases.RouteResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_map_ruta_recolector.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RutaRecolectorMapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
    private lateinit var map: GoogleMap
    private var rutasResult: RouteResult? = null
    private var mPolylineList = listOf<LatLng>()
    private lateinit var mPolylineOptions: PolylineOptions
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mCurrentLatLng: LatLng = LatLng(0.0, 0.0)
    private var mCurrentDistance: Int = 0
    private var idService = ""
    private var serviceLat = ""
    private var serviceLon = ""
    private var driverLat = ""
    private var driverLon = ""
    private  lateinit var service: Service

    private lateinit var database: FirebaseDatabase

    //  private lateinit var referaceUsuario: DatabaseReference
    private lateinit var referaceServicio: DatabaseReference
    var handler = Handler(Looper.getMainLooper())
    private var listaPuntos: List<LatLng> = listOf(
        LatLng(-34.744774, -58.695204),
        LatLng(-34.746859, -58.717010),
        LatLng(-34.757320, -58.711366),
        LatLng(-34.762085, -58.706405)
    )

    companion object {
        const val REQUEST_CODE_LOCATION = 0
        const val SERVICE_ID = "idService"
        const val SERVICE_LAT = "lat"
        const val SERVICE_LONG = "lon"
        const val DRIVER_LAT = "currentlat"
        const val DRIVER_LONG = "currentlon"
        const val SERVICE_ADDRESS = "address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_ruta_recolector)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        createFragment()

        btnIniciarServicio.setOnClickListener {
            btnIniciarServicio.visibility = View.GONE
            btnFinalizarServicio.visibility = View.VISIBLE
            btnCancelarServicio.visibility = View.VISIBLE

        }
btnCancelarServicio.setOnClickListener {
    onBackPressed()
}
        btnFinalizarServicio.setOnClickListener {
            //obtenerCurretPositionLoop(true)

            getLastLocation()
            if (mCurrentDistance < 500) {
                btnFinalizarServicio.visibility = View.GONE
                btnCancelarServicio.visibility = View.GONE
                cardViewFinalizarServicio.visibility = View.VISIBLE

            } else {
                Toast.makeText(
                    this,
                    "Debes estar cerca del servicio para poder finalizarlo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnTerminarServConfirm.setOnClickListener {
            var comentario = comentarioTerminarServicio.text.toString()
            actualizarServicio(comentario)
            btnIniciarServicio.visibility = View.VISIBLE
            btnFinalizarServicio.visibility = View.GONE
            cardViewFinalizarServicio.visibility = View.GONE
            btnCancelarServicio.visibility = View.GONE
        }
    }

    fun obtenerCurretPositionLoop(cancelarServicio: Boolean) {
        val TIEMPO: Long = 10000
        handler.postDelayed(object : Runnable {
            override fun run() {

                getLastLocation()
                if (cancelarServicio) {
                    finishAndRemoveTask()
                }// función para refrescar la ubicación del conductor, creada en otra línea de código
                handler.postDelayed(this, TIEMPO)
            }
        }, TIEMPO)

    }


    private fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapRecolector) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // createMarker()
        // createPolylines()
        // map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)


        enableLocation()

        service = intent.getSerializableExtra("Service") as Service
        idService = intent.getStringExtra(SERVICE_ID).toString()
        serviceLat = intent.getStringExtra(SERVICE_LAT).toString()
        serviceLon = intent.getStringExtra(SERVICE_LONG).toString()
        driverLat = intent.getStringExtra(DRIVER_LAT).toString()
        driverLon = intent.getStringExtra(DRIVER_LONG).toString()
        //  trazarRutasLista(listaPuntos)

        getLastLocation()
        trazarRuta(
            LatLng(driverLat.toDouble(), driverLon.toDouble()),
            LatLng(service.latitud!!.toDouble(), service.longitud!!.toDouble())
        )
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
                                        rutasResult = response.body()!!
                                        mCurrentDistance =
                                            rutasResult!!.routes[0].legs[0].distance.value
                                        /*          Toast.makeText(
                                                      this@RutaRecolectorMapActivity,
                                                      "Current Distance: " + mCurrentDistance,
                                                      Toast.LENGTH_SHORT
                                                  ).show()*/

                                    }
                                    else -> {
                                        Toast.makeText(
                                            this@RutaRecolectorMapActivity,
                                            getString((response.code())),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }
                            }

                            override fun onFailure(call: Call<RouteResult>, t: Throwable) {
                                Toast.makeText(
                                    this@RutaRecolectorMapActivity,
                                    getString(R.string.search_call_error),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        })
                }
        }
    }

    private fun trazarRutasLista(listaUbi: List<LatLng>) {
        var i = 0
        val ultimo = listaUbi.size - 1
        while (i < ultimo) {
            trazarRuta(listaUbi[i], listaUbi[i + 1])
            i++
        }

    }

    private fun trazarRuta(origen: LatLng, destino: LatLng) {
        val serviceAddress = intent.getStringExtra(SERVICE_ADDRESS)
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
                                    this@RutaRecolectorMapActivity,
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
                                this@RutaRecolectorMapActivity,
                                "Trazado exitoso",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this@RutaRecolectorMapActivity,
                                getString((response.code())),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }


                override fun onFailure(call: Call<RouteResult>, t: Throwable) {
                    Toast.makeText(
                        this@RutaRecolectorMapActivity,
                        getString(R.string.search_call_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }
    }

    private fun actualizarServicio(comentario: String?) {
        database = FirebaseDatabase.getInstance()
        referaceServicio = database.getReference(Constants.SERVICE_REF)
        referaceServicio.child(idService).child(ServiceFields.ESTADO)
            .setValue(Constants.ESTADO_SERV_FINALIZADO)
        referaceServicio.child(idService).child(ServiceFields.COMENTARIO).setValue(comentario)

        val intent = Intent(this, ServiciosRecolectorRutaActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun createPolylines() {
        val polylineOptions = PolylineOptions()
            .add(LatLng(-34.744774, -58.695204))
            .add(LatLng(-34.749489, -58.710446))
        val polyLine = map.addPolyline(polylineOptions)
        polyLine.jointType
    }

    private fun createMarker() {
        val coordinates = LatLng(-34.744774, -58.695204)
        val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Mi calle")
        map.addMarker(marker)
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