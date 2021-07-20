package ar.com.unlam.enlazar.data.retrofit

import ar.com.unlam.enlazar.model.ResponseServicios
import ar.com.unlam.enlazar.model.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

//Alternatina a implementar en futuras versiones
class ServiceRecolectorRepository(private val rootRef:DatabaseReference=FirebaseDatabase.getInstance().reference,
private val serviceReference:DatabaseReference=rootRef.child(Constants.SERVICE_REF)) {


    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines2(): ResponseServicios {
        val response = ResponseServicios()
        try {
            response.listService = serviceReference.get().await().children.map { snapShot ->
                snapShot.getValue(Service::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines(): ResponseServicios {
        val idRecolector = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        val response = ResponseServicios()
        try {
            response.listService = serviceReference.orderByChild("recolectorId").equalTo(idRecolector).get().await().children.map { snapShot ->
                snapShot.getValue(Service::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

}