package ar.com.unlam.enlazar.model

import java.lang.Exception

data class ResponseServicios
    (
    var listService: List<Services>? = null,
    var exception: Exception? = null

)
