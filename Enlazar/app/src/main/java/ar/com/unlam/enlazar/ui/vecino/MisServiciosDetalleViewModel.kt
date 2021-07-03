package ar.com.unlam.enlazar.ui.vecino

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Consulta
import ar.com.unlam.enlazar.ui.Estado
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MisServiciosDetalleViewModel() : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    var serviceLiveData = MutableLiveData<Service>()
    val estado = MutableLiveData<Consulta>()
    val con=MutableLiveData<Consulta>()
    fun getServiceById(id: String) {
        viewModelScope.launch {
            db.child("Service").child(id).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var model = snapshot.getValue(Service::class.java)
                    serviceLiveData.value = model
                    estado.value=Consulta.SUCCESS
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel", error.toString())
                    estado.value=Consulta.ERROR
                }


            })

        }
    }
    fun cancelService(id: String){
        viewModelScope.launch {
        db.child("Service").child(id).child("estado").setValue(Estado.CANCELADO.ordinal)
            .addOnCompleteListener {
                con.value=Consulta.SUCCESS
            }
        }
    }
}