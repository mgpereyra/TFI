package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R

class DetalleCardInfoActivity : AppCompatActivity() {
    var id: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_card_info)



    }
    companion object {
        val ID: String = "id"
    }
}