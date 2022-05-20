package com.example.coincex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.text.DecimalFormat

class WalletAdapter(private val data: ArrayList<WalletCoinDataClass>):
    RecyclerView.Adapter<WalletAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_coin_wallet, parent, false)
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
        private val color = view.findViewById(R.id.logo_image4) as ImageView
        private val name = view.findViewById(R.id.textView72) as TextView
        private val quantity = view.findViewById(R.id.textView73) as TextView
        private val price = view.findViewById(R.id.textView74) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(coin: WalletCoinDataClass) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN

            color.setBackgroundColor(coin.color)
            name.text = coin.name
            quantity.text = coin.quantity.toString()
            price.text = df.format(coin.price*coin.quantity).toString()+"$"
        }

    }

}