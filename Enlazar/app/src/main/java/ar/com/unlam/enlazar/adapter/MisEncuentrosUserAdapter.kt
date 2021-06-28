package ar.com.unlam.enlazar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.data.retrofit.Constants
import ar.com.unlam.enlazar.databinding.CardEncuentroBinding
import ar.com.unlam.enlazar.model.PuntoEncuentro
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.card_encuentro.view.*

class MisEncuentrosUserAdapter(val clickListener: OnRecyclerItemClick) :
    RecyclerView.Adapter<MisEncuentrosUserAdapter.EncuentrosHolder>() {
    val idVecinoCurrent= FirebaseAuth.getInstance().getCurrentUser()!!.getUid()

    private val puntosEncuentroList = mutableListOf<PuntoEncuentro>()

    class EncuentrosHolder(
        private val binding: CardEncuentroBinding,
        clickListener: OnRecyclerItemClick
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerViewAdapter = MisEncuentrosUserAdapter(clickListener)

        fun binNote(puntoEncuentroItem: PuntoEncuentro) {
            binding.cardInfoLocaclidad.text = puntoEncuentroItem.localidad
            binding.cardInfoFecha.text = puntoEncuentroItem.date +" - "+ puntoEncuentroItem.time
            binding.cardInfoCalles.text = puntoEncuentroItem.calle
            binding.cardInfoLugar.text = puntoEncuentroItem.lugar
            binding.cardInfoDescEncuentro.text = puntoEncuentroItem.description.toString()
            /*Picasso.get().load(note.imagen).into(binding.imgItemResult)}*/
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuentrosHolder {
        val binding = CardEncuentroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EncuentrosHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: EncuentrosHolder, position: Int) {
        holder.binNote(puntosEncuentroList[position])
        puntosEncuentroList[position].asistentes?.forEach {
            if (it.key == idVecinoCurrent) {
                holder.itemView.btn_aceptar_encuentro2.visibility = View.GONE
                holder.itemView.btncancelar_encuentro.visibility = View.VISIBLE
            }
        }
        holder.itemView.btn_aceptar_encuentro2.setOnClickListener {
            clickListener.onItemClickListener(puntosEncuentroList[position], Constants.ASISTIR)
            //  it.visibility = View.GONE
            //holder.itemView.btncancelar_encuentro.visibility = View.VISIBLE
        }
        holder.itemView.btncancelar_encuentro.setOnClickListener {
            clickListener.onItemClickListener(
                puntosEncuentroList[position],
                Constants.CANCELAR_ASISTENCIA
            )
            it.visibility = View.GONE
            holder.itemView.btn_aceptar_encuentro2.visibility = View.VISIBLE
        }
        holder.itemView.expandBtn.setOnClickListener {
            if (holder.itemView.cardInfo_desc_encuentro.visibility == View.GONE) {

                holder.itemView.cardInfo_desc_encuentro.visibility = View.VISIBLE
                it.rotation = 180.0F
            } else{
                holder.itemView.cardInfo_desc_encuentro.visibility = View.GONE
                it.rotation = 0.0F
            }
        }
    }
    override fun getItemCount(): Int {
        return puntosEncuentroList.size
    }

    fun submitList(it: List<PuntoEncuentro>) {
        puntosEncuentroList.clear()
        puntosEncuentroList.addAll(it)
    }
    interface OnRecyclerItemClick {
        fun onItemClickListener(puntoEncuentro: PuntoEncuentro,Action:Int)
    }
}