package ar.com.unlam.enlazar.model

import java.io.Serializable

data class PuntoEncuentro(
    var id:String? = null,
    val description:String? = null,
    var localidad:String? = null,
    var calle:String? = null,
    var lugar:String? = null,
    val estado:Int? = null,
    val date: String? = null,
    val time:String? = null,
    ): Serializable