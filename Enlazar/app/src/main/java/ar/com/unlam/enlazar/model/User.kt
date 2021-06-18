package ar.com.unlam.enlazar.model

import java.io.Serializable
import java.util.*

data class User (
        var address:String? = null,
        var district:String? = null,
        var dni:String? = null,
        var email:String? = null,
        var id:String? = null,
        var initDate:String? = null,
        var latitud:String? = null,
        var longitud:String? = null,
        var locality:String? = null,
        var name:String? = null,
        var password:String? = null,
        var phone:String? = null,
        var typeUser:Int? = 1,

        ):Serializable
