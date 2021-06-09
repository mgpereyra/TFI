package ar.com.unlam.enlazar.model.clasesDePrueba

import com.google.gson.annotations.SerializedName

data class PuntoLatLong(
    @SerializedName("Lat")var lat : Double,
    @SerializedName("Long")	var lng : Double
)