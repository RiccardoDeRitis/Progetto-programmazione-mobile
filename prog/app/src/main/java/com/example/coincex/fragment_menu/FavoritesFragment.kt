package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.R

class FavoritesFragment: Fragment() {

    fun deletePreferences(id: String, context: Context) {
        val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove(id)
        editor.apply()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val favoriteCoin: ArrayList<ListCoinData> = ArrayList()

        val listCoinFavorite = view.findViewById<RecyclerView>(R.id.listCoinFavorites)
        listCoinFavorite?.layoutManager = LinearLayoutManager(view.context)

        val title = view.findViewById<TextView>(R.id.titleFavorite)
        val recipeList = MarketFragment.recipeList
        for (coin in recipeList)
            if (coin.id == MarketFragment.getPreferences(coin.id, view.context))
                favoriteCoin.add(coin)
        if (favoriteCoin.isEmpty()) {
            title.text = "Non stai seguendo alcun asset, torna indietro e tieni traccia dei tuoi asset preferiti"
            title.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        else {
            title.text = "I tuoi asset preferiti :"
            val adapter = CoinAdapter(favoriteCoin, true)
            listCoinFavorite?.adapter = adapter
        }

    }

}