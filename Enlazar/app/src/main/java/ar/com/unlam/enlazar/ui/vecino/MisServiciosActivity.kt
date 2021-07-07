package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.MisServiciosVecinoAdapter
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Estado
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mis_servicios.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*

class MisServiciosActivity : AppCompatActivity() {
    var id = FirebaseAuth.getInstance().currentUser!!.uid
    val viewModelMisServicios:MisServiciosViewModel by viewModels()
    var serviceList = ArrayList<Service>()
    private lateinit var adapter:MisServiciosVecinoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_servicios)

        viewModelMisServicios.getServicios(Estado.PENDIENTE)
        servicios_pendientes.setBackgroundColor(
            resources.getColor(R.color.green)
        )
        adapter = MisServiciosVecinoAdapter()
        with(listado_servicios) {
            layoutManager =
                LinearLayoutManager(this@MisServiciosActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@MisServiciosActivity.adapter
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
            adapter.servicesList.clear()
            adapter.notifyDataSetChanged()
            viewModelMisServicios.getServicios(Estado.ASIGNADO)

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
           // viewModelMisServicios.misServicios.
            adapter.notifyDataSetChanged()
            viewModelMisServicios.getServicios(Estado.PENDIENTE)

        }
        btnVolver_mis_servicios.setOnClickListener { finish() }
        setServicios()


    }
    @Override
    public override fun onResume() {
        super.onResume()
        serviceList.clear()
        adapter.notifyDataSetChanged()
        viewModelMisServicios.getServicios(Estado.ASIGNADO)

    }
   @Override
    public override fun onResume() {
        super.onResume()
       setServicios()
        viewModelMisServicios.getServicios(Estado.PENDIENTE)
    }

    override fun onStart() {
        setServicios()
        viewModelMisServicios.getServicios(Estado.PENDIENTE)
        super.onStart()
    }
    private fun setServicios() {
        viewModelMisServicios.misServicios.observe(this,{
            adapter.submitList(it)
            adapter.notifyDataSetChanged()

        })
        if (adapter.servicesList.size>0){
            listado_servicios.visibility= View.GONE
        }else{
            listado_servicios.visibility=View.VISIBLE
        }
        /*db.child("Service").addValueEventListener(object : ValueEventListener {
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
                    listado_servicios.adapter = adapter}
                else{
                    Toast.makeText(this@MisServiciosActivity,
                        "no se encontraron servicios del estado especificado",
                        Toast.LENGTH_LONG).show()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
    }


}
