package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coincex.activity.CoinChartActivity
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.GlobalData
import com.example.coincex.R
import com.example.coincex.activity.SearchActivity
import com.example.coincex.list_adapter.CoinAdapter

class MarketFragment: Fragment() {

    lateinit var progressBar: ProgressBar
    private lateinit var listView: RecyclerView

    // Oggetto contenente 2 attributi statici e un metodo per salvare gli id delle coin preferite
    companion object {
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

        // Listener per il refresh dei dati all'interno della lista
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            getData(view.context)
            swipeRefresh.isRefreshing = false
        }

        val search = view.findViewById<Button>(R.id.search)

        // Listener per passare all'activity di ricerca
        search.setOnClickListener {
            val intent = Intent(view.context, SearchActivity::class.java)
            startActivity(intent)
        }

        progressBar = view.findViewById(R.id.progressBar3)
        getData(view.context)

        // Esegue la chiamata all'api e setta le info globali con diversi colori a seconda dell'andamento
        GlobalData.getDataFromApi(view.context) {
            if (it == "null") {
                progressBar.visibility = View.GONE
                Toast.makeText(view.context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.visibility = View.GONE
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
        progressBar.visibility = View.VISIBLE
        ListCoinData.getDataFromApi(context) {
            if (it == "null")
                Toast.makeText(context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            else {
                listView.layoutManager = LinearLayoutManager(context)
                recipeList = ListCoinData.getData(it)
                val adapter = CoinAdapter(
                    recipeList,
                    false,
                    { coin -> onClickItem(coin, context) },
                    { id, _, preferred -> onClickRank(id, preferred, context) }
                )
                listView.adapter = adapter
            }
        }
    }

    private fun onClickItem(coinData: ListCoinData, context: Context) {
        val intent = Intent(context, CoinChartActivity::class.java)
        intent.putExtra("item", coinData)
        context.startActivity(intent)
    }

    private fun onClickRank(id: String, preferred: ImageView, context: Context) {
        if (FavoritesFragment.getPreferences(id,context) != "null") {
            FavoritesFragment.deletePreferences(id,context)
            Toast.makeText(context, "Rimosso dai preferiti", Toast.LENGTH_SHORT).show()
        }
        else {
            savePreferences(id,context)
            preferred.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                preferred.visibility = View.GONE
            }, 500)
            Toast.makeText(context, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show()
        }
    }

}