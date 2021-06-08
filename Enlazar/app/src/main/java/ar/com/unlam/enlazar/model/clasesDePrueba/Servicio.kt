package ar.com.unlam.enlazar.model.clasesDePrueba

import com.google.gson.annotations.SerializedName

data class Servicio(
    @SerializedName("Id")val id: String,
    @SerializedName("IdRecolector")val idRecolector: String,
    @SerializedName("IdUsuario")val idUsuario: String,
    @SerializedName("Estado")val estado: Int =0,
    @SerializedName("Direccion") val direccion: Direccion
)