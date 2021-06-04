package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.recolector.DashboardRecolectorActivity
import ar.com.unlam.enlazar.ui.recolector.MiRutaActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password

class LoginActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            if(email.editText?.text.toString().isNotEmpty() && password.editText?.text.toString().isNotEmpty()){
                val mailString = email.editText?.text.toString()
                val passString = password.editText?.text.toString()

                FirebaseAuth.getInstance().
                signInWithEmailAndPassword(mailString,
                    passString).addOnCompleteListener{
                    if (it.isSuccessful){
                        irDashboardUserActivity(it.result?.user?.email.toString() ?: "",
                            ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }



           // startActivity(Intent(this,MiRutaActivity::class.java))*/
        }



        ir_crear_cuenta.setOnClickListener {
            this@LoginActivity.finish()
            startActivity(Intent(this,CrearCuentaActivity::class.java))
        }

    }
    private fun irDashboardUserActivity(email:String, provider:ProviderType){
        val darsheboardActivity = Intent(this,DashboardUserActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
        this@LoginActivity.finish()
        startActivity(darsheboardActivity)
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Usuario incorrecto")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()

    }
}