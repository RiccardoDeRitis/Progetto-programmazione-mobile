package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.coincex.R
import com.example.coincex.api_data.ListCoinData
import com.squareup.picasso.Picasso

class RecipeAdapter(context: Context, private val dataSourceList: ArrayList<ListCoinData>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSourceList.size
    }

    override fun getItem(position: Int): Any {
        return dataSourceList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {

            view = inflater.inflate(R.layout.list_recipe, parent, false)

            holder = ViewHolder()
            holder.rank = view.findViewById(R.id.rank) as TextView
            holder.image = view.findViewById(R.id.logo_image) as ImageView
            holder.symbol = view.findViewById(R.id.coin) as TextView
            holder.name = view.findViewById(R.id.name) as TextView
            holder.cap = view.findViewById(R.id.market_cap) as TextView
            holder.volume = view.findViewById(R.id.volume) as TextView
            holder.price = view.findViewById(R.id.price) as TextView
            holder.priceChange = view.findViewById(R.id.change_price) as TextView
            holder.pricePercent = view.findViewById(R.id.change24h) as TextView

            view.tag = holder

        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val rank = holder.rank
        val image = holder.image
        val symbol = holder.symbol
        val name = holder.name
        val cap = holder.cap
        val volume = holder.volume
        val price = holder.price
        val priceChange = holder.priceChange
        val pricePercent = holder.pricePercent

        val recipe = getItem(position) as ListCoinData

        rank.text = recipe.rank
        symbol.text = recipe.symbol
        name.text = recipe.name
        cap.text = recipe.cap
        volume.text = recipe.volume

        price.text = recipe.price

        if (recipe.change24h.contains("-")) {
            priceChange.text = recipe.change24h
            priceChange.setTextColor(Color.parseColor("#ff5232"))
        }
        else {
            priceChange.text = "+"+recipe.change24h
            priceChange.setTextColor(Color.parseColor("#00af5f"))
        }

        if (recipe.changePercent.contains("-")) {
            pricePercent.text = recipe.changePercent
            pricePercent.setTextColor(Color.parseColor("#ff5232"))
        }
        else {
            pricePercent.text = "+"+recipe.changePercent
            pricePercent.setTextColor(Color.parseColor("#00af5f"))
        }

        val picasso = Picasso.get()
        picasso.load(recipe.imageLogo).into(image)

        return view
    }

    private class ViewHolder {
        lateinit var rank: TextView
        lateinit var image: ImageView
        lateinit var symbol: TextView
        lateinit var name: TextView
        lateinit var cap: TextView
        lateinit var volume: TextView
        lateinit var price: TextView
        lateinit var priceChange: TextView
        lateinit var pricePercent: TextView
    }

}