package ar.com.unlam.enlazar.model

import java.util.*

data class Services (
        val Id:Int,
        val Latitud:Long,
        val Longitud:Long,
        val EnvasesPlasticos:Int,
        val EnvasesVidrio:Int,
        val EnvasesCarton:Int,
        val Date: Date,
        val time:Date


        )