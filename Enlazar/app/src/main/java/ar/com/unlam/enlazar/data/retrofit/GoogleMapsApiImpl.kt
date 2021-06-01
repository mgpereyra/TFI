package ar.com.unlam.enlazar.data.retrofit

import ar.com.unlam.enlazar.R
import ar.com.unlam.mapexample.geoClases.RouteResult
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GoogleMapsApiImpl {

    private val baseUrlApiMap = "https://maps.googleapis.com"

    fun getApi(): GoogleMapsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrlApiMap)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GoogleMapsApi::class.java) //crea el servicio para hacer las llamadas
    }

   /* fun getRoutesAp(origen: LatLng, destino: LatLng, callback: Callback<RouteResult>) {
        var query = "/maps/api/directions/json?origin=" + origen.latitude + "," + origen.longitude +
                "&destination=" + destino.latitude + "," + destino.longitude +
                "&key=" + R.string.google_map_key
        val keyy = "AIzaSyDulhHt1YltEBYDSjDTm8_avq0CNnl93lA"
        var origenString = origen.latitude.toString() + "," + origen.longitude.toString()
        var destinString = destino.latitude.toString() + "," + destino.longitude.toString()
        getApi().search(origenString, destinString, keyy).enqueue(callback)
    }*/
}