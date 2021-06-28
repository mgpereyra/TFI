package ar.com.unlam.enlazar.model

data class Item(
    val title: String = "",
    val id: String = "",
    val description: String = "",
    val amount: Int = 0,
    val imageCode: String = "",
    val pointsCost:Int=0,
    val image:String="default"


)