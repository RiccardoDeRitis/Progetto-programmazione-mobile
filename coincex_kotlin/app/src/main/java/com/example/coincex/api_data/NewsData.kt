package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

data class NewsData (
    val title: String,
    val description: String,
    val url: String,
    val image: String,
    val date: String,
    val source: String){

    companion object {

        fun getDataFromApi(context: Context, callback: (result: String) -> Unit) {
            val url = "https://gnews.io/api/v4/search?q=cryptocurrency&token=56111f69aed38c3370b79abe1bb3c74d&sortBy=publishedAt"
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

        fun getNews(data: String): ArrayList<NewsData> {
            val listNews = ArrayList<NewsData>()
            val jsonData = JSONObject(data).getJSONArray("articles")
            for (i in 0 until jsonData.length()) {
                val title = jsonData.getJSONObject(i).getString("title")
                val description = jsonData.getJSONObject(i).getString("description")
                val url = jsonData.getJSONObject(i).getString("url")
                val image = jsonData.getJSONObject(i).getString("image")
                val date = jsonData.getJSONObject(i).getString("publishedAt")
                val source = jsonData.getJSONObject(i).getJSONObject("source").getString("name")
                listNews.add(NewsData(title,description,url,image,date,source))
            }
            return listNews
        }

    }

}