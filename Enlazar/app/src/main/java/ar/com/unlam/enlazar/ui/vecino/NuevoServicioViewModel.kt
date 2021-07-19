package ar.com.unlam.enlazar.ui.vecino

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ar.com.unlam.enlazar.model.Service
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*

class NuevoServicioViewModel(private val estado: SavedStateHandle) : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    var direccion: String = ""
    var localidad: String = ""
    var lat: Double? = 0.0
    var long: Double? = 0.0
    val estados = MutableLiveData<EstadoNewService>()

    fun obtenerDireccion(idUser: String) {
        db.child("User").child(idUser).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                direccion = snapshot.child("address").value.toString()
                lat = snapshot.child("latitud").value.toString().toDouble()
                long = snapshot.child("longitud").value.toString().toDouble()
                localidad = snapshot.child("locality").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun crearNuevoServicio(servicio: Service) {
        if (servicio.id != null) {
            db.child("Service").child(servicio.id).setValue(servicio)
                .addOnCompleteListener {


                }
        }
    }


    enum class EstadoNewService {
        SUCCESS,
        ERROR
    }
}