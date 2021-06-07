package ar.com.unlam.enlazar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Services
import ar.com.unlam.enlazar.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*
import kotlinx.android.synthetic.main.activity_nuevo_servicio.btnVolver
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class NuevoServicioActivity : AppCompatActivity() {
    var id:String=""
     var u:String=""
    var lat:Double=0.0
    var long:Double=0.0
    private val db= FirebaseDatabase.getInstance().getReference()
    val newServiceViewModel: NewServiceViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_servicio)
        if (intent.hasExtra(ID)) {
            id= intent.extras!!.getString(ID, "").toString()
            setDirection(id)
        }
btn_finalizar.setOnClickListener {
    createService()

}
     //   setObservers()
        toolbar()
btnVolver.setOnClickListener {
    btnVolver.setOnClickListener {
        this@NuevoServicioActivity.finish()
    }
}

    }

    private fun setDirection(idForLocation:String) {
db.child("User").child(idForLocation).addValueEventListener(object:ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        u=snapshot.child("address").value.toString()
        lat=snapshot.child("latitud").value.toString().toDouble()
        long=snapshot.child("longitud").value.toString().toDouble()
        ubicacion.editText?.setText(u)
        localidad.editText?.setText(snapshot.child("locality").value.toString())
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }
})
    }

    fun toolbar(){
        setSupportActionBar(toolbar2)
        var ab: ActionBar?=supportActionBar
        if (ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.menu)
            ab.setDisplayHomeAsUpEnabled(true)

        }

    }
    private fun createService(){
        /*val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")*/

        id= FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
       var serviceId= db.push().key.toString()
        var service= Services(serviceId,lat,long,cant_tipo1.editText?.text.toString().toIntOrNull(),
        cant_tipo2.editText?.text.toString().toIntOrNull(),cant_tipo3.editText?.text.toString().toIntOrNull(),
            Date(),Date(),id,"")
        if (serviceId!= null) {
            db.child("Service").child(serviceId).setValue(service).addOnCompleteListener{
                Toast.makeText(this, "Tu Servicio ha sido registrado correctamente",Toast.LENGTH_LONG).show()

            }
        }


    }
    private fun setObservers() {
        newServiceViewModel.estados.observe(this,{estado(it)})

    }
    private fun estado(status: NewServiceViewModel.EstadoNewService) {
        when (status) {
            NewServiceViewModel.EstadoNewService.SUCCESS -> Toast.makeText(this@NuevoServicioActivity,
                getString(R.string.succes), Toast.LENGTH_LONG).show()
            NewServiceViewModel.EstadoNewService.ERROR -> Toast.makeText(this@NuevoServicioActivity,
                getString(R.string.error), Toast.LENGTH_LONG).show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                drawerNewService.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    companion object {
        val ID: String = "id"
    }
}