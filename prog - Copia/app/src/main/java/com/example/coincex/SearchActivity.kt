package com.example.coincex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.SearchCoinData

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

        searchCoin.setOnItemClickListener { _, _, position, _ ->
            SearchCoinData.getCoinDataFromApi(applicationContext, listCoinSearch[position].id) {
                val listCoin = ListCoinData.getData(it)
                val intent = Intent(applicationContext, CoinDetailsActivity::class.java)
                intent.putExtra("item", listCoin[0])
                Log.d("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",listCoin[0].name)
                startActivity(intent)
            }
        }

    }


}