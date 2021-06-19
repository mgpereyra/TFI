package ar.com.unlam.enlazar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.databinding.ServiciosRecolectorRutaItemBinding
import ar.com.unlam.enlazar.model.Service

class   MisServiciosRecolectorAdapter(val onItemDetailViewClick: (servicioItem: Service) -> Unit) :
    RecyclerView.Adapter<MisServiciosRecolectorAdapter.ServiciosHolder>() {

    private val servicesList = mutableListOf<Service>()


    class ServiciosHolder(private val binding: ServiciosRecolectorRutaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binNote(servicioItem: Service) {
            binding.etNombreNote.text = servicioItem.address
            binding.etCommentNote.text = servicioItem.date
            /*Picasso.get()
                .load(
                    note.imagen
                )
                .into(binding.imgItemResult)
        }*/

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiciosHolder {
        val binding = ServiciosRecolectorRutaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiciosHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiciosHolder, position: Int) {
        holder.binNote(servicesList[position])
        holder.itemView.setOnClickListener {
            onItemDetailViewClick(servicesList[position])
        }
    }

    override fun getItemCount(): Int {
        return servicesList.size

    }

    fun submitList(it: List<Service>) {
        servicesList.clear()
        servicesList.addAll(it)
    }
}