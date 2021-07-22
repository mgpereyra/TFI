package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Consulta
import ar.com.unlam.enlazar.ui.Estado
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_mis_servicios_detalle.*
import kotlinx.android.synthetic.main.activity_mis_servicios_detalle.cardInfoId


class MisServiciosDetalleActivity : AppCompatActivity() {
    private val servicioDetalleViewModel: MisServiciosDetalleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_servicios_detalle)
        if (intent.hasExtra(ID)) {
            verServicio(intent.extras!!.getString(ID, ""))
        } else {
            Toast.makeText(
                this@MisServiciosDetalleActivity,
                getString(R.string.valor_perdido), Toast.LENGTH_LONG
            ).show()
        }
        btn_cancel_servicio.setOnClickListener {
            cancelarServicio(cardInfoId.text.toString())
        }
        btnVolver_mis_servicios_detalle.setOnClickListener {
            finish()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
    private fun cancelarServicio(id: String) {
        /*  db.child("Service").child(id).child("estado").setValue(Estado.CANCELADO.ordinal)
              .addOnCompleteListener {
                  Toast.makeText(
                      this,
                      "Tu Servicio ha sido cancelado exitosamente",
                      Toast.LENGTH_LONG
                  )
                      .show()
              }*/
        servicioDetalleViewModel.cancelService(id)
        irMisServiciosConfirmadosActivity()

    }

    private fun irMisServiciosConfirmadosActivity() {

        finish()
/*        val serviceActivity = Intent(this, MisServiciosActivity::class.java)

        this.startActivity(serviceActivity)
        this@MisServiciosDetalleActivity.finish()
        startActivity(serviceActivity)*/
    }

    private fun verServicio(id: String) {

        servicioDetalleViewModel.getServiceById(id)
        servicioDetalleViewModel.serviceLiveData.observe(this, {
            setObservers(it)

        })
        /*     db.child("Service").child(id).addValueEventListener(object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     detalle_activity_cantidad_de_bolsas_amarillo.text =
                         snapshot.child("envasesPlasticos").value.toString()
                     detalle_activity_cantidad_bolsas_verdes.text =
                         snapshot.child("envasesVidrio").value.toString()
                     detalle_activity_cantidad_de_bolsas_azul.text =
                         snapshot.child("envasesCarton").value.toString()
                     detalle_activity_cardInfo_date.text = snapshot.child("date").value.toString()
                     detalle_activity_cardInfo_direccion.text =
                         snapshot.child("address").value.toString()
                     detalle_activity_turno.text = snapshot.child("time").value.toString()
                     verificarEstado(snapshot.child("estado").value.toString().toInt())
                     cardInfoId.text = snapshot.key
                 }

                 override fun onCancelled(error: DatabaseError) {
                     Toast.makeText(
                         this@MisServiciosDetalleActivity,
                         "No se encontraron los datos especificados", Toast.LENGTH_LONG
                     ).show()
                 }


             })*/

    }


    private fun verificarEstado(e: Int?) {
        if (e == Estado.ASIGNADO.ordinal) {
            btn_cancel_servicio.visibility = View.INVISIBLE
        }
    }

    private fun setObservers(s: Service) {
        detalle_activity_cantidad_de_bolsas_amarillo.text = s.envasesPlasticos.toString()
        detalle_activity_cantidad_bolsas_verdes?.setText(s.envasesVidrio.toString())
        detalle_activity_cantidad_de_bolsas_azul?.setText(s.envasesCarton.toString())
        detalle_activity_cardInfo_detalle_direccion?.setText(s.address)
        detalle_activity_turno?.setText("Fecha: "+s.date +" - "+ "Hora: "+s.time)
        cardInfoId?.setText(s.id)
        verificarEstado(s.estado)

        servicioDetalleViewModel.estado.observe(this, { estado(it) })
    }

    private fun estado(status: Consulta) {
        when (status) {
           /* Consulta.SUCCESS -> Toast.makeText(
                this@MisServiciosDetalleActivity,
                getString(R.string.succes), Toast.LENGTH_LONG
            ).show()*/
            Consulta.ERROR -> Toast.makeText(
                this@MisServiciosDetalleActivity,
                getString(R.string.error), Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        val ID: String = "ID"
    }
}