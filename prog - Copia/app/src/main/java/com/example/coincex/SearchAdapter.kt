package com.example.coincex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.coincex.api_data.SearchCoinData
import com.squareup.picasso.Picasso

class SearchAdapter(context: Context, private val dataSourceList: ArrayList<SearchCoinData>): BaseAdapter() {

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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {

            view = inflater.inflate(R.layout.list_recipe_search_coin, parent, false)

            holder = ViewHolder()
            holder.logo = view.findViewById(R.id.logo_image3) as ImageView
            holder.symbol = view.findViewById(R.id.textView36) as TextView
            holder.name = view.findViewById(R.id.textView31) as TextView

            view.tag = holder

        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val image = holder.logo
        val symbol = holder.symbol
        val name = holder.name

        val recipe = getItem(position) as SearchCoinData

        symbol.text = recipe.symbol
        name.text = recipe.nameCoin

        val picasso = Picasso.get()
        picasso.load(recipe.logo).into(image)

        return view
    }

    private class ViewHolder {
        lateinit var logo: ImageView
        lateinit var symbol: TextView
        lateinit var name: TextView
    }

}