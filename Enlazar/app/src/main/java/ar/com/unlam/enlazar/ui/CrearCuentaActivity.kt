package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_crear_cuenta.*

class CrearCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        btnVolver.setOnClickListener {
            this@CrearCuentaActivity.finish()
        }
        btn_login.setOnClickListener{
            this@CrearCuentaActivity.finish()
            val intent: Intent = Intent(this, NuevoServicioActivity::class.java)
            startActivity(intent)

        }
        val spinner2 = findViewById<Spinner>(R.id.partido_spinner)
        val listaSipnner2 = resources.getStringArray(R.array.partidos_array)
        val adapterSpinner2 = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaSipnner2
        )
        spinner2.adapter = adapterSpinner2
       itemSelectedSipnner(spinner2)

        val spinner = findViewById<Spinner>(R.id.location_spinner)
        val listaSipnner = resources.getStringArray(R.array.locations_array)
        val adapterSpinner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaSipnner
        )
        spinner.adapter = adapterSpinner
        itemSelectedSipnner(spinner)




    }

    private fun itemSelectedSipnner(obj: Spinner?) {
        obj?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}