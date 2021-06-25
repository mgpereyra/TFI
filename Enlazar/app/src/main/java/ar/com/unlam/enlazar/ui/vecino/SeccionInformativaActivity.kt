package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.CardInfoAdapter
import ar.com.unlam.enlazar.model.CardInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_seccion_informativa.*

class SeccionInformativaActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference
    var adapter=CardInfoAdapter()
    var listAdvice=ArrayList<CardInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seccion_informativa)
getConsejo()


    }

    private fun getConsejo() {

        db.child("Advice").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children.iterator()) {
                    listAdvice.add(postSnapshot.value as CardInfo)


                }
                if (listAdvice.size>0){
                    adapter.cardInfoList=listAdvice.toMutableList()

                    adapter.notifyDataSetChanged()
                    list_of_cardInfo.adapter = adapter}
                else{
                    Toast.makeText(this@SeccionInformativaActivity,
                        "no se encontraron consejos cargados todavia",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SeccionInformativaActivity,
                    "ha ocurrido un error en la consulta",
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}