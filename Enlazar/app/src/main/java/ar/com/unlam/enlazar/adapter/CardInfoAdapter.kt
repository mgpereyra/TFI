package ar.com.unlam.enlazar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CardInfo
import ar.com.unlam.enlazar.ui.TipoConsejo
import ar.com.unlam.enlazar.ui.vecino.SeccionInformativaDetalleActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_info.view.*

class CardInfoAdapter: RecyclerView.Adapter<CardInfoAdapter.CardInfoViewHolder>()  {

var cardInfoList=mutableListOf<CardInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_info,parent,false)



        return CardInfoViewHolder(view)
    }
//

    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {
        cardInfoList[position].let{
           // holder.itemView.cardInfo_content_advice.text=it.content
            holder.itemView.cardInfo_title_advice.text=it.title
            holder.itemView.cardInfoId.text=it.id

            when (it.tipe){
                TipoConsejo.COMO_RECICLAR_BIEN.toString()->holder.itemView.advice_type.setText(R.string.como_reciclar)
                TipoConsejo.CONSEJO_DE_LA_SEMANA.toString()->holder.itemView.advice_type.setText(R.string.consejo_semana)
                TipoConsejo.ECOINFORME.toString()->holder.itemView.advice_type.setText(R.string.eco_info)
            }

            Picasso.get()
                .load(it.uri)
                //.resize(160,190)
                .centerCrop()
                .fit()
                .into(holder.itemView.cardInfo_image_advice)

                holder.card=it
        }
    }

    override fun getItemCount(): Int {
        return cardInfoList.size
    }
    class CardInfoViewHolder(view: View, var card:CardInfo?=null):RecyclerView.ViewHolder(view){
        init {
                view.dataContainer.setOnClickListener {
                    val intent= Intent(view.context, SeccionInformativaDetalleActivity::class.java)
                    intent.putExtra(SeccionInformativaDetalleActivity.ID,card!!.id)
                    view.context.startActivity(intent)

                }
        }

    }

}


