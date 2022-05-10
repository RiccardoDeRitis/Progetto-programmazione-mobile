package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SearchCoinData (
    val logo: String,
    val symbol: String,
    val nameCoin: String) {

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

        fun getData(data: String): ArrayList<SearchCoinData> {

            val searchListCoin = ArrayList<SearchCoinData>()
            val jsonData = JSONObject(data).getJSONArray("coins")
            for (i in 0 until jsonData.length()) {
                val logo = jsonData.getJSONObject(i).getString("large")
                val symbol = jsonData.getJSONObject(i).getString("symbol")
                val nameCoin = jsonData.getJSONObject(i).getString("name")
                searchListCoin.add(SearchCoinData(logo, symbol, nameCoin))
            }

            return searchListCoin
        }

    }
}