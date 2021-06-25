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

class MisEncuentrosUserAdapter(val onItemDetailViewClick: (puntoEncuentroItem: PuntoEncuentro) -> Unit) :
    RecyclerView.Adapter<MisEncuentrosUserAdapter.EncuentrosHolder>() {

    private val puntosEncuentroList = mutableListOf<PuntoEncuentro>()

    class EncuentrosHolder(private val binding: CardEncuentroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binNote(puntoEncuentroItem: PuntoEncuentro) {
           /* binding.cardInfoLocaclidad.text = puntoEncuentroItem.address
            binding.cardInfoFecha.text = puntoEncuentroItem.date
            binding.cardInfoCalles.text = puntoEncuentroItem.date
            binding.cardInfoLugar.text = untoEncuentroItem.date
            Picasso.get()
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
        holder.itemView.setOnClickListener {
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