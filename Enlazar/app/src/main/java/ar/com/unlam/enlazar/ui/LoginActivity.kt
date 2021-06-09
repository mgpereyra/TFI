package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import ar.com.unlam.enlazar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password

class LoginActivity : AppCompatActivity() {
    private lateinit var referenciaUser: DatabaseReference
    private lateinit var database: FirebaseDatabase

    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            if (email.editText?.text.toString().isNotEmpty() && password.editText?.text.toString()
                    .isNotEmpty()
            ) {
                val mailString = email.editText?.text.toString()
                val passString = password.editText?.text.toString()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    mailString,
                    passString
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        id = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
                        referenciaUser = database.getReference("User")

                        /*                       //Falta terminar de implementar el redireccionamiento segun el tipo de usuario
                                               database = FirebaseDatabase.getInstance()
                                               val type = referenciaUser.orderByChild("id").equalTo(id)
                                               Log.d("Usuario datos resultado", type.toString())

                                               if (type == 1) {
                                                   irDashboardUserActivity(id)
                                               } else if (type==2) {
                                                   irDashboardRecolectorActivity(id)
                                               }*/

                    } else {
                        showAlert()
                    }
                }
            }


            // startActivity(Intent(this,MiRutaActivity::class.java))*/
        }

        ir_crear_cuenta.setOnClickListener {
            this@LoginActivity.finish()
            startActivity(Intent(this, CrearCuentaActivity::class.java))
        }

    }

    private fun irDashboardUserActivity(idUser: String) {
        val darsheboardActivity = Intent(this, DashboardUserActivity::class.java)
        darsheboardActivity.putExtra(DashboardUserActivity.IDKEY, idUser!!)

        this.startActivity(darsheboardActivity)
        this@LoginActivity.finish()
        startActivity(darsheboardActivity)
    }

    private fun irDashboardRecolectorActivity(idUser: String) {
        val darsheboardActivity = Intent(this, DashboardUserActivity::class.java)
        darsheboardActivity.putExtra(DashboardUserActivity.IDKEY, idUser!!)

        this.startActivity(darsheboardActivity)
        this@LoginActivity.finish()
        startActivity(darsheboardActivity)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Usuario incorrecto")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}