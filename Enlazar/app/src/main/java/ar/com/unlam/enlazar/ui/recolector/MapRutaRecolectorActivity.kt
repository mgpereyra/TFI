package ar.com.unlam.enlazar.ui.recolector

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.data.retrofit.GoogleMapsApiImpl
import ar.com.unlam.enlazar.model.utils.DecodePointsJavaUtils
import ar.com.unlam.mapexample.geoClases.RouteResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapRutaRecolectorActivity : AppCompatActivity(),OnMapReadyCallback{
    private  lateinit var map :GoogleMap
    private var rutasResult: RouteResult? = null
    private var mPolylineList = listOf<LatLng>()
    private lateinit var mPolylineOptions: PolylineOptions
    private var listaPuntos: List<LatLng> = listOf(LatLng(-34.744774, -58.695204), LatLng(-34.746859, -58.717010),LatLng(  -34.757320, -58.711366), LatLng(-34.762085, -58.706405))
    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_ruta_recolector)
        createFragment()
    }

    private fun createFragment(){
        val mapFragment=supportFragmentManager.findFragmentById(R.id.mapRecolector) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // createMarker()
        // createPolylines()
       // map.setOnMyLocationButtonClickListener(this)
      //  map.setOnMyLocationClickListener(this)
        enableLocation()
        //trazarRutasLista(listaPuntos)
        trazarRuta(LatLng(-34.744774, -58.695204), LatLng(-34.746859, -58.717010))
    }

    private fun trazarRutasLista(listaUbi : List<LatLng>){
        var i =0
        val ultimo=listaUbi.size-1
        while (i <ultimo){
            trazarRuta(listaUbi[i],listaUbi[i+1])
            i++
        }

    }


    private  fun trazarRuta(origen: LatLng, destino: LatLng) {
        CoroutineScope(Dispatchers.IO).launch {

            GoogleMapsApiImpl().getRoutesAp(origen, destino, object : Callback<RouteResult> {
                override fun onResponse(call: Call<RouteResult>, response: Response<RouteResult>) {
                    when (response.code()) {
                        in 200..299 -> {
                            rutasResult = response.body()!!
                            mPolylineList =
                                DecodePointsJavaUtils.decodePoly(rutasResult!!.routes[0].overview_polyline.points) as List<LatLng>

                            mPolylineOptions = PolylineOptions()
                            mPolylineOptions.color(Color.GREEN)
                            mPolylineOptions.width(8f)
                            mPolylineOptions.startCap(SquareCap())
                            mPolylineOptions.jointType(JointType.ROUND)
                            mPolylineOptions.addAll(mPolylineList)
                            val polyLine = map.addPolyline(mPolylineOptions)
                            polyLine.jointType
                            var marker: MarkerOptions = MarkerOptions().position(destino)
                            map.addMarker(marker)
                            map.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(LatLng(-34.744774, -58.695204), 18f),
                                2000, null
                            )
                            Toast.makeText(
                                this@MapRutaRecolectorActivity,
                                "Trazado exitoso",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this@MapRutaRecolectorActivity,
                                getString((response.code())),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }


                override fun onFailure(call: Call<RouteResult>, t: Throwable) {
                    Toast.makeText(
                        this@MapRutaRecolectorActivity,
                        getString(R.string.search_call_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }
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
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(-34.744774, -58.695204), 18f),
            4000,
            null
        )
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
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
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
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
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
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
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

    /*override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Boton pulsado", Toast.LENGTH_SHORT).show()

        return false //en false te lleva a la ubicacion actual al abrir. En true no
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Estas en ${p0.latitude},${p0.longitude}", Toast.LENGTH_SHORT).show()
    }*/
}