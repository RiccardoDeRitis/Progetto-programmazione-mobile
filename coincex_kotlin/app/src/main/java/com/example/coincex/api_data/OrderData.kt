package com.example.coincex.api_data

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class OrderData {

    companion object {

        fun orderApiData(context: Context,
                         symbol: String,
                         apikey: String,
                         secret: String,
                         quantity: String,
                         order: String,
                         callback: (result: String) -> Unit) {
            val mills = System.currentTimeMillis()
            val params = "symbol=$symbol&side=$order&type=MARKET&quantity=$quantity&recvWindow=50000&timestamp=$mills"
            val hashing = Hashing.hmacSha256(secret.toByteArray(StandardCharsets.UTF_8))
                .hashString(params, StandardCharsets.UTF_8).toString()
            val url = "https://api.binance.com/api/v3/order?$params&signature=$hashing"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = object: StringRequest(
                Method.POST, url,
                {
                    callback(it)
                },
                {
                    callback("null")
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers: HashMap<String, String> = HashMap()
                    headers["X-MBX-APIKEY"] = apikey
                    return headers
                }
            }
            queue.add(stringRequest)
        }

    }
}