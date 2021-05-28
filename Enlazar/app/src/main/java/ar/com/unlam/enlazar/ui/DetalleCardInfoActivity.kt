package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_detalle_card_info.*

class DetalleCardInfoActivity : AppCompatActivity() {
    var id: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_card_info)
        btnVolver.setOnClickListener { this@DetalleCardInfoActivity.finish() }


    }
    companion object {
        val ID: String = "id"
    }
}