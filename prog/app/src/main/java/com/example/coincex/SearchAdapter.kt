package com.example.coincex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.fragment_menu.FavoritesFragment
import com.squareup.picasso.Picasso

class SearchAdapter(private val data: ArrayList<SearchCoinData>,
                    private val onClickItem: (coin: SearchCoinData) -> Unit,
                    private val onClickStar: (id: String,
                                              preferred: ImageView) -> Unit):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_search_coin, parent, false)
        return ViewHolder(view, onClickItem, onClickStar)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(view: View,
                     val onClickItem: (coin: SearchCoinData) -> Unit,
                     val onClickStar: (id: String, preferred: ImageView) -> Unit):
        RecyclerView.ViewHolder(view) {

        private val preferred = view.findViewById(R.id.imageView7) as ImageView
        private val logo = view.findViewById(R.id.logo_image3) as ImageView
        private val symbol = view.findViewById(R.id.textView36) as TextView
        private val name = view.findViewById(R.id.textView31) as TextView
        private var currentCoin: SearchCoinData? = null
        private var currentId: String? = null

        init {
            preferred.setOnClickListener {
                currentId?.let { id ->
                    onClickStar(id,preferred)
                }
            }
            itemView.setOnClickListener {
                currentCoin?.let {
                    onClickItem(it)
                }
            }
        }

        fun bind(coin: SearchCoinData) {
            currentCoin = coin
            currentId = coin.id

            symbol.text = coin.symbol
            name.text = coin.nameCoin

            if (FavoritesFragment.getPreferences(coin.id, itemView.context) != "null")
                preferred.setBackgroundResource(R.drawable.preferred)

            val picasso = Picasso.get()
            picasso.load(coin.logo).into(logo)
        }

    }

}