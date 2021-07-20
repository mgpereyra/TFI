package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.utils.QrUtils
import ar.com.unlam.enlazar.ui.recolector.RutaRecolectorMapActivity
import kotlinx.android.synthetic.main.activity_qr_view.*
import kotlinx.android.synthetic.main.mis_canjes_card.view.*

class QrViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_view)

     var   datosQr = intent.getStringExtra("qrDatos").toString()
        qrDetalleImg.setImageBitmap(QrUtils.generateQr(datosQr))
    }
}