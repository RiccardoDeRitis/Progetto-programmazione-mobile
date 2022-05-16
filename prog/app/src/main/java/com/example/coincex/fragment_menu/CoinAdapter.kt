package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.api_data.ListCoinData
import com.squareup.picasso.Picasso

class CoinAdapter(private val data: ArrayList<ListCoinData>,
                  private val bool: Boolean,
                  private val onClickItem: (coin: ListCoinData) -> Unit,
                  private val onClickRank: (id: String, pos: Int, preferred: ImageView) -> Unit):
    RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    // Metodo per l'inflate di list_recipe che ritorna un Viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe, parent, false)
        return ViewHolder(view,bool,onClickItem,onClickRank)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // Metodo che prende una coin nell'Arraylist ed esegue il bind dei dati
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, bool, position)
    }

    class ViewHolder(view: View,
                     bool: Boolean,
                     val onClickItem: (coin: ListCoinData) -> Unit,
                     val onClickRank: (id: String,
                                       pos: Int,
                                       preferred: ImageView) -> Unit)
        : RecyclerView.ViewHolder(view) {

        private val preferred = view.findViewById(R.id.imageView6) as ImageView
        private val rank = view.findViewById(R.id.rank) as TextView
        private val image = view.findViewById(R.id.logo_image) as ImageView
        private val symbol = view.findViewById(R.id.coin) as TextView
        private val name = view.findViewById(R.id.name) as TextView
        private val cap = view.findViewById(R.id.market_cap) as TextView
        private val volume = view.findViewById(R.id.volume) as TextView
        private val price = view.findViewById(R.id.price) as TextView
        private val priceChange = view.findViewById(R.id.change_price) as TextView
        private val pricePercent = view.findViewById(R.id.change24h) as TextView
        private var currentCoin: ListCoinData? = null
        private var currentId: String? = null
        private var currentPosition: Int? = null

        /*
        Listener per il click sull'item, sul rank per inserirlo nei preferiti e nel caso in cui
        l'adapter è riferito alla lista delle coin preferite, anche per il click sull'immagine
         */
        init {

            itemView.setOnClickListener { _ ->
                currentCoin?.let{
                    onClickItem(it)
                }
            }

            rank.setOnClickListener {
                currentId?.let { id ->
                    currentPosition?.let { pos ->
                        onClickRank(id,pos,preferred)
                    }
                }
            }

            if (bool) {
                preferred.visibility = View.GONE
                rank.visibility = View.GONE
                image.setOnClickListener {
                    currentId?.let { id ->
                        currentPosition?.let { pos ->
                            onClickRank(id,pos,preferred)
                        }
                    }
                }
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind(coin: ListCoinData, bool: Boolean, pos: Int) {
            currentCoin = coin
            currentId = coin.id
            currentPosition = pos

            rank.text = coin.rank
            symbol.text = coin.symbol
            name.text = coin.name
            cap.text = coin.cap
            volume.text = coin.volume
            price.text = coin.price
            priceChange.text = coin.change24h
            pricePercent.text = coin.changePercent

            if (coin.change24h.contains("-")) {
                priceChange.text = coin.change24h
                priceChange.setTextColor(Color.parseColor("#ff5232"))
            }
            else {
                priceChange.text = "+"+coin.change24h
                priceChange.setTextColor(Color.parseColor("#00af5f"))
            }

            if (coin.changePercent.contains("-")) {
                pricePercent.text = coin.changePercent
                pricePercent.setTextColor(Color.parseColor("#ff5232"))
            }
            else {
                pricePercent.text = "+"+coin.changePercent
                pricePercent.setTextColor(Color.parseColor("#00af5f"))
            }

            val picasso = Picasso.get()
            picasso.load(coin.imageLogo).into(image)

            if (!bool)
                if (FavoritesFragment.getPreferences(coin.id, itemView.context) != "null")
                    preferred.visibility = View.VISIBLE
        }
    }
}