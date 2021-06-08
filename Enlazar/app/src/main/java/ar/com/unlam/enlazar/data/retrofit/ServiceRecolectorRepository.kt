package ar.com.unlam.enlazar.data.retrofit

import ar.com.unlam.enlazar.model.ResponseServicios
import ar.com.unlam.enlazar.model.Services
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class ServiceRecolectorRepository(private val rootRef:DatabaseReference=FirebaseDatabase.getInstance().reference,
private val serviceReference:DatabaseReference=rootRef.child(Constants.SERVICE_REF)) {


    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines2(): ResponseServicios {
        val response = ResponseServicios()
        try {
            response.listService = serviceReference.get().await().children.map { snapShot ->
                snapShot.getValue(Services::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines(): ResponseServicios {
        val response = ResponseServicios()
        val idRecolector = "-Mbb-PGvlAiZykBDUIIj"
        try {
            response.listService = serviceReference.orderByChild("RecolectorId").equalTo(idRecolector).get().await().children.map { snapShot ->
                snapShot.getValue(Services::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }
/*    fun getServices() {
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
}