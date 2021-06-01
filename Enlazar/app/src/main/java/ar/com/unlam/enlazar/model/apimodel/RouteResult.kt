package ar.com.unlam.mapexample.geoClases

import Geocoded_waypoints
import com.google.gson.annotations.SerializedName

data class RouteResult(

    @SerializedName("geocoded_waypoints") var geocoded_waypoints: List<Geocoded_waypoints>,
    @SerializedName("routes") var routes: List<Routes>,
    @SerializedName("status") var status: String
)
