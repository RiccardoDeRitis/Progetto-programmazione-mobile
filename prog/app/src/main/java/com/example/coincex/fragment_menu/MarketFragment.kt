package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.GlobalData
import com.example.coincex.R
import com.example.coincex.SearchActivity

class MarketFragment: Fragment() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var listView: RecyclerView
        lateinit var recipeList: ArrayList<ListCoinData>

        fun savePreferences(id: String, context: Context) {
            val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(id, id)
            editor.apply()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.market_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val marketCapText = view.findViewById<TextView>(R.id.textView4)
        val volumeCapText = view.findViewById<TextView>(R.id.textView5)
        val btcDominanceText = view.findViewById<TextView>(R.id.textView6)
        val changeCapText = view.findViewById<TextView>(R.id.textView10)
        val changeVolumeText = view.findViewById<TextView>(R.id.textView11)
        val changeBtcText = view.findViewById<TextView>(R.id.textView12)

        listView = view.findViewById(R.id.listCoin)

        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            getData(view.context)
            swipeRefresh.isRefreshing = false
        }

        val search = view.findViewById<Button>(R.id.search)

        search.setOnClickListener {
            val intent = Intent(view.context, SearchActivity::class.java)
            startActivity(intent)
        }

        ListCoinData.getDataFromApi(view.context) {
            if (it == "null")
                Toast.makeText(context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            else {
                listView.layoutManager = LinearLayoutManager(context)
                recipeList = ListCoinData.getData(it)
                val adapter = CoinAdapter(recipeList,false)
                listView.adapter = adapter
            }
        }

        GlobalData.getDataFromApi(view.context) {
            if (it == "null")
                Toast.makeText(view.context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            else {
                val recipe = GlobalData.getData(it)
                marketCapText.text = recipe.marketCap
                volumeCapText.text = recipe.volumeCap
                btcDominanceText.text = recipe.btcDom

                if (recipe.marketChange.contains("-")) {
                    changeCapText.text = recipe.marketChange
                    changeCapText.setTextColor(Color.parseColor("#ff5232"))
                } else {
                    changeCapText.text = "+" + recipe.marketChange
                    changeCapText.setTextColor(Color.parseColor("#00af5f"))
                }

                if (recipe.volumeChange.contains("-")) {
                    changeVolumeText.text = recipe.volumeChange
                    changeVolumeText.setTextColor(Color.parseColor("#ff5232"))
                } else {
                    changeVolumeText.text = "+" + recipe.volumeChange
                    changeVolumeText.setTextColor(Color.parseColor("#00af5f"))
                }

                if (recipe.btcDomChange.contains("-")) {
                    changeBtcText.text = recipe.btcDomChange
                    changeBtcText.setTextColor(Color.parseColor("#ff5232"))
                } else {
                    changeBtcText.text = "+" + recipe.btcDomChange
                    changeBtcText.setTextColor(Color.parseColor("#00af5f"))
                }
            }
        }

    }

    private fun getData(context: Context) {
        ListCoinData.getDataFromApi(context) {
            if (it == "null")
                Toast.makeText(context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            else {
                listView.layoutManager = LinearLayoutManager(context)
                recipeList = ListCoinData.getData(it)
                val adapter = CoinAdapter (recipeList, false)
                listView.adapter = adapter
            }
        }
    }

}