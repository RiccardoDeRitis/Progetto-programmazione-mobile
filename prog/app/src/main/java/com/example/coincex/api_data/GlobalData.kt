package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

// Data class per le info globali del mercato
data class GlobalData(
    val marketCap: String,
    val volumeCap: String,
    val btcDom: String,
    val marketChange: String,
    val volumeChange: String,
    val btcDomChange: String) {

    // Oggetto contenente metodi per ottenere dati attraverso le API di CoinMarketCap
    companion object {

        // Metodo asincrono che restituisce un callback contenente il risultato della chiamata
        fun getDataFromApi(context: Context, callback:(result: String) -> Unit) {
            val url = "https://pro-api.coinmarketcap.com/v1/global-metrics/quotes/latest?CMC_PRO_API_KEY=e3dc6624-b531-4a8e-b501-7474e1e2455a"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
                    callback(it)
                }
            ) { callback("null") }
            queue.add(stringRequest)
        }

        // Metodo per il bind dei dati e restituisce la data class
        fun getData(data: String): GlobalData {

            val marketCap: String
            val volumeCap: String
            val btcDominance: String
            val marketChange: String
            val volumeChange: String
            val btcDominanceChange: String

            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN

            val jsonData = JSONObject(data).getJSONObject("data")
            marketCap = if (jsonData.getJSONObject("quote").getJSONObject("USD").getDouble("total_market_cap") > 1000000000000)
                "$"+df.format(jsonData.getJSONObject("quote").getJSONObject("USD").getDouble("total_market_cap")/1000000000000).toString()+" T"
            else
                "$"+df.format(jsonData.getJSONObject("quote").getJSONObject("USD").getDouble("total_market_cap")/1000000000).toString()+" B"
            volumeCap = "$"+df.format(jsonData.getJSONObject("quote").getJSONObject("USD").getDouble("total_volume_24h")/1000000000).toString()+" B"
            btcDominance = df.format(jsonData.getDouble("btc_dominance")).toString()+"%"
            marketChange = df.format(jsonData.getJSONObject("quote").getJSONObject("USD").getDouble("total_market_cap_yesterday_percentage_change")).toString()+"%"
            volumeChange = df.format(jsonData.getJSONObject("quote").getJSONObject("USD").getDouble("total_volume_24h_yesterday_percentage_change")).toString()+"%"
            btcDominanceChange = df.format(jsonData.getDouble("btc_dominance_24h_percentage_change")).toString()+"%"

            return GlobalData(marketCap,volumeCap,btcDominance,marketChange,volumeChange,btcDominanceChange)
        }
    }
}