package ar.com.unlam.enlazar.ui.recolector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.DetalleServicioActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mi_ruta.*

class MiRutaActivity : AppCompatActivity() {
    //val database = Firebase.database
    //val myRef = database.getReference("message")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_ruta)
        buttonIrMapa.setOnClickListener{
            val intent: Intent = Intent(this, MapRutaRecolectorActivity::class.java)
            startActivity(intent)
            val analytics = FirebaseAnalytics.getInstance(this)
            val bundle=Bundle()
            bundle.putString("message", "integracion de firebase completa")
            analytics.logEvent("InitScreen",bundle)
        }
      //  myRef.setValue("Hello, World!2")
    }


}