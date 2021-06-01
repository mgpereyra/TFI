package ar.com.unlam.enlazar.ui.recolector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MiRutaActivity : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("message")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_ruta)

        myRef.setValue("Hello, World!2")
    }


}