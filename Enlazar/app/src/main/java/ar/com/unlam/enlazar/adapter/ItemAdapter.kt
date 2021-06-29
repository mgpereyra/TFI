package ar.com.unlam.enlazar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mis_canjes_card.view.*

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    var listItem = mutableListOf<Item>()

    class ItemViewHolder(view: View, var item: Item? = null) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.mis_canjes_card, parent, false)



        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        listItem[position].let {
            holder.itemView.cardInfoId.text = it.id
            Picasso.get()
                .load(it.imageCode).error(R.drawable.error).into(holder.itemView.codigo)
            holder.itemView.cardInfo_elemento_canje.text = it.title
            holder.itemView.cardInfo_costo.text = it.pointsCost.toString()


        }

    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}