package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

class MetricsCoinData(
    val max24h: String,
    val min24h: String,
    val supplyCirculating: String,
    val inflationAnnual: String,
    val ath: String,
    val dateAth: String,
    val priceDownAth: String) {

    companion object {

        fun getDataFromApi(context: Context, symbol: String, callback:(result: String) -> Unit) {
            val urlData = "https://data.messari.io/api/v1/assets/${symbol}/metrics"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, urlData,
                {
                    callback(it)
                }
            ) {
                callback("null")
            }
            queue.add(stringRequest)
        }

        fun getData(data: String) : MetricsCoinData{

            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            val jsonData = JSONObject(data).getJSONObject("data")

            val max24h = df.format(jsonData.getJSONObject("market_data").getJSONObject("ohlcv_last_24_hour").getDouble("high")).toString()
            val min24h = df.format(jsonData.getJSONObject("market_data").getJSONObject("ohlcv_last_24_hour").getDouble("low")).toString()

            val supplyCirculating = try {
                jsonData.getJSONObject("supply").getDouble("circulating").toString()
            } catch (e: Exception) {
                "null"
            }

            val inflationAnnual = try {
                jsonData.getJSONObject("supply").getDouble("annual_inflation_percent").toString()
            } catch (e: Exception) {
                "null"
            }

            val ath = df.format(jsonData.getJSONObject("all_time_high").getDouble("price")).toString()
            val dateAth = jsonData.getJSONObject("all_time_high").getString("at")
            val priceDownAth = df.format(jsonData.getJSONObject("all_time_high").getDouble("percent_down")).toString()

            return MetricsCoinData(max24h, min24h, supplyCirculating, inflationAnnual, ath, dateAth, priceDownAth)
        }

    }
}