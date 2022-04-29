package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class CoinData(
    val title: String,
    val details: String,
    val category: String,
    val whitepaper: String,
    val website: String,
    val background_details: String,
    val regulation: String,
    val token_name: String,
    val blockchain_explorer: String,
    val token_usage: String,
    val launch_details: String,
    val consensus_name: String,
    val consensus_details: String,
    val block_reward: String,
    val algorithm: String,
    val technology_details: String,
    val governance_details: String) {

    companion object {

        fun getProfileFromApi(context: Context, symbol: String, callback:(result: String) -> Unit) {
            val url = "https://data.messari.io/api/v2/assets/${symbol}/profile"
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

        fun getDataProfile(data: String) {

        }

    }
}