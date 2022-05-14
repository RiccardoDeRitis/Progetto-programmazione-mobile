package com.example.coincex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.fragment_menu.FavoritesFragment
import com.example.coincex.fragment_menu.MarketFragment

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

        val searchCoin = findViewById<RecyclerView>(R.id.SearchListCoin)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    SearchCoinData.getDataFromApi(applicationContext, p0) {
                        searchCoin.layoutManager = LinearLayoutManager(applicationContext)
                        val listCoinSearch = SearchCoinData.getData(it)
                        adapter = SearchAdapter(
                            listCoinSearch,
                            { coin -> onClickItem(coin, applicationContext) },
                            { id, preferred -> onClickStar(id, applicationContext, preferred) }
                        )
                        searchCoin.adapter = adapter
                    }
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

    }

    override fun onBackPressed() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,MarketFragment())
            commit()
        }
    }

    private fun onClickItem(coin: SearchCoinData,context: Context) {
        SearchCoinData.getCoinDataFromApi(context, coin.id) { result ->
            val listCoin = ListCoinData.getData(result)
            val intent = Intent(context, CoinChartActivity::class.java)
            intent.putExtra("item", listCoin[0])
            startActivity(intent)
        }
    }

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