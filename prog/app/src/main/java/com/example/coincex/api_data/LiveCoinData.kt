package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LiveCoinData(

) {

    companion object {

        fun getDataFromApi(context: Context, symbol: String, callback:(result: String) -> Unit) {
            val url = "https://data.messari.io/api/v2/assets/${symbol}/markets"
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

        fun getData(data: String) {

        }

    }
}