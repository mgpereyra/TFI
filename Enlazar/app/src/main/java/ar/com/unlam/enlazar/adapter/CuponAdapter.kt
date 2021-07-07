package ar.com.unlam.enlazar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CardInfo
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.model.Service
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mis_canjes_card.view.*

class CuponAdapter:RecyclerView.Adapter<CuponAdapter.CuponViewHolder>() {
    var listCupones= mutableListOf<CuponCanje>()

    class CuponViewHolder (view: View, var cupon: CuponCanje?=null):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuponViewHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.mis_canjes_card,parent,false)



        return CuponViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuponViewHolder, position: Int) {
        listCupones[position].let {
            holder.itemView.cardInfoId.text=it.id
            Picasso.get()
                .load(it.imageCode).error(R.drawable.error).into(holder.itemView.codigo)
            holder.itemView.cardInfo_elemento_canje.text=it.title
            holder.itemView.label_cantidad_disponible.visibility=View.INVISIBLE
            if (it.estadoCupon==false.toString()){
                holder.itemView.cardInfo_costo.text="Sin usar"
            }else{
                holder.itemView.cardInfo_costo.text="Canjeado"
            }



        }

    }
    fun submitList(it: List<CuponCanje>) {
        listCupones.clear()
        listCupones.addAll(it)
    }

    override fun getItemCount(): Int {
      return  listCupones.size
    }
}