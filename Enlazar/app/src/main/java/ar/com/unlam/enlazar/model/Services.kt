package ar.com.unlam.enlazar.model

import java.util.*

data class Services (
        val Id:String,
        val Latitud:Double,
        val Longitud:Double,
        val EnvasesPlasticos:Int,
        val EnvasesVidrio:Int,
        val EnvasesCarton:Int,
        val Date: Date,
        val time:Date


        )