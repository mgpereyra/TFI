package ar.com.unlam.enlazar.data.retrofit

import ar.com.unlam.mapexample.geoClases.RouteResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsApi {
    @GET("/maps/api/directions/json")
    fun search(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<RouteResult>
}