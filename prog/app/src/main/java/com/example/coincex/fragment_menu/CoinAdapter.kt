package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.CoinDetailsActivity
import com.example.coincex.R
import com.example.coincex.api_data.ListCoinData
import com.squareup.picasso.Picasso

class CoinAdapter(private val dataSourceList: ArrayList<ListCoinData>, private val bool: Boolean): RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSourceList[position]

        holder.rank.text = item.rank
        holder.symbol.text = item.symbol
        holder.name.text = item.name
        holder.cap.text = item.cap
        holder.volume.text = item.volume
        holder.price.text = item.price
        holder.priceChange.text = item.change24h
        holder.pricePercent.text = item.changePercent

        if (FavoritesFragment.getPreferences(item.id, holder.itemView.context) != "null") {
            holder.preferred.visibility = View.VISIBLE
        }

        if (item.change24h.contains("-")) {
            holder.priceChange.text = item.change24h
            holder.priceChange.setTextColor(Color.parseColor("#ff5232"))
        }
        else {
            holder.priceChange.text = "+"+item.change24h
            holder.priceChange.setTextColor(Color.parseColor("#00af5f"))
        }

        if (item.changePercent.contains("-")) {
            holder.pricePercent.text = item.changePercent
            holder.pricePercent.setTextColor(Color.parseColor("#ff5232"))
        }
        else {
            holder.pricePercent.text = "+"+item.changePercent
            holder.pricePercent.setTextColor(Color.parseColor("#00af5f"))
        }

        val picasso = Picasso.get()
        picasso.load(item.imageLogo).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CoinDetailsActivity::class.java)
            intent.putExtra("item", dataSourceList[position])
            it.context.startActivity(intent)
        }

        holder.rank.setOnClickListener {
            if (FavoritesFragment.getPreferences(item.id, it.context) != "null") {
                if (bool) {
                    FavoritesFragment.deletePreferences(item.id, it.context)
                    Toast.makeText(it.context, "Rimosso dai preferiti", Toast.LENGTH_SHORT).show()
                    dataSourceList.removeAt(position)
                    notifyItemRangeChanged(position, dataSourceList.size)
                    notifyItemRemoved(position)
                }
                else {
                    FavoritesFragment.deletePreferences(dataSourceList[position].id, it.context)
                    holder.preferred.visibility = View.INVISIBLE
                    Toast.makeText(it.context, "Rimosso dai preferiti", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                MarketFragment.savePreferences(item.id, it.context)
                holder.preferred.visibility = View.GONE
                notifyItemChanged(position)
                Toast.makeText(it.context, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return dataSourceList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val preferred = view.findViewById(R.id.imageView6) as ImageView
        val rank = view.findViewById(R.id.rank) as TextView
        val image = view.findViewById(R.id.logo_image) as ImageView
        val symbol = view.findViewById(R.id.coin) as TextView
        val name = view.findViewById(R.id.name) as TextView
        val cap = view.findViewById(R.id.market_cap) as TextView
        val volume = view.findViewById(R.id.volume) as TextView
        val price = view.findViewById(R.id.price) as TextView
        val priceChange = view.findViewById(R.id.change_price) as TextView
        val pricePercent = view.findViewById(R.id.change24h) as TextView
    }

}