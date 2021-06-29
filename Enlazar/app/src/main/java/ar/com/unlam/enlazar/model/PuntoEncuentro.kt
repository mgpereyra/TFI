package ar.com.unlam.enlazar.model

import java.io.Serializable

data class PuntoEncuentro(
    val asistentes:HashMap<String,String>? = null,
    var id:String? = null,
    val description:String? = null,
    var localidad:String? = null,
    var calle:String? = null,
    var lugar:String? = null,
    val latitud:String? = null,
    val longitud:String? = null,
    val estado:Int? = null,
    val date: String? = null,
    val time:String? = null,
    ): Serializable


