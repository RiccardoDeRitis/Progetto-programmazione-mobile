package com.example.coincex

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.fragment_menu.FavoritesFragment
import com.example.coincex.fragment_menu.MarketFragment
import com.squareup.picasso.Picasso

class SearchAdapter(private val dataSourceList: ArrayList<SearchCoinData>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_search_coin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSourceList[position]

        holder.symbol.text = item.symbol
        holder.name.text = item.nameCoin

        if (MarketFragment.getPreferences(item.id, holder.itemView.context) != "null")
            holder.preferred.setBackgroundResource(R.drawable.preferred)

        holder.preferred.setOnClickListener {
            if (MarketFragment.getPreferences(item.id, holder.itemView.context) != "null") {
                holder.preferred.setBackgroundResource(R.drawable.not_preferred)
                FavoritesFragment().deletePreferences(item.id, it.context)
            }
            else {
                holder.preferred.setBackgroundResource(R.drawable.preferred)
                MarketFragment.savePreferences(item.id, holder.itemView.context)
            }
        }

        holder.itemView.setOnClickListener {
            SearchCoinData.getCoinDataFromApi(it.context, item.id) { result ->
                val listCoin = ListCoinData.getData(result)
                val intent = Intent(it.context, CoinDetailsActivity::class.java)
                intent.putExtra("item", listCoin[0])
                it.context.startActivity(intent)
            }
        }

        val picasso = Picasso.get()
        picasso.load(item.logo).into(holder.logo)

    }

    override fun getItemCount(): Int {
        return dataSourceList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val preferred = view.findViewById(R.id.imageView7) as ImageView
        val logo = view.findViewById(R.id.logo_image3) as ImageView
        val symbol = view.findViewById(R.id.textView36) as TextView
        val name = view.findViewById(R.id.textView31) as TextView
    }

}