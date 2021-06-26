package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisServiciosVecinoAdapter
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Estado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mis_servicios.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*

class MisServiciosConfirmadosActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference
    var id = FirebaseAuth.getInstance().currentUser!!.uid
    var serviceList = ArrayList<Service>()
    private lateinit var adapter:MisServiciosVecinoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_servicios)
        servicios_confirmados.setOnClickListener {

        }
        setServicios(id,Estado.PENDIENTE)
        adapter = MisServiciosVecinoAdapter()
        with(listado_servicios) {
            layoutManager =
                LinearLayoutManager(this@MisServiciosConfirmadosActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@MisServiciosConfirmadosActivity.adapter
        }
        listado_servicios.adapter = adapter
        servicios_confirmados.setOnClickListener {
            servicios_confirmados.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            servicios_pendientes.setBackgroundColor(
                resources.getColor(R.color.emerald)
            )
            servicios_confirmados.isEnabled=false
            servicios_pendientes.isEnabled=true
            serviceList.clear()
            setServicios(id,Estado.ASIGNADO)

        }

        servicios_pendientes.setOnClickListener {

            servicios_pendientes.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            servicios_confirmados.setBackgroundColor(
                resources.getColor(R.color.emerald)
            )
            servicios_confirmados.isEnabled=true
            servicios_pendientes.isEnabled=false
            serviceList.clear()
            setServicios(id,Estado.PENDIENTE)

        }
        btnVolver_mis_servicios.setOnClickListener { finish() }

    }

    private fun setServicios(idUser: String, estado: Estado) {

        db.child("Service").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // var mSnapshot=snapshot.getValue(Service::class.java)
                for (postSnapshot in snapshot.children.iterator()) {
                    if (postSnapshot.child("userId").value.toString() == idUser &&
                        postSnapshot.child("estado").value.toString().toInt()==estado.ordinal) {
                        serviceList.add(
                            Service(
                                postSnapshot.child("address").value.toString(),
                                postSnapshot.child("id").value.toString(),
                                "",
                                "",
                                postSnapshot.child("envasesPlasticos").value.toString().toInt().or(0),
                                postSnapshot.child("envasesVidrio").value.toString().toInt().or(0) ,
                                postSnapshot.child("envasesCarton").value.toString().toInt().or(0),
                                postSnapshot.child("date").value.toString(),
                                postSnapshot.child("time").value.toString(),
                                postSnapshot.child("userId").value.toString(),"",
                                postSnapshot.child("estado").value.toString().toInt()
                            )
                        )
                    }
                }
                if (serviceList.size>0){
                adapter.servicesList=serviceList.toMutableList()

                adapter.notifyDataSetChanged()
                  /*  adapter = MisServiciosVecinoAdapter()
                    with(listado_servicios) {
                        layoutManager =
                            LinearLayoutManager(this@MisServiciosConfirmadosActivity, LinearLayoutManager.VERTICAL, false)
                        this.adapter = this@MisServiciosConfirmadosActivity.adapter
                    }*/
                    listado_servicios.adapter = adapter}
                else{
                    Toast.makeText(this@MisServiciosConfirmadosActivity,
                        "no se encontraron servicios del estado especificado",
                        Toast.LENGTH_LONG).show()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}