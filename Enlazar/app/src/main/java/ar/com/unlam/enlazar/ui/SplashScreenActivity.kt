package ar.com.unlam.enlazar.ui

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sp_logo.alpha=0f
        sp_logo.animate().setDuration(1500).alpha(1f).withEndAction {
            val i= Intent(this, LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()

        }
    }
}