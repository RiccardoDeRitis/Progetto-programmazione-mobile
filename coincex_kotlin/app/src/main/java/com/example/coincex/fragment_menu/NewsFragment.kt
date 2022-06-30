package com.example.coincex.fragment_menu

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.api_data.NewsData
import com.example.coincex.list_adapter.NewsAdapter
import com.example.coincex.list_adapter.RecyclerViewItemDecoration

class NewsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listNews = view.findViewById<RecyclerView>(R.id.listNews)

        val dialog = Dialog(view.context)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        NewsData.getDataFromApi(view.context) {
            if (it == "null") {
                dialog.dismiss()
                Toast.makeText(view.context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                listNews.layoutManager = LinearLayoutManager(view.context)
                val adapter = NewsAdapter(NewsData.getNews(it)) { news -> onClickItem(news) }
                listNews.adapter = adapter
                listNews.addItemDecoration(RecyclerViewItemDecoration(view.context, R.drawable.divider))
            }
        }

    }

    private fun onClickItem(news: NewsData) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(news.url)
        startActivity(intent)
    }

}