package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.CardInfoAdapter
import ar.com.unlam.enlazar.adapter.MisServiciosVecinoAdapter
import ar.com.unlam.enlazar.model.CardInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mis_servicios.*
import kotlinx.android.synthetic.main.activity_seccion_informativa.*

class SeccionInformativaActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().reference
    lateinit var adapter: CardInfoAdapter
    var listAdvice = ArrayList<CardInfo>()
    var id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seccion_informativa)
        getConsejo()
        adapter = CardInfoAdapter()
        with(list_of_cardInfo) {
            layoutManager =
                LinearLayoutManager(
                    this@SeccionInformativaActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            this.adapter = this@SeccionInformativaActivity.adapter
        }

    }

    private fun getConsejo() {

        db.child("Advice").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children.iterator()) {
                    listAdvice.add(
                        CardInfo(
                            postSnapshot.child("id").value.toString(),
                            postSnapshot.child("uri").value.toString(),
                            postSnapshot.child("title").value.toString(),
                            postSnapshot.child("content").value.toString(),
                            postSnapshot.child("likes").value.toString().toLong(),
                            postSnapshot.child("tipoConsejo").value.toString(),
                            postSnapshot.child("uri").value.toString()
                        )
                    )


                }
                if (listAdvice.size > 0) {
                    adapter.cardInfoList = listAdvice.toMutableList()

                    adapter.notifyDataSetChanged()
                    list_of_cardInfo.adapter = adapter
                } else {
                    Toast.makeText(
                        this@SeccionInformativaActivity,
                        "no se encontraron consejos cargados todavia",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SeccionInformativaActivity,
                    "ha ocurrido un error en la consulta",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}