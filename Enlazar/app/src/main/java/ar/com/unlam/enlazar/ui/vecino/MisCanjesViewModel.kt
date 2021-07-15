package ar.com.unlam.enlazar.ui.vecino

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.unlam.enlazar.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MisCanjesViewModel() : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    var lista_de_canjes = MutableLiveData<List<Item>>()

    fun getItemsParaCanjear() {
        db.child("Item").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               var list=ArrayList<Item>()
                for (item in snapshot.children) {
                    var canjes=item.getValue(Item::class.java)
                    if (item.child("amount").value.toString().toInt() > 0) {
                        if (canjes != null) {
                            list.add(canjes)
                        }
                        /*canjes.add(
                            Item(
                                item.child("title").value.toString(),
                                item.child("id").value.toString(),
                                item.child("description").value.toString(),
                                item.child("amount").value.toString().toInt(),
                                item.child("imageCode").value.toString(),
                                item.child("pointsCost").value.toString().toInt(),
                                item.child("image").value.toString()
                            )
                        )*/
                    }
                }
                if (list.size>0) {
                    lista_de_canjes.value=list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }


}