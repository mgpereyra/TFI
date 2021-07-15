package ar.com.unlam.enlazar.data.retrofit

import android.util.Log
import ar.com.unlam.enlazar.data.retrofit.repository.ServiciosRecRepository
import ar.com.unlam.enlazar.model.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ServiciosRecRepositoryImpl : ServiciosRecRepository {
    private lateinit var database: FirebaseDatabase
    private lateinit var referaceServicio: DatabaseReference
    private var  list = ArrayList<Service>()
override fun retList(): ArrayList<Service>{
    return  list
}
    override suspend  fun getServiciosPendientes (){
        val idRecolector = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        database = FirebaseDatabase.getInstance()
        referaceServicio = database.getReference("Service")


        referaceServicio.orderByChild("recolectorId").equalTo(idRecolector)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list2 = ArrayList<Service>()

                    for (data in snapshot.children) {
                        var model = data.getValue(Service::class.java)
                        if (model != null) {
                            if (model.estado==1){
                                list2.add(model)
                            }
                        }
                    }
                 list = list2
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel", error.toString())
                }
            })

    }
}