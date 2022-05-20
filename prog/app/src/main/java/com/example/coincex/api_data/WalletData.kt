package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.common.hash.Hashing
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets


data class WalletData(
    val name: String,
    val value: Double
) {

    companion object {

        fun getDataFromApi(context: Context, callback: (result: String) -> Unit) {
            val key = ""
            val mills = System.currentTimeMillis()
            val params = "recvWindow=60000&timestamp=$mills"
            val hashing = Hashing.hmacSha256(key.toByteArray(StandardCharsets.UTF_8))
                .hashString(params, StandardCharsets.UTF_8).toString()
            val url = "https://api.binance.com/api/v3/account?$params&signature=$hashing"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = object: StringRequest(
                Method.GET, url,
                {
                    callback(it)
                },
                {
                    callback("null")
                }
            ) {
                override fun getHeaders(): Map<String, String> {
                    val headers: HashMap<String, String> = HashMap()
                    headers["X-MBX-APIKEY"] = ""
                    return headers
                }
            }
            queue.add(stringRequest)
        }

        fun getData(data: String): ArrayList<WalletData> {
            val assetsList = ArrayList<WalletData>()
            val jsonData = JSONObject(data).getJSONArray("balances")
            try {
                for (i in 0 until jsonData.length()) {
                    if (jsonData.getJSONObject(i).getString("free").toDouble() > 0)
                        assetsList.add(WalletData(jsonData.getJSONObject(i).getString("asset"),
                            jsonData.getJSONObject(i).getString("free").toDouble()))

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return assetsList
        }

        fun getPriceAsset(context: Context, listAsset: ArrayList<WalletData>, callback: (result: String) -> Unit) {
            var url = "https://api.binance.com/api/v3/ticker/price?symbols=["
            for (asset in listAsset) {
                url += if (asset == listAsset[listAsset.size-1])
                    "\"${asset.name}USDT\"]"
                else
                    "\"${asset.name}USDT\","
            }
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
                    callback(it)
                }
            ) {
                it.printStackTrace()
            }
            queue.add(stringRequest)
        }

        fun getDataPrice(data: String): ArrayList<WalletData> {
            val priceList = ArrayList<WalletData>()
            val jsonData = JSONArray(data)

            for (i in 0 until jsonData.length())
                priceList.add(WalletData(jsonData.getJSONObject(i).getString("symbol").replace("USDT", ""),
                    jsonData.getJSONObject(i).getString("price").toDouble()))

            return priceList
        }


    }
}
