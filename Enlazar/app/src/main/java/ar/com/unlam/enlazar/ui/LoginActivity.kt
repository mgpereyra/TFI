package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.data.retrofit.Constants
import ar.com.unlam.enlazar.ui.DashboardUserActivity.Companion.IDKEY
import ar.com.unlam.enlazar.ui.recolector.DashboardRecolectorActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password

class LoginActivity : AppCompatActivity() {
//    private lateinit var referenciaUser: DatabaseReference=rootRef.child(Constants.SERVICE_REF)
    private lateinit var database: FirebaseDatabase

    private val rootRef:DatabaseReference=FirebaseDatabase.getInstance().reference
    private val referenciaUser:DatabaseReference=rootRef.child(Constants.USER_REF)
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
                       val type = referenciaUser.child("typeUser").orderByChild("id").equalTo(id)
                        irDashboardUserActivity(id)
                       // irDashboardRecolectorActivity(id)
                        //Falta terminar de implementar el redireccionamiento segun el tipo de usuario

                                              // Log.d("Usuario datos resultado", type.toString())

                                /*                  if (type == 1) {
                                                   irDashboardUserActivity(id)
                                               } else if (type==2) {

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
        val darsheboardActivity = Intent(this, DashboardRecolectorActivity::class.java)
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