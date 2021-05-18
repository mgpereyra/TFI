package ar.com.unlam.enlazar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardView_mis_servicios.setOnClickListener{
    val intent: Intent = Intent(this, misServicios::class.java)
    startActivity(intent)
}


        cardView_proximo_servicio.setOnClickListener{
            val intent: Intent = Intent(this, DetalleServicioActivity::class.java)
            startActivity(intent)
        }
    }

}