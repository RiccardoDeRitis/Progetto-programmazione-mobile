package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.coincex.data_class.WalletCoinDataClass
import com.example.coincex.fragment_menu.MarketFragment
import org.json.JSONObject

class ConvertCoinData {

    companion object {

        fun getDataFromApi(context: Context, callback:(result: String) -> Unit) {
            val url = "https://api.binance.com/api/v3/exchangeInfo"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
                    callback(it)
                }
            ) { callback("null") }
            queue.add(stringRequest)
        }

        fun getData(data: String, symbol: String): ArrayList<WalletCoinDataClass> {

            val recipeList = MarketFragment.recipe

            val listConvertData = ArrayList<WalletCoinDataClass>()
            val jsonData = JSONObject(data).getJSONArray("symbols")

            for (i in 0 until jsonData.length()) {
                if (jsonData.getJSONObject(i).getString("baseAsset") == symbol) {
                    val element = recipeList.stream().filter {
                        it.symbol == jsonData.getJSONObject(i).getString("quoteAsset")
                    }.findFirst().orElse(null)
                    val j = recipeList.indexOf(element)
                    if (j == -1)
                        listConvertData.add(WalletCoinDataClass("n/a",jsonData.getJSONObject(i).getString("quoteAsset"), "", 0.0, 0.0))
                    else
                        listConvertData.add(WalletCoinDataClass(recipeList[j].imageLogo,jsonData.getJSONObject(i).getString("quoteAsset"), "", 0.0, 0.0))
                }
            }
            return listConvertData

        }

    }
}