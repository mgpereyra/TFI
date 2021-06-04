package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import ar.com.unlam.enlazar.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_crear_cuenta.view.*

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
        setup()
    }

    private fun setup(){
        btn_login.setOnClickListener {
            if(email.editText?.text.toString().isNotEmpty() && password.editText?.text.toString().isNotEmpty()){
                val mailString = email.editText?.text.toString()
                val passString = password.editText?.text.toString()

                 FirebaseAuth.getInstance().
                 createUserWithEmailAndPassword(mailString,
                     passString).addOnCompleteListener{
                    if (it.isSuccessful){
                        irDashboardUserActivity(it.result?.user?.email.toString() ?: "",
                            ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                 }
             }
        }
    }
private fun showAlert(){
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Error")
    builder.setMessage("Se ha producido un error autenticando al usuario")
    builder.setPositiveButton("Aceptar",null)
    val dialog: AlertDialog=builder.create()
    dialog.show()

}

    private fun irDashboardUserActivity(email:String, provider:ProviderType){
        val darsheboardActivity = Intent(this,DashboardUserActivity::class.java).apply {
         putExtra("email",email)
         putExtra("provider",provider)
        }
        startActivity(darsheboardActivity)
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