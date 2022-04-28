package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class CoinData (
    val rank: String,
    val imageLogo: String,
    val symbol: String,
    val name: String,
    val cap: String,
    val volume: String,
    val price: String,
    val change24h: String,
    val changePercent: String) {

    companion object {

        fun getDataFromApi(context: Context, callback:(result: String) -> Unit) {
            val url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
                    callback(it)
                }
            ) {
                callback(it.toString())
            }
            queue.add(stringRequest)
        }

        fun getData(data: String): ArrayList<CoinData> {

            val recipeList = ArrayList<CoinData>()
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            val jsonData = JSONArray(data)

            for (i in 0 until jsonData.length()) {
                val symbol :String = jsonData.getJSONObject(i).getString("symbol").uppercase()
                val rank = jsonData.getJSONObject(i).getInt("market_cap_rank").toString()
                val name = jsonData.getJSONObject(i).getString("name")
                val image = jsonData.getJSONObject(i).getString("image")
                val cap = "$"+df.format(jsonData.getJSONObject(i).getDouble("market_cap")/1000000000).toString()+" B"
                val volume: String = if (jsonData.getJSONObject(i).getDouble("total_volume") > 1000000000)
                    "$"+df.format(jsonData.getJSONObject(i).getDouble("total_volume")/1000000000).toString()+" B"
                else
                    "$"+df.format(jsonData.getJSONObject(i).getDouble("total_volume")/1000000).toString()+" M"
                val price = "$"+jsonData.getJSONObject(i).getDouble("current_price").toString()
                val price24h = df.format(jsonData.getJSONObject(i).getDouble("price_change_24h")).toString()+"$"
                val pricePercent = df.format(jsonData.getJSONObject(i).getDouble("price_change_percentage_24h")).toString()+"%"
                recipeList.add(CoinData(rank,image,symbol,name,cap,volume,price,price24h,pricePercent))
            }

            return recipeList
        }
    }
}