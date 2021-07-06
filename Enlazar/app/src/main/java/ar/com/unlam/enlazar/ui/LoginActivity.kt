package ar.com.unlam.enlazar.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.data.retrofit.Constants
import ar.com.unlam.enlazar.model.User
import ar.com.unlam.enlazar.ui.recolector.DashboardRecolectorActivity
import ar.com.unlam.enlazar.ui.vecino.CrearCuentaActivity
import ar.com.unlam.enlazar.ui.vecino.DashboardUserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password

class LoginActivity : AppCompatActivity() {
    //    private lateinit var referenciaUser: DatabaseReference=rootRef.child(Constants.SERVICE_REF)
    //  private lateinit var database: FirebaseDatabase

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val referenciaUser: DatabaseReference = rootRef.child(Constants.USER_REF)
    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        session()
        setClickListerners()

    }

    private fun setClickListerners() {
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

                        referenciaUser.child(id).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                 //snapshot.child("id").value
                                val user = snapshot.getValue(User::class.java)
                                if (user!!.typeUser == 1) {
                                    irDashboardUserActivity(
                                        user!!.id!!,
                                        user!!.email!!,
                                        user!!.typeUser!!
                                    )

                                } else {
                                    irDashboardRecolectorActivity(
                                        user!!.id!!,
                                        user!!.email!!,
                                        user!!.typeUser!!
                                    )
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("Cancel", error.toString())
                            }
                        })

                    } else {
                        showAlert()
                    }
                }
            }
        }

        ir_crear_cuenta.setOnClickListener {
            this@LoginActivity.finish()
            startActivity(Intent(this, CrearCuentaActivity::class.java))
        }
    }

    private fun irDashboardUserActivity(idUser: String, email: String, typeUser: Int) {
        val intent = Intent(this, DashboardUserActivity::class.java)
        intent.putExtra("idUser", idUser)
        intent.putExtra("email", email)
        intent.putExtra("typeUser", typeUser)

        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE).edit()
        pref.putString("idUser", idUser)
        pref.putString("email", email)
        pref.putInt("typeUser", typeUser!!)
        pref.apply()

        this@LoginActivity.finish()
        startActivity(intent)
    }

    private fun irDashboardRecolectorActivity(idUser: String, email: String, typeUser: Int) {
        val intent = Intent(this, DashboardRecolectorActivity::class.java)
        intent.putExtra("idUser", idUser)
        intent.putExtra("email", email)
        intent.putExtra("typeUser", typeUser)

        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE).edit()
        pref.putString("idUser", idUser)
        pref.putString("email", email)
        pref.putInt("typeUser", typeUser!!)
        pref.apply()

        startActivity(intent)
        this@LoginActivity.finish()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Usuario incorrecto")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun session() {
        val pref = getSharedPreferences(getString(R.string.user_login), Context.MODE_PRIVATE)
        val typeUser = pref.getInt("typeUser", 5)
        if (typeUser != null) {
            if (typeUser == 1) {
                val intent = Intent(this, DashboardUserActivity::class.java)

                startActivity(intent)
                finish()
            }else if(typeUser==2) {
                val intent = Intent(this, DashboardRecolectorActivity::class.java)
                startActivity(intent)
                finish()
            }else{

            }

        }

    }


}