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
        btnVolver_deMisPuntos.setOnClickListener {
            finish()
        }
        btn_ir_a_canjear.setOnClickListener {
            val intent = Intent(this, MisCanjesActivity::class.java)
            startActivity(intent)

        }
        setData()
        adapter = CuponAdapter()
        with(historial_canje) {
            layoutManager =
                LinearLayoutManager(this@MisPuntosActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@MisPuntosActivity.adapter
        }
        historial_canje.adapter = adapter



    }

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

}
