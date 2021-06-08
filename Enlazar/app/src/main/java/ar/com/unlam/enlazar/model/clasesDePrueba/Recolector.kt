package ar.com.unlam.enlazar.model.clasesDePrueba

import ar.com.unlam.mapexample.geoClases.Routes
import com.google.gson.annotations.SerializedName

data class Recolector(
    val id:String,
    val nombre: String="",
    val rol:Int=1,
   // @SerializedName("Service") var servicios: List<Servicio>,
)
