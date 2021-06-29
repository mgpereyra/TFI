package ar.com.unlam.enlazar.ui.vecino

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.model.PuntoEncuentro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class PuntosEncuentroMapViewModel: ViewModel() {
    val misPuntosEncuentro = MutableLiveData<List<PuntoEncuentro>>()
    private lateinit var database: FirebaseDatabase
    //  private lateinit var referaceUsuario: DatabaseReference
    private lateinit var referaceMeetingPoint: DatabaseReference

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
        val idVecinoCurrent= FirebaseAuth.getInstance().getCurrentUser()!!.getUid()

        database = FirebaseDatabase.getInstance()
        referaceMeetingPoint= database.getReference("MeetingPoint")
        referaceMeetingPoint.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<PuntoEncuentro>()
                for (data in snapshot.children) {
                    var model = data.getValue(PuntoEncuentro::class.java)
                    if (model != null) {
                        list.add(model)
                    }

                }
                misPuntosEncuentro.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }

    private fun getDataBaseBk() {
        val idVecinoCurrent= FirebaseAuth.getInstance().getCurrentUser()!!.getUid()

        database = FirebaseDatabase.getInstance()
        referaceMeetingPoint= database.getReference("MeetingPoint")

        // referaceMeetingPoint.orderByChild("recolectorId").equalTo(idVecinoCurrent)
        referaceMeetingPoint.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<PuntoEncuentro>()
                for (data in snapshot.children) {
                    var model = data.getValue(PuntoEncuentro::class.java)
                    if (model != null) {
                        list.add(model)
                    }

                }
                misPuntosEncuentro.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }

}