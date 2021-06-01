package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.ui.recolector.DashboardRecolectorActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener {
            this@LoginActivity.finish()
            startActivity(Intent(this,DashboardRecolectorActivity::class.java))
        }
        ir_crear_cuenta.setOnClickListener {
            this@LoginActivity.finish()
            startActivity(Intent(this,CrearCuentaActivity::class.java))
        }

    }


}