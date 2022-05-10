package com.example.coincex

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.fragment_menu.CoinAdapter
import com.example.coincex.fragment_menu.MarketFragment

class SearchActivity: AppCompatActivity() {

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)
        var listCoinSearch = ArrayList<SearchCoinData>()

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.queryHint = "Search by name or symbol.."

        val searchCoin = findViewById<ListView>(R.id.SearchCoin)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    SearchCoinData.getDataFromApi(applicationContext, p0) {
                        listCoinSearch = SearchCoinData.getData(it)
                        val adapter = SearchAdapter(applicationContext, listCoinSearch)
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


}