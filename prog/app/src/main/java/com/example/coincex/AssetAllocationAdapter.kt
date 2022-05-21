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

class AssetAllocationAdapter(private val data: ArrayList<AssetAllocationDataClass>):
    RecyclerView.Adapter<AssetAllocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_recipe_asset_wallet, parent, false)
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
        private val color = view.findViewById(R.id.logo_image2) as ImageView
        private val name = view.findViewById(R.id.textView75) as TextView
        private val percent = view.findViewById(R.id.textView76) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(coin: AssetAllocationDataClass) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN

            color.setBackgroundColor(coin.color)
            name.text = coin.symbol
            percent.text = df.format(coin.percent).toString()+" %"
        }

    }
}