package ar.com.unlam.enlazar.ui.vecino

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisEncuentrosUserAdapter
import ar.com.unlam.enlazar.data.retrofit.Constants
import ar.com.unlam.enlazar.databinding.ActivityMisEncuentrosBinding
import ar.com.unlam.enlazar.model.PuntoEncuentro
import ar.com.unlam.enlazar.model.User
import ar.com.unlam.enlazar.ui.recolector.RutaRecolectorMapActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mis_encuentros.*

class MisEncuentrosActivity : AppCompatActivity(), MisEncuentrosUserAdapter.OnRecyclerItemClick {
    private lateinit var binding: ActivityMisEncuentrosBinding
    private val viewModelMisPuntosEncuentros: MisEncuentrosViewModel by viewModels()
    private lateinit var recyclerAdapter: MisEncuentrosUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_encuentros)

        binding = ActivityMisEncuentrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv_list_encuentros.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = MisEncuentrosUserAdapter(this@MisEncuentrosActivity)
        rv_list_encuentros.adapter = recyclerAdapter
         toolbar.title = "Puntos de encuentro"
        setListeners()

    }

    private fun setListeners() {
        btnVolver.setOnClickListener {
            finish()

        }
        irMisPuntosEncuentroMap.setOnClickListener {
            val intent = Intent(this, PuntosEncuentroMapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
    override fun onStart() {
        getMeetingPoints()
        super.onStart()
    }
    private fun irDashboardUserActivity() {
      finish()
    }
    private fun getMeetingPoints() {
        viewModelMisPuntosEncuentros.misPuntosEncuentro.observe(this, {
            recyclerAdapter.submitList(it)
            recyclerAdapter.notifyDataSetChanged()
        })
    }

    override fun onItemClickListener(puntoEncuentro: PuntoEncuentro, Action: Int) {
        val db = FirebaseDatabase.getInstance().getReference()
        val idUser = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE)
        val emailUser = pref.getString("email",null)


        if (Action == Constants.ASISTIR) {
            db.database.getReference(Constants.USER_REF).child(idUser)
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var currentUser = snapshot.getValue(User::class.java)
                        db.database.getReference("MeetingPoint").child(puntoEncuentro.id!!).child("asistentes").child(idUser).setValue(emailUser)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@MisEncuentrosActivity,
                            "Ocurrió un error con su confirmación",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            Toast.makeText(
                this@MisEncuentrosActivity,
                "Su asistencia fue confirmada correctamente",
                Toast.LENGTH_LONG
            ).show()
        } else if (Action == Constants.CANCELAR_ASISTENCIA) {
            db.database.getReference(Constants.USER_REF).child(idUser)
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var currentUser = snapshot.getValue(User::class.java)
                        db.child("MeetingPoint").child(puntoEncuentro.id.toString()).child("asistentes").child(
                            currentUser?.id.toString())
                            .removeValue()



                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@MisEncuentrosActivity,
                            "Ocurrió un error con su cancelacion",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            Toast.makeText(
                this@MisEncuentrosActivity,
                "Su asistencia fue cancelada exitosamente",
                Toast.LENGTH_LONG
            ).show()
        }

    }


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
/*    private fun toOnItemViewClick(puntoEncuentro: PuntoEncuentro,Action:String) {
        //Toast.makeText(this,puntoEncuentro.id.toString(),Toast.LENGTH_LONG).show()
        val db = FirebaseDatabase.getInstance().getReference()
        val idUser = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        db.database.getReference(Constants.USER_REF).child(idUser).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var model = snapshot.getValue(User::class.java)
                db.child("Meeting_User").child(puntoEncuentro.id.toString()).child(idUser)
                    .setValue(model!!.email).addOnCompleteListener {
                        Toast.makeText(this@MisEncuentrosActivity, "Su asistencia fue ah sido confimada exitosamente", Toast.LENGTH_LONG).show()
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MisEncuentrosActivity, "Ocurrió un error con su confirmacion", Toast.LENGTH_LONG).show()
            }

        })
    }*/

/* CANCELAR ASISTENCIA CON LISTA NN
else if (Action == Constants.CANCELAR_ASISTENCIA) {
    db.database.getReference(Constants.USER_REF).child(idUser)
        .addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser = snapshot.getValue(User::class.java)
                db.child("Meeting_User").child(puntoEncuentro.id.toString()).child(idUser)
                    .removeValue()
                    .addOnCompleteListener {
                        Toast.makeText(
                            this@MisEncuentrosActivity,
                            "Su asistencia fue removida exitosamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MisEncuentrosActivity,
                    "Ocurrió un error con su cancelacion",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
}*/
/*      crear_punto.setOnClickListener {


           val db = FirebaseDatabase.getInstance().getReference()
           var idencuentro = db.push().key.toString()
           val idUser = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()

*//*           db.database.getReference(Constants.USER_REF).child(idUser)
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var currentUser = snapshot.getValue(User::class.java)
                        val lista = ArrayList<String>()
                        if (currentUser != null) {
                            lista.add(currentUser.id.toString())
                        }
                        val encuentro = PuntoEncuentro(
                            lista,
                            idencuentro,
                            "Esta es la desc del primer punto de encuentro",
                            "Localidad Punto " + contPrueba,
                            "Calle punto",
                            "Plaza del punto",
                            "-34.746438", "-58.701689",
                            1,
                            "11/10/2021",
                            "09 a 12 hs"
                        )
                        db.child("MeetingPoint").child(idencuentro).setValue(encuentro).addOnCompleteListener {

                        }
                        contPrueba += 1

                    *//**//*    db.child("Meeting_User").child(puntoEncuentro.id.toString()).child(idUser)
                            .setValue(currentUser!!.email).addOnCompleteListener {
                                Toast.makeText(
                                    this@MisEncuentrosActivity,
                                    "Su asistencia fue confimada exitosamente",
                                    Toast.LENGTH_LONG
                                ).show()

                            }*//**//*
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@MisEncuentrosActivity,
                            "Ocurrió un error con su confirmacion",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })*//*




        }*/