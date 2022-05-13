package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.io.Serializable
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class ListCoinData (
    val id: String,
    val max24h: String,
    val min24h: String,
    val circulatingSupply: String,
    val maxSupply: String,
    val ath: String,
    val athChangePercent: String,
    val dateAth: String,
    val rank: String,
    val imageLogo: String,
    val symbol: String,
    val name: String,
    val cap: String,
    val volume: String,
    val price: String,
    val change24h: String,
    val changePercent: String): Serializable {

    companion object {

        fun getDataFromApi(context: Context, callback:(result: String) -> Unit) {
            val url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
                    callback(it)
                }
            ) {
                callback("null")
            }
            queue.add(stringRequest)
        }

        fun getData(data: String): ArrayList<ListCoinData> {

            val recipeList = ArrayList<ListCoinData>()
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            val jsonData = JSONArray(data)

            for (i in 0 until jsonData.length()) {
                val id = jsonData.getJSONObject(i).getString("id")
                val max24h = df.format(jsonData.getJSONObject(i).getDouble("high_24h")).toString()+"$"
                val min24h = df.format(jsonData.getJSONObject(i).getDouble("low_24h")).toString()+"$"
                val circulatingSupply = try {
                    if (jsonData.getJSONObject(i).getDouble("circulating_supply") >= 1000000000)
                        df.format(jsonData.getJSONObject(i).getDouble("circulating_supply")/1000000000).toString()+" B"
                    else {
                        if (jsonData.getJSONObject(i).getLong("circulating_supply") in 1000000..1000000000)
                            df.format(jsonData.getJSONObject(i).getDouble("circulating_supply")/1000000).toString()+" M"
                        else
                            df.format(jsonData.getJSONObject(i).getDouble("circulating_supply")/1000).toString()+" K"
                    }
                } catch (e: Exception) {
                    "n/a"
                }
                val maxSupply = try {
                    if (jsonData.getJSONObject(i).getDouble("max_supply") >= 1000000000)
                        df.format(jsonData.getJSONObject(i).getDouble("max_supply")/1000000000).toString()+" B"
                    else {
                        if (jsonData.getJSONObject(i).getLong("max_supply") in 1000000..1000000000)
                            df.format(jsonData.getJSONObject(i).getDouble("max_supply")/1000000).toString()+" M"
                        else
                            df.format(jsonData.getJSONObject(i).getDouble("max_supply")/1000).toString()+" K"
                    }
                } catch (e: Exception) {
                    "n/a"
                }
                val ath = df.format(jsonData.getJSONObject(i).getDouble("ath")).toString()+"$"
                val athChangePercent = df.format(jsonData.getJSONObject(i).getDouble("ath_change_percentage")).toString()+"%"
                val dateAth = jsonData.getJSONObject(i).getString("ath_date").replace("T"," ").substring(0,10)
                val symbol = jsonData.getJSONObject(i).getString("symbol").uppercase()
                val rank = try {
                    jsonData.getJSONObject(i).getInt("market_cap_rank").toString()
                } catch (e: Exception) {
                    "n/a"
                }
                val name = jsonData.getJSONObject(i).getString("name")
                val image = jsonData.getJSONObject(i).getString("image")
                val cap = if (jsonData.getJSONObject(i).getDouble("market_cap") > 1000000000)
                    "$"+df.format(jsonData.getJSONObject(i).getDouble("market_cap")/1000000000).toString()+" B"
                else
                    "$"+df.format(jsonData.getJSONObject(i).getDouble("market_cap")/1000000).toString()+" M"
                val volume = if (jsonData.getJSONObject(i).getDouble("total_volume") > 1000000000)
                    "$"+df.format(jsonData.getJSONObject(i).getDouble("total_volume")/1000000000).toString()+" B"
                else
                    "$"+df.format(jsonData.getJSONObject(i).getDouble("total_volume")/1000000).toString()+" M"
                val price = "$"+jsonData.getJSONObject(i).getDouble("current_price").toString()
                val price24h = df.format(jsonData.getJSONObject(i).getDouble("price_change_24h")).toString()+"$"
                val pricePercent = df.format(jsonData.getJSONObject(i).getDouble("price_change_percentage_24h")).toString()+"%"
                recipeList.add(ListCoinData(id,max24h,min24h,circulatingSupply,maxSupply,ath,athChangePercent,dateAth,rank,image,symbol,name,cap,volume,price,price24h,pricePercent))
            }

            return recipeList
        }
    }
}