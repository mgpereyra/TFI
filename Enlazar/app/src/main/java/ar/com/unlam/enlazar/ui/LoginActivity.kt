package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ir_crear_cuenta.setOnClickListener {
            startActivity(Intent(this,CrearCuentaActivity::class.java))
        }
    }


}