package ar.com.unlam.enlazar.model

import ar.com.unlam.enlazar.ui.TipoConsejo

data class CardInfo (

var id:String,
var img: String,
var title:String,
var content:String,
var likes:Long?=0,
var tipe: String="",
var uri:String

        )