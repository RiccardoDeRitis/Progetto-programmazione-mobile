package com.example.coincex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ConvertAdapter(private val data: ArrayList<WalletCoinDataClass>):
    RecyclerView.Adapter<ConvertAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_coin_convert, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val logo = view.findViewById(R.id.logo_image8) as ImageView
        private val name = view.findViewById(R.id.textView87) as TextView
        private val symbol = view.findViewById(R.id.textView88) as TextView

        fun bind(coin: WalletCoinDataClass) {
            name.text = coin.name
            symbol.text = coin.symbol

            val picasso = Picasso.get()
            picasso.load(coin.logo).into(logo)
        }

    }

}