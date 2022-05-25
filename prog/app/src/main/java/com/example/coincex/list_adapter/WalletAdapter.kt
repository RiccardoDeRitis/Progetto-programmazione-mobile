package com.example.coincex.list_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.data_class.WalletCoinDataClass
import com.squareup.picasso.Picasso
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
        private val logo = view.findViewById(R.id.logo_image4) as ImageView
        private val symbol = view.findViewById(R.id.textView72) as TextView
        private val name = view.findViewById(R.id.textView71) as TextView
        private val quantity = view.findViewById(R.id.textView73) as TextView
        private val price = view.findViewById(R.id.textView74) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(coin: WalletCoinDataClass) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN

            val picasso = Picasso.get()

            if (coin.logo == "n/a")
                picasso.load(R.drawable.logo_na).into(logo)
            else
                picasso.load(coin.logo).into(logo)


            name.text = coin.name
            symbol.text = coin.symbol
            quantity.text = coin.quantity.toString()
            price.text = df.format(coin.price*coin.quantity).toString()+"$"
        }

    }

}