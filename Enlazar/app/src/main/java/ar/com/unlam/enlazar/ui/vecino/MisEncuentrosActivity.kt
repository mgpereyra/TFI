package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisEncuentrosUserAdapter
import ar.com.unlam.enlazar.adapter.MisServiciosRecolectorAdapter
import ar.com.unlam.enlazar.databinding.ActivityMisEncuentrosBinding
import ar.com.unlam.enlazar.databinding.ActivityServiciosRecolectorRutaBinding
import ar.com.unlam.enlazar.model.PuntoEncuentro
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.recolector.RutaRecolectorMapActivity
import ar.com.unlam.enlazar.ui.recolector.ServiciosRecolectorViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_mis_encuentros.*

class MisEncuentrosActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMisEncuentrosBinding
    private lateinit var adapter: MisEncuentrosUserAdapter
    private val viewModelMisPuntosEncuentros: MisEncuentrosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_encuentros)

        binding = ActivityMisEncuentrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MisEncuentrosUserAdapter { encuentro -> toOnItemViewClick(encuentro) }
        with(binding.rvListEncuentros) {
            layoutManager =
                LinearLayoutManager(
                    this@MisEncuentrosActivity,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                    false
                )
            this.adapter = this@MisEncuentrosActivity.adapter
        }


        setListeners()

    }

    private fun setListeners() {
        crear_punto.setOnClickListener {
            val db = FirebaseDatabase.getInstance().getReference()
            var idencuentro = db.push().key.toString()
            val encuentro = PuntoEncuentro(
                idencuentro,
                "Esta es la desc del primer punto de encuentro",
                "Localidad Punto",
                "Calle punto",
                "Plaza del punto",
                1,
                "11/10/2021",
                "09 a 12 hs"
            )
            db.child("MeetingPoint").child(idencuentro).setValue(encuentro).addOnCompleteListener {

            }

        }
    }


    override fun onStart() {
        getServicesResponde2()
        //viewModelServices.getServices()
        setObservers()
        super.onStart()
    }

    private fun getServicesResponde2() {
        viewModelMisPuntosEncuentros.misPuntosEncuentro.observe(this, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setObservers() {
        viewModelMisPuntosEncuentros.misPuntosEncuentro.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun toOnItemViewClick(puntoEncuentro: PuntoEncuentro) {
 /*       if(servicio.estado==3){
            Toast.makeText(this, "no se puede ir a servicio finalizado loco", Toast.LENGTH_LONG).show()
        }else{

            val intent = Intent(this, RutaRecolectorMapActivity::class.java)
            intent.putExtra("idService", servicio.id)
            intent.putExtra("lat", servicio.latitud)
            intent.putExtra("lon", servicio.longitud)
            intent.putExtra("currentlat", mCurrentLatLng.latitude.toString())
            intent.putExtra("currentlon", mCurrentLatLng.longitude.toString())
            intent.putExtra("address", servicio.address)
            intent.putExtra("Service",servicio)
            startActivity(intent)
        }*/

    }
}