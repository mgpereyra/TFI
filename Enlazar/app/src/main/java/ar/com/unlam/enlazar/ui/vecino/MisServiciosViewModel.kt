package ar.com.unlam.enlazar.ui.vecino

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Estado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MisServiciosViewModel(): ViewModel() {
    var misServicios=MutableLiveData<List<Service>>()
    private var db=FirebaseDatabase.getInstance().reference
    var id = FirebaseAuth.getInstance().currentUser!!.uid

    init {
        try {
            getServicios(Estado.PENDIENTE)
        }catch (e:Exception){
            Log.e("Cancel", e.toString())
        }
    }
    fun getServicios(estado: Estado){
        db.child("Service").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list=ArrayList<Service>()
                // var mSnapshot=snapshot.getValue(Service::class.java)
                for (postSnapshot in snapshot.children.iterator()) {
                    var model=postSnapshot.getValue(Service::class.java)
                    if (postSnapshot.child("userId").value.toString() == id &&
                        postSnapshot.child("estado").value.toString().toInt()==estado.ordinal) {
                        if (model != null) {
                            list.add(model)
                        }
                        /*serviceList.add(
                            Service(
                                postSnapshot.child("address").value.toString(),
                                postSnapshot.child("id").value.toString(),
                                "",
                                "",
                                postSnapshot.child("envasesPlasticos").value.toString().toInt().or(0),
                                postSnapshot.child("envasesVidrio").value.toString().toInt().or(0) ,
                                postSnapshot.child("envasesCarton").value.toString().toInt().or(0),
                                postSnapshot.child("date").value.toString(),
                                postSnapshot.child("time").value.toString(),
                                postSnapshot.child("userId").value.toString(),"",
                                postSnapshot.child("estado").value.toString().toInt()
                            )
                        )*/
                    }
                }
                misServicios.value=list
           /*     if (list.size>0) {


                }*/
            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())

            }
        })
    }

    }

