package ar.com.unlam.enlazar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.Estado
import ar.com.unlam.enlazar.ui.vecino.MisServiciosDetalleActivity
import ar.com.unlam.enlazar.ui.vecino.MisServiciosDetalleActivity.Companion.ID
import kotlinx.android.synthetic.main.mis_servicios_card.view.*

class MisServiciosVecinoAdapter:RecyclerView.Adapter<MisServiciosVecinoAdapter.ServicioVecinoHolder>() {
    var servicesList = mutableListOf<Service>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioVecinoHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.mis_servicios_card,parent,false)



        return ServicioVecinoHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioVecinoHolder, position: Int) {
        servicesList[position].let{service ->  
            holder.itemView.cantidad_de_bolsas_amarillo.text=service.envasesPlasticos.toString()
            holder.itemView.cantidad_de_bolsas_azul.text=service.envasesCarton.toString()
            holder.itemView.cantidad_bolsas_verdes.text=service.envasesVidrio.toString()
            holder.itemView.cardInfo_date.text=service.date.toString()
            holder.itemView.cardInfo_direccion.text=service.address.toString()
            holder.itemView.turno.text=service.time
            holder.itemView.estado.text=service.estado.toString()
            holder?.service =service
        }

    }

    override fun getItemCount(): Int {
       return servicesList.size

    }
    class ServicioVecinoHolder(view: View, var service:Service?=null):RecyclerView.ViewHolder(view){

        init {

                view.btn_ver_servicio.visibility=View.VISIBLE
                view.btn_ver_servicio.setOnClickListener {
                    val intent = Intent(view.context, MisServiciosDetalleActivity::class.java)
                    intent.putExtra(ID, service!!.id)
                    view.context.startActivity(intent)
                }


        }
    }
}