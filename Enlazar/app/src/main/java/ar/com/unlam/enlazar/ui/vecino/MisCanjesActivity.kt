package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
    var puntos = 0
    var id = FirebaseAuth.getInstance().currentUser!!.uid
    val misCanjesViewModel: MisCanjesViewModel by viewModels()

    private val db = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_canjes)
        btnVolver_de_miscanjes.setOnClickListener {
            var intent = Intent(this, MisPuntosActivity::class.java)
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

        setObserverInCanjes()
    }

    @Override
    public override fun onResume() {
        super.onResume()
        cargarCanjes()

    }
    private fun setObserverInCanjes() {
        misCanjesViewModel.lista_de_canjes.observe(this,{
            adapter.submitList(it)
            adapter.notifyDataSetChanged()

        })}
    private fun cargarCanjes() {
        misCanjesViewModel.getItemsParaCanjear()
        /*var canjes=ArrayList<Item>()
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
        })*/

    }


}