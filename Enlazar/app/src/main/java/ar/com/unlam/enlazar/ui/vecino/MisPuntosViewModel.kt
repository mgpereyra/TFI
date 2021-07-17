package ar.com.unlam.enlazar.ui.vecino

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.ui.ValorMaterial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch


class MisPuntosViewModel() : ViewModel() {
    var id = FirebaseAuth.getInstance().currentUser!!.uid
    private val db = FirebaseDatabase.getInstance().reference
    var misCanjes = MutableLiveData<List<CuponCanje>>()
    var misPuntos=MutableLiveData<Int>()
    var puntos_vidrio = 0
    var puntos_papel = 0
    var puntos_plastico = 0
    var total = 0
    var sumatoria = 0

    init {
        viewModelScope.launch {
            cargarDatos(id)
            calcularPuntos(id)
        }
    }
    fun cargarDatos(id: String) {
        viewModelScope.launch {
            db.child("User").child(id).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    var mis_canjes = ArrayList<CuponCanje>()
                    misPuntos.value = snapshot.child("puntos").value.toString().toInt()
                    for (canje in snapshot.child("Cupon").children) {
                        var model = canje.getValue(CuponCanje::class.java)
                        if (model != null) {
                            mis_canjes.add(model)
                        }
                    }
                    if (mis_canjes.size > 0) {
                        misCanjes.value = mis_canjes
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel", error.toString())
                }
            })


        }
    }

    fun calcularPuntos(id: String) {
        viewModelScope.launch {
            db.child("Service").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children) {
                        if (postSnapshot.child("userId").value.toString() == id &&
                            postSnapshot.child("calculado").value.toString() == false.toString()
                        ) {
                            db.child("Service").child(postSnapshot.child("id").value.toString())
                                .child("calculado")
                                .setValue(true)


                            var p_vid =
                                (ValorMaterial.VALOR_VIDRIO.valor * postSnapshot.child("envasesVidrio").value.toString()
                                    .toInt())
                            puntos_vidrio = p_vid
                            var p_pap =
                                (ValorMaterial.VALOR_CARTON.valor * postSnapshot.child("envasesCarton").value.toString()
                                    .toInt())
                            puntos_papel = p_pap
                            var p_plas = (ValorMaterial.VALOR_PLASTICO.valor * postSnapshot.child(
                                "envasesPlasticos"
                            ).value.toString().toInt())
                            puntos_plastico = p_plas
                            total = puntos_papel + puntos_vidrio + puntos_plastico
                            sumar(total, id)

                            break
                        }



                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel", error.toString())
                }
            })

        }
    }

    fun sumar(u: Int, id: String) {
        viewModelScope.launch {
            sumatoria += u+(misPuntos.value!!)

            db.child("User").child(id).child("puntos").setValue(sumatoria)
            sumatoria=0
        }
    }
}