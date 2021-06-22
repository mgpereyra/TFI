package ar.com.unlam.enlazar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CardInfo
import ar.com.unlam.enlazar.ui.vecino.DetalleCardInfoActivity
import kotlinx.android.synthetic.main.card_info.view.*

class CardInfoAdapter: RecyclerView.Adapter<CardInfoAdapter.CardInfoViewHolder>()  {

var cardInfoList=ArrayList<CardInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_info,parent,false)



        return CardInfoViewHolder(view)
    }
//

    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return cardInfoList.size
    }
    class CardInfoViewHolder(view: View, var card:CardInfo?=null):RecyclerView.ViewHolder(view){
        init {
                view.btn_ver_card_info.setOnClickListener {
                    val intent= Intent(view.context, DetalleCardInfoActivity::class.java)
                    intent.putExtra(DetalleCardInfoActivity.ID,card!!.id)
                    view.context.startActivity(intent)

                }
        }

    }

}


