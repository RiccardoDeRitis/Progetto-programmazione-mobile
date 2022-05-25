package com.example.coincex.list_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.data_class.WalletCoinDataClass
import com.squareup.picasso.Picasso

class ConvertAdapter(private val data: ArrayList<WalletCoinDataClass>, private val onClickItem: (coin: WalletCoinDataClass) -> Unit):
    RecyclerView.Adapter<ConvertAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_coin_convert, parent, false)
        return ViewHolder(view, onClickItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(view: View, val onClickItem: (coin: WalletCoinDataClass) -> Unit): RecyclerView.ViewHolder(view) {
        private val logo = view.findViewById(R.id.logo_image8) as ImageView
        private val name = view.findViewById(R.id.textView87) as TextView
        private val symbol = view.findViewById(R.id.textView88) as TextView
        private var currentCoin: WalletCoinDataClass? = null

        init {
            itemView.setOnClickListener { _ ->
                currentCoin?.let {
                    onClickItem(it)
                }
            }
        }

        fun bind(coin: WalletCoinDataClass) {
            currentCoin = coin
            name.text = coin.name
            symbol.text = coin.symbol

            val picasso = Picasso.get()

            if (coin.logo == "n/a")
                picasso.load(R.drawable.logo_na).into(logo)

            else
                picasso.load(coin.logo).into(logo)

        }

    }

}