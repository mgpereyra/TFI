package ar.com.unlam.enlazar.model

import java.util.*

data class User (
        var Address:String,
        var District:String,
        var Dni:Int,
        var Email:String,
        var Id:String?,
        var InitDate:String,
        var Latitud:Double?,
        var Longitud:Double?,
        var Locality:String,
        var Name:String,
        var Password:String,
        var Phone:String,
        var TypeUser:Int=1,

        )
