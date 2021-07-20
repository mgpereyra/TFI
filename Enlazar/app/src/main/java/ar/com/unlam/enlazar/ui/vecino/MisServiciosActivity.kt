package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
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
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_mis_servicios.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*

class MisServiciosActivity : AppCompatActivity() {
    var id = FirebaseAuth.getInstance().currentUser!!.uid
    val viewModelMisServicios: MisServiciosViewModel by viewModels()
    var serviceList = ArrayList<Service>()
    private lateinit var adapter: MisServiciosVecinoAdapter


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
            servicios_confirmados.isEnabled = false
            servicios_pendientes.isEnabled = true
            viewModelMisServicios.getServicios(Estado.ASIGNADO)
            setServicios()
        }
        btn_new_service.setOnClickListener {

            val intent = Intent(this, NuevoServicioActivity::class.java)
            intent.putExtra(NuevoServicioActivity.ID, id)
            id
            this.startActivity(intent)
        }
        servicios_pendientes.setOnClickListener {
            servicios_pendientes.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            servicios_confirmados.setBackgroundColor(
                resources.getColor(R.color.emerald)
            )
            servicios_confirmados.isEnabled = true
            servicios_pendientes.isEnabled = false
            viewModelMisServicios.getServicios(Estado.PENDIENTE)
            setServicios()
        }
        btnVolver_mis_servicios.setOnClickListener { finish() }
        setServicios()
    }

    override fun onResume() {
        super.onResume()
        setServicios()
        adapter.notifyDataSetChanged()

    }

    override fun onStart() {
        setServicios()
        super.onStart()
    }

    private fun setServicios() {
        viewModelMisServicios.misServicios.observe(this, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()

            if (viewModelMisServicios.misServicios.value!!.size > 0) {
                no_hay_servicios.visibility = View.GONE
                listado_servicios.visibility = View.VISIBLE
            } else {
                listado_servicios.visibility = View.GONE
                no_hay_servicios.visibility = View.VISIBLE
            }

            })
        }
    }