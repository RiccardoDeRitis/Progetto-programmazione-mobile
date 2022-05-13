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
import com.example.coincex.api_data.SearchCoinData

class FavoritesFragment: Fragment() {

    companion object {

        fun deletePreferences(id: String, context: Context) {
            val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove(id)
            editor.apply()
        }

        fun getPreferences(id: String, context: Context): String? {
            return context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE).getString(id, "null")
        }

        fun getAllPreferences(context: Context, callback: (result: String) -> Unit) {
            val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
            if (sharedPref.all.isNotEmpty())
                for (keys in sharedPref.all)
                    callback(keys.key)
            else
                callback("void")
        }

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

        getAllPreferences(view.context) { id ->
            if (id != "void") {
                SearchCoinData.getCoinDataFromApi(view.context, id) { result ->
                    favoriteCoin.addAll(ListCoinData.getData(result))
                    favoriteCoin.sortBy { it.symbol }
                    title.text = "I tuoi asset preferiti :"
                    val adapter = CoinAdapter(favoriteCoin, true)
                    listCoinFavorite?.adapter = adapter
                }
            }
            else {
                title.text = "Non stai seguendo alcun asset, torna indietro e tieni traccia dei tuoi asset preferiti"
                title.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        }

    }

}