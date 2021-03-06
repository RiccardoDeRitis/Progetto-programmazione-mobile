package com.example.coincex.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.api_data.CoinData
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.fragment_menu.FavoritesFragment
import com.example.coincex.fragment_menu.MarketFragment
import com.example.coincex.list_adapter.SearchAdapter

class SearchActivity: AppCompatActivity() {

    companion object {
        lateinit var adapter: SearchAdapter
    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.queryHint = "Search by name or symbol.."
        searchView.isIconified = false

        val text = findViewById<TextView>(R.id.textView95)
        val searchCoin = findViewById<RecyclerView>(R.id.SearchListCoin)
        val dialog = Dialog(this@SearchActivity)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Listener per la ricerca delle coin/token attraverso una SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            /*
            Metodo che al click del pulsante search ritorna tutte le coin che contengono la stringa immessa
            La query viene inoltrata al metodo getDataFromApi che ritorna tutte le coin
            e queste vengono poi messe in un RecyclerView attraverso un Adapter
             */
            override fun onQueryTextSubmit(p0: String?): Boolean {
                dialog.show()
                if (p0 != null) {
                    SearchCoinData.getDataFromApi(applicationContext, p0) {
                        searchCoin.layoutManager = LinearLayoutManager(applicationContext)
                        val listCoinSearch = SearchCoinData.getData(it)
                        if (listCoinSearch.isEmpty())
                            text.visibility = View.VISIBLE
                        else {
                            text.visibility = View.GONE
                            adapter = SearchAdapter(
                                listCoinSearch,
                                { coin -> onClickItem(coin, applicationContext) },
                                { id, preferred -> onClickStar(id, applicationContext, preferred) }
                            )
                            dialog.dismiss()
                            searchCoin.adapter = adapter
                        }
                    }
                }
                dialog.dismiss()
                return false
            }

            // Metodo per ricevere i dati in tempo reale con la digitazione (non implementata)
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

    }

    // Metodo eseguito al click di un item nella lista, passato attraverso il metodo onClickItem presente nell'adapter
    // che apre l'activity CoinChartActivity passandogli la coin selezionata
    private fun onClickItem(coin: SearchCoinData,context: Context) {
        SearchCoinData.getCoinDataFromApi(context, coin.id) { result ->
            val listCoin = CoinData.getData(result)
            val intent = Intent(context, CoinChartActivity::class.java)
            intent.putExtra("item", listCoin[0])
            startActivity(intent)
        }
    }

    // Metodo eseguito al click della stella nella lista, passato attraverso il metodo onClickItem presente nell'adapter
    // Nel caso in cui l'item non fosse un preferito, setta la stella "preferred" e lo inserisce nei preferiti
    // Nel caso in cui l'item ?? un preferito, setta la stella "not_preferred" e lo rimuove dai preferiti
    private fun onClickStar(id: String, context: Context, preferred: ImageView) {
        if (FavoritesFragment.getPreferences(id,context) != "null") {
            FavoritesFragment.deletePreferences(id,context)
            preferred.setBackgroundResource(R.drawable.not_preferred)
        }
        else {
            MarketFragment.savePreferences(id,context)
            preferred.setBackgroundResource(R.drawable.preferred)
        }
    }

}