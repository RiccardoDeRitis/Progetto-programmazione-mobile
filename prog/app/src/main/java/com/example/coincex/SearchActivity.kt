package com.example.coincex

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.api_data.SearchCoinData
import com.example.coincex.fragment_menu.MarketFragment

class SearchActivity: AppCompatActivity() {

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
                        val adapter = SearchAdapter(listCoinSearch)
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

}