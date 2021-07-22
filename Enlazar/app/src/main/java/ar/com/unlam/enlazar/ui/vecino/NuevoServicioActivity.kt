package ar.com.unlam.enlazar.ui.vecino


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ar.com.unlam.enlazar.R
import androidx.lifecycle.Observer
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Estado
import ar.com.unlam.enlazar.ui.pickers.DatePickerFragent
import ar.com.unlam.enlazar.ui.pickers.TimePickerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*

import java.util.*

class NuevoServicioActivity : AppCompatActivity() {
    var lat: Double? = 0.0
    var long: Double? = 0.0
    private val db = FirebaseDatabase.getInstance().getReference()
    var id = FirebaseAuth.getInstance().currentUser!!.uid

    val newServiceViewModel: NuevoServicioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_servicio)

        getDirection(id)
        dia_picker.setOnClickListener { showDatePicker() }
        horario_picker.setOnClickListener { showTimePicker() }
        btn_finalizar.setOnClickListener {
            createService()
        }
        //   setObservers()


        btnVolver_NuevoServicio.setOnClickListener {
            finish()

        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
    private fun showTimePicker() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelected(time: String) {

        horario_picker.setText(time)
    }



    private fun getDirection(idForLocation: String) {
        newServiceViewModel.obtenerDireccion(idForLocation)
        newServiceViewModel.direccion.observe(this, Observer { setObserveDireccion(it) })
        newServiceViewModel.localidad.observe(this, Observer { setObserveLocalidad(it) })
        newServiceViewModel.lat.observe(this, Observer { setObserverLat(it) })
        newServiceViewModel.long.observe(this, Observer { setObserverLong(it) })

    }

    private fun setObserverLong(it: Double?) {
        long = it
    }


    private fun setObserverLat(it: Double?) {
        lat = it
    }


    private fun setObserveLocalidad(it: String?) {
        localidad.editText?.setText(it)
    }

    private fun setObserveDireccion(it: String?) {
        ubicacion.editText?.setText(it)

    }


    private fun createService() {
        if (cant_tipo1.editText?.text.toString()
                .isNotEmpty() || cant_tipo2.editText?.text.toString()
                .isNotEmpty() || cant_tipo3.editText?.text.toString().isNotEmpty()
        ) {
            if (dia_picker.text.isNotEmpty() && horario_picker.text.isNotEmpty()) {
                if (cant_tipo1.editText?.text.toString().isEmpty()) {
                    cant_tipo1.editText?.setText("0")
                }
                if (cant_tipo2.editText?.text.toString().isEmpty()) {
                    cant_tipo2.editText?.setText("0")
                }
                if (cant_tipo3.editText?.text.toString().isEmpty()) {
                    cant_tipo3.editText?.setText("0")
                }

                var serviceId = db.push().key.toString()
                var service = Service(
                    ubicacion.editText?.text.toString(),
                    serviceId,
                    lat.toString(),
                    long.toString(),
                    cant_tipo1.editText?.text.toString().toInt(),
                    cant_tipo2.editText?.text.toString().toInt(),
                    cant_tipo3.editText?.text.toString().toInt(),
                    dia_picker.text.toString(),
                    horario_picker.text.toString(), "",
                    id,
                    "",
                    Estado.PENDIENTE.ordinal
                )
                newServiceViewModel.crearNuevoServicio(service)
                irMisServiciosActivity()
            } else {

                Toast.makeText(
                    this,
                    "Debes completar los datos de Fecha y Horario",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "Debes tener al menos una bolsa con material para reciclar",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun irMisServiciosActivity() {
        finish()
    }
    
    private fun irDashboardUserActivity() {
        val darsheboardActivity = Intent(this, DashboardUserActivity::class.java)

        this.startActivity(darsheboardActivity)
        this@NuevoServicioActivity.finish()
        startActivity(darsheboardActivity)
    }

    private fun setObservers() {
        newServiceViewModel.estados.observe(this, { estado(it) })

    }




    private fun estado(status: NuevoServicioViewModel.EstadoNewService) {
        when (status) {
            NuevoServicioViewModel.EstadoNewService.SUCCESS -> Toast.makeText(
                this@NuevoServicioActivity,
                getString(R.string.succes), Toast.LENGTH_LONG
            ).show()
            NuevoServicioViewModel.EstadoNewService.ERROR -> Toast.makeText(
                this@NuevoServicioActivity,
                getString(R.string.error), Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerFragent { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        dia_picker.setText("$day/$month/$year")

    }

    companion object {
        val ID: String = "id"

    }
}

/*    fun toolbar() {
        setSupportActionBar(toolbar_nuevo_servicio)
        var ab: ActionBar? = supportActionBar
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.menu)
            ab.setDisplayHomeAsUpEnabled(true)


*/