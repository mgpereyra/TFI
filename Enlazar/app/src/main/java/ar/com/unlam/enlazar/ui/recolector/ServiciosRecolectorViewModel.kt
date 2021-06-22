package ar.com.unlam.enlazar.ui.recolector

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.data.retrofit.ServiceRecolectorRepository
import ar.com.unlam.enlazar.model.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiciosRecolectorViewModel(
    private val repository: ServiceRecolectorRepository = ServiceRecolectorRepository()

) : ViewModel() {
    val misServicios = MutableLiveData<List<Service>>()
    private lateinit var database: FirebaseDatabase
    //  private lateinit var referaceUsuario: DatabaseReference
    private lateinit var referaceServicio: DatabaseReference

    init {
        viewModelScope.launch {
            try {
                getDataBase()
            } catch (e: Exception) {
                Log.e("Cancel", e.toString())
            }
        }
    }

    private fun getDataBase() {
        val idRecolector = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()

        database = FirebaseDatabase.getInstance()
        referaceServicio = database.getReference("Service")

        referaceServicio.orderByChild("recolectorId").equalTo(idRecolector)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list = ArrayList<Service>()
                    for (data in snapshot.children) {
                        var model = data.getValue(Service::class.java)
                        if (model != null) {
                            if (model.estado==1){
                                list.add(model)
                            }
                        }
                    }
                    misServicios.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel", error.toString())
                }
            })
    }

 /*   val responseLiveData = liveData(Dispatchers.IO) {
        emit(repository.getResponseFromRealtimeDatabaseUsingCoroutines())

    }*/


}

/*   fun getServices() {
        getDataBase()
        val idRecolector = "-Mbb-PGvlAiZykBDUIIj"
        referaceServicio.orderByChild("recolectorId").equalTo(idRecolector).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                json = it.value.toString()
                Log.d("jsonString: ",json)
                val hola:Services=gson.fromJson(json,Services::class.java)
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }*/
/*           .addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {

                   if (snapshot.exists()) {
                       snapshot.let { it ->
                           var prueba1= it.children.map { snapshot -> snapshot.getValue(Services::class.java)!! }
                           var prueba = it.child("address").getValue().toString()
                       }
                   }
               }


               override fun onCancelled(error: DatabaseError) {
                   Log.d("error", "Hubo un error en la busqueda ")
               }
           })*/