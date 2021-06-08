package ar.com.unlam.enlazar.model

import java.util.*

data class Services (
        val Address:String? = null,
        val Id:String? = null,
        val Latitud:String? = null,
        val Longitud:String? = null,
        val EnvasesPlasticos:Int?? = null,
        val EnvasesVidrio:Int?? = null,
        val EnvasesCarton:Int?? = null,
        val Date: String? = null,
        val time:String? = null,
        val UserId:String? = null,
        val RecolectorId:String? = null,
        val Estado:Int? = null,

        )