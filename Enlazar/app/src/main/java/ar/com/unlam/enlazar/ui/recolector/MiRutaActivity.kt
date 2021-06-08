package ar.com.unlam.enlazar.ui.recolector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisServiciosRecolectorAdapter
import ar.com.unlam.enlazar.model.clasesDePrueba.Direccion
import ar.com.unlam.enlazar.model.clasesDePrueba.PuntoLatLong
import ar.com.unlam.enlazar.model.clasesDePrueba.Recolector
import ar.com.unlam.enlazar.model.clasesDePrueba.Servicio
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_mi_ruta.*

class MiRutaActivity : AppCompatActivity() {
   //lateinit var  database :DatabaseReference
   private lateinit var adapter: MisServiciosRecolectorAdapter
    private val viewModelServices: MiRutaViewModel by viewModels()


    private lateinit var database: FirebaseDatabase
    private lateinit var referaceUsuario:DatabaseReference
    private lateinit var referaceServicio:DatabaseReference

    val puntoLatLOng= PuntoLatLong(-34.744774, -58.695204)
    val direccion = Direccion("","Pontevedra","Azul",4097,puntoLatLOng)
    val listaServicio :ArrayList<Servicio> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_ruta)
        buttonIrMapa.setOnClickListener{


            referaceServicio.orderByChild("idRecolector").equalTo("Iduno").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
           /* val intent: Intent = Intent(this, MapRutaRecolectorActivity::class.java)
            startActivity(intent)*/
        }


            var recolector = Recolector("Iduno","NombreRecolector1",)

        val servicioUno = Servicio("",recolector.id,"IdUsuarioUno",0,direccion)

        database = FirebaseDatabase.getInstance()
            referaceUsuario = database.getReference("UsuarioKotlin")
        referaceServicio= database.getReference("ServicioKotlin")
            buttonRutas.setOnClickListener {

             /*   referaceUsuario.push().setValue(recolector).addOnSuccessListener {
                    Toast.makeText(this,"Operacion1 exitosa",Toast.LENGTH_SHORT).show()
                }*/
                referaceServicio.push().setValue(servicioUno).addOnSuccessListener {
                    Toast.makeText(this,"Operacion2 exitosa",Toast.LENGTH_SHORT).show()

                }

            }
    }

    /*  val analytics = FirebaseAnalytics.getInstance(this)
            val bundle=Bundle()
            bundle.putString("message", "integracion de firebase completa")
            analytics.logEvent("InitScreen",bundle)*/
}