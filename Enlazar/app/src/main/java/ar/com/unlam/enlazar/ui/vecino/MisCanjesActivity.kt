package ar.com.unlam.enlazar.ui.vecino


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.adapter.ItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
            finish()
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
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

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


    }


}