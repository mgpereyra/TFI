package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.CuponAdapter
import ar.com.unlam.enlazar.adapter.ItemAdapter
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mis_canjes.*
import kotlinx.android.synthetic.main.activity_mis_puntos.*
import kotlinx.android.synthetic.main.activity_seccion_informativa.*

class MisCanjesActivity : AppCompatActivity() {
   lateinit var adapter: ItemAdapter
   var puntos=0
    var id = FirebaseAuth.getInstance().currentUser!!.uid

    private val db = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_canjes)
        if (intent.hasExtra(PUNTOS_DISPONIBLES)) {

            puntos = intent.extras!!.getInt(PUNTOS_DISPONIBLES, 0)
        } else {
            Toast.makeText(
                this@MisCanjesActivity,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG
            ).show()
        }
        btnVolver_de_miscanjes.setOnClickListener {
            var intent=Intent(this,MisPuntosActivity::class.java)
            intent.putExtra(MisPuntosActivity.PUNTOS,puntos)
            startActivity(intent)


        }


        cargarCanjes()
        adapter = ItemAdapter()
        with(item_canjes) {
            layoutManager =
                LinearLayoutManager(
                    this@MisCanjesActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            this.adapter = this@MisCanjesActivity.adapter
        }
    }
    @Override
    public override fun onResume() {
        super.onResume()
        cargarCanjes()

    }
private fun cargarCanjes(){
    var canjes=ArrayList<Item>()
    db.child("Item").addValueEventListener(object:ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            for (item in snapshot.children) {
                if (item.child("amount").value.toString().toInt() > 0) {
                    canjes.add(
                        Item(
                            item.child("title").value.toString(),
                            item.child("id").value.toString(),
                            item.child("description").value.toString(),
                            item.child("amount").value.toString().toInt(),
                            item.child("imageCode").value.toString(),
                            item.child("pointsCost").value.toString().toInt(),
                            item.child("image").value.toString()
                        )
                    )
                }
            }
            if (canjes.size > 0) {
                adapter.listItem = canjes.toMutableList()

                adapter.notifyDataSetChanged()
                item_canjes.adapter = adapter
            } else {
                Toast.makeText(
                    this@MisCanjesActivity,
                    "no se encontraron servicios del estado especificado",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })



}
private fun cargarPuntos():Int{
    db.child("User").child(id).child("puntos").addValueEventListener(object:ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            puntos=snapshot.value.toString().toInt()

        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
    return puntos
}
companion object{
   var PUNTOS_DISPONIBLES=""
}

}