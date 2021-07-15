package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.CuponAdapter

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mis_puntos.*

class MisPuntosActivity : AppCompatActivity() {
    var id = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var adapter: CuponAdapter
    val misPuntosViewModel: MisPuntosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_puntos)

        btn_ir_a_canjear.setOnClickListener {
            val intent = Intent(this, MisCanjesActivity::class.java)
            startActivity(intent)

        }
        misPuntosViewModel.calcularPuntos(id)
        misPuntosViewModel.cargarDatos(id)
        setData()
        adapter = CuponAdapter()
        with(historial_canje) {
            layoutManager =
                LinearLayoutManager(this@MisPuntosActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@MisPuntosActivity.adapter
        }
        historial_canje.adapter = adapter

        btnVolver_deMisPuntos.setOnClickListener {
            finish()
        }

    }

    private fun cargarData() {
        /* db.child("User").child(id).addValueEventListener(object : ValueEventListener {
             var mis_canjes = ArrayList<CuponCanje>()
             override fun onDataChange(snapshot: DataSnapshot) {
                 cantidad_de_puntos.text = snapshot.child("puntos").value.toString()
                 for (canje in snapshot.child("Cupon").children) {
                     mis_canjes.add(
                         CuponCanje(
                             canje.child("id").value.toString(),
                             canje.child("id_item").value.toString(),
                             canje.child("title").value.toString(),
                             canje.child("description").value.toString(),
                             canje.child("imageCode").value.toString(),
                             canje.child("estadoCupon").value.toString()
                         )
                     )
                 }
                 if (mis_canjes.size > 0) {
                     adapter.listCupones = mis_canjes.toMutableList()

                     adapter.notifyDataSetChanged()
                     historial_canje.adapter = adapter
                 } else {
                     Toast.makeText(
                         this@MisPuntosActivity,
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

    /* private fun sumar(u: Int) {
         sumatoria += u

         db.child("User").child(id).child("puntos").setValue(sumatoria)
     }

     private fun setEstadocuponUi(e:String){
         if (e == false.toString()){

         }
     }
     private fun calcularPuntos() {
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
                         sumar(total)

                         break
                     }


                 }

             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }
         })

     }*/
    private fun setData() {
        misPuntosViewModel.misCanjes.observe(this, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()

        })
        misPuntosViewModel.misPuntos.observe(this,{ observePoints(it)})
    }

    private fun observePoints(it: Int){
        cantidad_de_puntos.text=it.toString()
    }

    companion object {
        var PUNTOS = ""
    }
}
