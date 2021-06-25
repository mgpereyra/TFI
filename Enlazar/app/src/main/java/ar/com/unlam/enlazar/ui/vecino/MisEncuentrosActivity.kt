package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisEncuentrosUserAdapter
import ar.com.unlam.enlazar.data.retrofit.Constants
import ar.com.unlam.enlazar.databinding.ActivityMisEncuentrosBinding
import ar.com.unlam.enlazar.model.PuntoEncuentro
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mis_encuentros.*

class MisEncuentrosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMisEncuentrosBinding
    private lateinit var adapter: MisEncuentrosUserAdapter
    var contPrueba = 0
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
                "Localidad Punto "+ contPrueba,
                "Calle punto",
                "Plaza del punto",
                "-34.746438", "-58.701689",
                1,
                "11/10/2021",
                "09 a 12 hs"
            )
            db.child("MeetingPoint").child(idencuentro).setValue(encuentro).addOnCompleteListener {

            }
    contPrueba +=1
        }
    }

    override fun onStart() {
        getMeetingPoints()
        //viewModelServices.getServices()
        setObservers()
        super.onStart()
    }

    private fun getMeetingPoints() {
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
        Toast.makeText(this,puntoEncuentro.id.toString(),Toast.LENGTH_LONG).show()
        val db = FirebaseDatabase.getInstance().getReference()
        val idUser = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val userData = db.database.getReference(Constants.USER_REF).child(idUser).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var model = snapshot.getValue(User::class.java)

                db.child("Meeting_User").child(puntoEncuentro.id.toString()).child(idUser)
                    .setValue(model!!.email).addOnCompleteListener {}

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
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

