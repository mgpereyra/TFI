package ar.com.unlam.enlazar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.databinding.CardEncuentroBinding
import ar.com.unlam.enlazar.databinding.MisEncuentrosPendientesCardBinding
import ar.com.unlam.enlazar.databinding.ServiciosRecolectorRutaItemBinding
import ar.com.unlam.enlazar.model.PuntoEncuentro
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.ui.vecino.MisEncuentrosActivity
import kotlinx.android.synthetic.main.card_encuentro.view.*

class MisEncuentrosUserAdapter(val onItemDetailViewClick: (puntoEncuentroItem: PuntoEncuentro) -> Unit) :
    RecyclerView.Adapter<MisEncuentrosUserAdapter.EncuentrosHolder>() {

    private val puntosEncuentroList = mutableListOf<PuntoEncuentro>()

    class EncuentrosHolder(private val binding: CardEncuentroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binNote(puntoEncuentroItem: PuntoEncuentro) {
            binding.cardInfoLocaclidad.text = puntoEncuentroItem.localidad
            binding.cardInfoFecha.text = puntoEncuentroItem.date +" - "+ puntoEncuentroItem.time
            binding.cardInfoCalles.text = puntoEncuentroItem.calle
            binding.cardInfoLugar.text = puntoEncuentroItem.lugar
            binding.cardInfoLugar.text = puntoEncuentroItem.estado.toString()

            /*     Picasso.get()
                   .load(
                       note.imagen
                   )
                   .into(binding.imgItemResult)
           }*/

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuentrosHolder {
        val binding = CardEncuentroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EncuentrosHolder(binding)
    }

    override fun onBindViewHolder(holder: EncuentrosHolder, position: Int) {
        holder.binNote(puntosEncuentroList[position])
        holder.itemView.btn_aceptar_encuentro2.setOnClickListener {
            onItemDetailViewClick(puntosEncuentroList[position])
        }
    }

    override fun getItemCount(): Int {
        return puntosEncuentroList.size
    }

    fun submitList(it: List<PuntoEncuentro>) {
        puntosEncuentroList.clear()
        puntosEncuentroList.addAll(it)
    }
}