package ar.com.unlam.enlazar.ui.recolector

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.liveData

import androidx.lifecycle.MutableLiveData
import ar.com.unlam.enlazar.data.retrofit.ServiceRecolectorRepository
import ar.com.unlam.enlazar.model.Services
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers

class MiRutaViewModel(
    private val repository: ServiceRecolectorRepository = ServiceRecolectorRepository()

) : ViewModel() {
    val misServicios = MutableLiveData<List<Services>>()
    private lateinit var database: FirebaseDatabase
  //  private lateinit var referaceUsuario: DatabaseReference
    private lateinit var referaceServicio: DatabaseReference

    private fun getDataBase() {
        database = FirebaseDatabase.getInstance()
        //   referaceUsuario = database.getReference("UsuarioKotlin")
        referaceServicio = database.getReference("Service")
    }

    val responseLiveData = liveData(Dispatchers.IO) {
        emit(repository.getResponseFromRealtimeDatabaseUsingCoroutines())

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


}

