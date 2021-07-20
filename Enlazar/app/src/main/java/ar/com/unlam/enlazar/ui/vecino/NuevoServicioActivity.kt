package ar.com.unlam.enlazar.ui.vecino

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Estado
import ar.com.unlam.enlazar.ui.LoginActivity
import ar.com.unlam.enlazar.ui.pickers.DatePickerFragent
import ar.com.unlam.enlazar.ui.pickers.TimePickerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_dashboard_recolector.*
import kotlinx.android.synthetic.main.activity_dashboard_usuario.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.btnVolver_NuevoServicio
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class NuevoServicioActivity : AppCompatActivity() {
    var u: String = ""
    var lat: Double? = 0.0
    var long: Double? = 0.0
    private val db = FirebaseDatabase.getInstance().getReference()
    var id = FirebaseAuth.getInstance().currentUser!!.uid

    val newServiceViewModel: NewServiceViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_servicio)
        setDirection(id)
        dia_picker.setOnClickListener { showDatePicker() }
        horario_picker.setOnClickListener { showTimePicker() }
        btn_finalizar.setOnClickListener {
            createService()
        }
        //   setObservers()
        // toolbar()
        btnVolver_NuevoServicio.setOnClickListener {
            finish()

        }

    }

    private fun showTimePicker() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelected(time: String) {

        horario_picker.setText(time)
    }

    private fun logOut() {
        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE).edit()
        pref.clear()
        pref.apply()
        FirebaseAuth.getInstance().signOut()
        val mainIntent = Intent(this, LoginActivity::class.java)
        startActivity(mainIntent)
    }

    private fun setDirection(idForLocation: String) {
        db.child("User").child(idForLocation).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                u = snapshot.child("address").value.toString()
                lat = snapshot.child("latitud").value.toString().toDouble()
                long = snapshot.child("longitud").value.toString().toDouble()
                ubicacion.editText?.setText(u)
                localidad.editText?.setText(snapshot.child("locality").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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
                    u,
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
                if (serviceId != null) {
                    db.child("Service").child(serviceId).setValue(service)
                        .addOnCompleteListener {
                            Toast.makeText(
                                this,
                                "Tu Servicio ha sido registrado correctamente",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            irMisServiciosActivity()
                        }
                }
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
        val misServiciosActivity = Intent(this, MisServiciosActivity::class.java)
        this.startActivity(misServiciosActivity)
        this@NuevoServicioActivity.finish()
        startActivity(misServiciosActivity)
    }

    private fun setObservers() {
        newServiceViewModel.estados.observe(this, { estado(it) })
    }

    private fun estado(status: NewServiceViewModel.EstadoNewService) {
        when (status) {
            NewServiceViewModel.EstadoNewService.SUCCESS -> Toast.makeText(
                this@NuevoServicioActivity,
                getString(R.string.succes), Toast.LENGTH_LONG
            ).show()
            NewServiceViewModel.EstadoNewService.ERROR -> Toast.makeText(
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

        }

    }*/
/*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recolector , menu)
        //menu?.findItem(R.id.mis_direcciones_user)?.setVisible(false)
        toolbar_nuevo_servicio.setNavigationIcon(null)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.mis_canjes_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.invita_amigos_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.guardado_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }

            R.id.mi_cuenta_rec -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
            }
            R.id.logout_rec -> {
                logOut()
            }
        }
        return super.onOptionsItemSelected(item)

    }*/