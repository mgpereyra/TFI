package ar.com.unlam.enlazar.ui.vecino

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.model.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_nuevo_servicio.*
import kotlinx.coroutines.launch

class NuevoServicioViewModel(private val estado: SavedStateHandle) : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    var direccion=MutableLiveData<String>()
    var localidad=MutableLiveData<String>()
    var lat=MutableLiveData<Double>()
    var long=MutableLiveData<Double>()
    var id = FirebaseAuth.getInstance().currentUser!!.uid

    val estados = MutableLiveData<EstadoNewService>()
    val estadosServicio = MutableLiveData<EstadoNewService>()

    /*init {
        viewModelScope.launch{
        obtenerDireccion(id)
        }
    }*/
    fun obtenerDireccion(idUser: String) {
        viewModelScope.launch {
            db.child("User").child(idUser).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    direccion.value = snapshot.child("address").value.toString()
                    lat.value = snapshot.child("latitud").value.toString().toDouble()
                    long.value = snapshot.child("longitud").value.toString().toDouble()
                    localidad.value = snapshot.child("locality").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    estados.value=EstadoNewService.ERROR
                }
            })
        }
    }

    fun crearNuevoServicio(servicio: Service) {
        viewModelScope.launch {
            if (servicio.id != null) {
                db.child("Service").child(servicio.id).setValue(servicio)
                    .addOnCompleteListener {
                        estadosServicio.value=EstadoNewService.SUCCESS

                    }
            }
        }
    }


    enum class EstadoNewService {
        SUCCESS,
        ERROR
    }
}