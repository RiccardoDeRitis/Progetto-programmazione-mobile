package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.activity.CoinChartActivity
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.R
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.list_adapter.CoinAdapter

@SuppressLint("SetTextI18n", "NotifyDataSetChanged")
class FavoritesFragment: Fragment() {

    lateinit var title: TextView
    private lateinit var listCoinFavorite: RecyclerView
    lateinit var detective: ImageView
    lateinit var favorite: ImageView

    // Oggetto per la definizione di 2 attributi statici e metodi per eliminare e ottenere gli id delle coin dai preferiti
    companion object {

        lateinit var favoriteCoin: ArrayList<ListCoinData>
        lateinit var adapter: CoinAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        title = view.findViewById(R.id.titleFavorite)

        favoriteCoin = ArrayList()

        listCoinFavorite = view.findViewById(R.id.listCoinFavorites)
        listCoinFavorite.layoutManager = LinearLayoutManager(view.context)
        detective = view.findViewById(R.id.imageView17)
        favorite = view.findViewById(R.id.imageView18)

        // Ritorna un preferito alla volta e lo inserisce in un ArrayList per poi passarlo all'adapter
        // Se non ci sono preferiti viene settato un title che lo riferisce

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar4)

        getAllPreferences(view.context) { id ->
            progressBar.visibility = View.VISIBLE
            if (id != "void") {
                detective.visibility = View.GONE
                SearchCoinData.getCoinDataFromApi(view.context, id) { result ->
                    favoriteCoin.addAll(ListCoinData.getData(result))
                    favoriteCoin.sortBy { it.symbol }
                    listCoinFavorite.visibility = View.VISIBLE
                    title.text = "I tuoi asset preferiti :"
                    adapter = CoinAdapter(
                        favoriteCoin,
                        true,
                        { coin -> onClickItem(coin, view.context) },
                        { id,pos,preferred -> onClickRank(id,pos,preferred, view.context) }
                    )
                    progressBar.visibility = View.GONE
                    listCoinFavorite.adapter = adapter
                }
            }
            else {
                progressBar.visibility = View.GONE
                title.text = "Non stai seguendo alcun asset. Tieni traccia dei tuoi asset preferiti!"
                detective.visibility = View.VISIBLE
                favorite.visibility = View.VISIBLE
            }
        }

    }

    private fun onClickItem(coinData: ListCoinData, context: Context) {
        val intent = Intent(context, CoinChartActivity::class.java)
        intent.putExtra("item", coinData)
        context.startActivity(intent)
    }

    private fun onClickRank(id: String, position: Int, preferred: ImageView, context: Context) {
        if (getPreferences(id,context) != "null") {
            deletePreferences(id,context)
            preferred.visibility = View.GONE
            Toast.makeText(context, "Rimosso dai preferiti", Toast.LENGTH_SHORT).show()
            favoriteCoin.removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, favoriteCoin.size)
            if (favoriteCoin.isEmpty()) {
                title.text = Html.fromHtml("Non stai seguendo alcun asset.\nTieni traccia dei tuoi asset preferiti", 1)
                listCoinFavorite.visibility = View.GONE
                detective.visibility = View.VISIBLE
                favorite.visibility = View.VISIBLE
            }
        }
    }

}