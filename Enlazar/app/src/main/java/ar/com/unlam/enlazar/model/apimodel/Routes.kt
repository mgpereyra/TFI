package ar.com.unlam.mapexample.geoClases

import Bounds
import Legs
import Overview_polyline
import com.google.gson.annotations.SerializedName


data class Routes(

    @SerializedName("bounds") var bounds: Bounds,
    @SerializedName("copyrights") var copyrights: String,
    @SerializedName("legs") var legs: List<Legs>,
    @SerializedName("overview_polyline") var overview_polyline: Overview_polyline,
    @SerializedName("summary") var summary: String,
    @SerializedName("warnings") var warnings: List<String>,
    @SerializedName("waypoint_order") var waypoint_order: List<String>
)