package ar.com.unlam.enlazar.model

import java.io.Serializable
import java.util.*

data class Service (
        val address:String? = null,
        val id:String? = null,
        val latitud:String? = null,
        val longitud:String? = null,
        val envasesPlasticos:Int?? = null,
        val envasesVidrio:Int?? = null,
        val envasesCarton:Int?? = null,
        val date: String? = null,
        val time:String? = null,
        val userId:String? = null,
        val recolectorId:String? = null,
        val estado:Int? = null,

        ): Serializable