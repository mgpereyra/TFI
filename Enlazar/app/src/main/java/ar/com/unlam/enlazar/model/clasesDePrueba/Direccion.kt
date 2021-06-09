package ar.com.unlam.enlazar.model.clasesDePrueba

data class Direccion (val id :String,
                      val localidad:String,
                      val calle:String,
                      val altura:Int,
                      val puntoLatLong: PuntoLatLong
)
