package ar.com.unlam.enlazar.adapter

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.provider.Settings.Secure.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.TypedArrayUtils.getResourceId
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.CuponCanje
import ar.com.unlam.enlazar.model.utils.QrUtils
import ar.com.unlam.enlazar.ui.vecino.QrViewActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mis_canjes_card.view.*
import kotlinx.android.synthetic.main.mis_canjes_card.view.cardInfoId
import kotlinx.android.synthetic.main.mis_canjes_card.view.codigo
import kotlinx.android.synthetic.main.mis_canjes_card.view.label_cantidad_disponible

class CuponAdapter : RecyclerView.Adapter<CuponAdapter.CuponViewHolder>() {
    var listCupones = mutableListOf<CuponCanje>()

    class CuponViewHolder(view: View, var cupon: CuponCanje? = null) :
        RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuponViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.mis_canjes_card, parent, false)



        return CuponViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuponViewHolder, position: Int) {
        val idRecolector = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        listCupones[position].let {
            var datosQr = idRecolector + "/" + it.id + "/" + it.id_item
            holder.itemView.cardInfoId.text = it.id

           // holder.itemView.codigo.setImageBitmap(QrUtils.generateQr(idRecolector + "/" + it.id + "/" + it.id_item))

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, QrViewActivity::class.java)
                intent.putExtra("qrDatos", datosQr)
                holder.itemView.codigo.context.startActivity(intent)
            }
            Picasso.get().load(it.imageCode).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.error).into(holder.itemView.codigo)
            holder.itemView.cardInfo_elemento_canje.text = it.title
            holder.itemView.label_puntos_de_costo.text = "Estado " // getText(R.string.estado_cupon)
            holder.itemView.label_cantidad_disponible.visibility = View.GONE
            holder.itemView.ver_item.visibility = View.GONE
            if (!it.estadoCupon) {
                holder.itemView.cardInfo_costo.text =
                    "Disponible" //resources.getColor(R.color.green)
                holder.itemView.cardInfo_costo.setTextColor(Color.parseColor("#006D46"))

            } else {
                holder.itemView.cardInfo_costo.text = "Canjeado"
                holder.itemView.cardInfo_costo.setTextColor(Color.parseColor("#bb6517"))
            }
        }

    }

    fun submitList(it: List<CuponCanje>) {
        listCupones.clear()
        listCupones.addAll(it)
    }

    override fun getItemCount(): Int {
        return listCupones.size
    }

}