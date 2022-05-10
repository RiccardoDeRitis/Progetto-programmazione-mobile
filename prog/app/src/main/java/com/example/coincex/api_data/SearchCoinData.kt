package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.Serializable

class SearchCoinData (
    val id: String,
    val logo: String,
    val symbol: String,
    val nameCoin: String): Serializable {

    companion object {

        fun getDataFromApi(context: Context, query: String, callback:(result: String) -> Unit) {
            val url = "https://api.coingecko.com/api/v3/search?query=$query"
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

        fun getCoinDataFromApi(context: Context, id: String, callback:(result: String) -> Unit) {
            val url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=$id"
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

        fun getData(data: String): ArrayList<SearchCoinData> {

            val searchListCoin = ArrayList<SearchCoinData>()
            val jsonData = JSONObject(data).getJSONArray("coins")
            for (i in 0 until jsonData.length()) {
                val id = jsonData.getJSONObject(i).getString("id")
                val logo = jsonData.getJSONObject(i).getString("large")
                val symbol = jsonData.getJSONObject(i).getString("symbol")
                val nameCoin = jsonData.getJSONObject(i).getString("name")
                searchListCoin.add(SearchCoinData(id, logo, symbol, nameCoin))
            }

            return searchListCoin
        }

    }
}