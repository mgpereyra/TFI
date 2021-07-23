package ar.com.unlam.enlazar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.model.Item
import ar.com.unlam.enlazar.ui.vecino.DetalleCanjeItemActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mis_canjes_card.view.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    var listItem = mutableListOf<Item>()

    class ItemViewHolder(view: View, var item: Item? = null) : RecyclerView.ViewHolder(view) {
        init {
            view.ver_item.setOnClickListener {
                val intent = Intent(view.context, DetalleCanjeItemActivity::class.java)
                intent.putExtra(DetalleCanjeItemActivity.ID_CANJE, item!!.id)
                view.context.startActivity(intent)
            }
        }
    }

    fun submitList(it: List<Item>) {
        listItem.clear()
        listItem.addAll(it)
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
                .load(it.image)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.error)
                .into(holder.itemView.codigo)
            holder.itemView.cardInfo_elemento_canje.text = it.title
            holder.itemView.label_puntos_de_costo.text = "Puntos de costo: " // getText(R.string.estado_cupon)
            holder.itemView.cardInfo_costo.text = it.pointsCost.toString()
            holder.itemView.cantidad_diponible.text = it.amount.toString()
            holder.item = it
        }

    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}