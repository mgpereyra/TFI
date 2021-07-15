package ar.com.unlam.enlazar.model

import android.icu.text.CaseMap
import java.io.Serializable

data class PuntoEncuentro(
    val title:String? = null,
    val asistentes:HashMap<String,String>? = null,
    var id:String? = null,
    val description:String? = null,
    var localidad:String? = null,
   // var calle:String? = null,
    var ubication:String? = null,
    val lat:Double? = null,
    val lng:Double? = null,
    val estado:Int? = null,
    val date: String? = null,
    val time:String? = null,
    ): Serializable


