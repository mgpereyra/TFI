package ar.com.unlam.enlazar.model

data class UserRecolector(
    var active: String? = null,
    var dni: String? = null,
    var email: String? = null,
    var id: String? = null,
    var initDate: String? = null,
    var lat: Long? = null,
    var lng: Long? = null,
    var name: String? = null,
    var password: String? = null,
    var phone: String? = null,
    var surname: String? = null,
    var typeUser: Int? = 1,
    var ubication: String? = null
)
