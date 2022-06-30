package com.example.coincex.list_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.api_data.NewsData
import com.squareup.picasso.Picasso

class NewsAdapter(private val data: ArrayList<NewsData>,
                  private val onClickItem: (news: NewsData) -> Unit):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.news_recipe, parent, false)
        return ViewHolder(view,onClickItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(view: View,
                     val onClickItem: (news: NewsData) -> Unit):
        RecyclerView.ViewHolder(view) {

        private val title = view.findViewById(R.id.textView96) as TextView
        private val description = view.findViewById(R.id.textView97) as TextView
        private val image = view.findViewById(R.id.logo_image10) as ImageView
        private val date = view.findViewById(R.id.textView98) as TextView
        private val source = view.findViewById(R.id.textView99) as TextView
        private var currentNews: NewsData? = null

        init {
            itemView.setOnClickListener { _ ->
                currentNews?.let {
                    onClickItem(it)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(news: NewsData) {
            currentNews = news
            title.text = news.title
            if (news.description.length >= 100)
                description.text = news.description.substring(0,100)+"..."
            else
                description.text = news.description
            date.text = news.date.substring(0,10)
            source.text = news.source

            val picasso = Picasso.get()
            picasso.load(news.image).into(image)
        }
    }

}