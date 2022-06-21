package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coincex.activity.CoinChartActivity
import com.example.coincex.api_data.CoinData
import com.example.coincex.api_data.GlobalData
import com.example.coincex.R
import com.example.coincex.activity.SearchActivity
import com.example.coincex.list_adapter.CoinAdapter
import com.example.coincex.list_adapter.RecyclerViewItemDecoration

class MarketFragment: Fragment() {

    private lateinit var dialog: Dialog
    private lateinit var listView: RecyclerView

    // Oggetto contenente 2 attributi statici e un metodo per salvare gli id delle coin preferite
    companion object {
        lateinit var recipe: ArrayList<CoinData>

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

        val imageMarket = view.findViewById<ImageView>(R.id.logo_image11)
        val imageVolume = view.findViewById<ImageView>(R.id.logo_image12)
        val imageBTC = view.findViewById<ImageView>(R.id.logo_image13)

        listView = view.findViewById(R.id.listCoin)

        // Listener per il refresh dei dati all'interno della lista
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            getData(view.context)
            dialog.dismiss()
            swipeRefresh.isRefreshing = false
        }

        val search = view.findViewById<Button>(R.id.search)

        // Listener per passare all'activity di ricerca
        search.setOnClickListener {
            val intent = Intent(view.context, SearchActivity::class.java)
            startActivity(intent)
        }

        dialog = Dialog(view.context)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getData(view.context)

        // Esegue la chiamata all'api e setta le info globali con diversi colori a seconda dell'andamento
        GlobalData.getDataFromApi(view.context) {
            if (it == "null") {
                dialog.dismiss()
                Toast.makeText(view.context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                val recipe = GlobalData.getData(it)

                marketCapText.text = recipe.marketCap
                volumeCapText.text = recipe.volumeCap
                btcDominanceText.text = recipe.btcDom

                if (recipe.marketChange.contains("-")) {
                    imageMarket.setBackgroundColor(Color.parseColor("#FFFF5252"))
                    changeCapText.text = recipe.marketChange
                    changeCapText.setTextColor(Color.parseColor("#FFFF5252"))
                } else {
                    imageMarket.setBackgroundColor(Color.parseColor("#FF00E676"))
                    changeCapText.text = "+" + recipe.marketChange
                    changeCapText.setTextColor(Color.parseColor("#FF00E676"))
                }

                if (recipe.volumeChange.contains("-")) {
                    imageVolume.setBackgroundColor(Color.parseColor("#FFFF5252"))
                    changeVolumeText.text = recipe.volumeChange
                    changeVolumeText.setTextColor(Color.parseColor("#FFFF5252"))
                } else {
                    imageVolume.setBackgroundColor(Color.parseColor("#FF00E676"))
                    changeVolumeText.text = "+" + recipe.volumeChange
                    changeVolumeText.setTextColor(Color.parseColor("#FF00E676"))
                }

                if (recipe.btcDomChange.contains("-")) {
                    imageBTC.setBackgroundColor(Color.parseColor("#FFFF5252"))
                    changeBtcText.text = recipe.btcDomChange
                    changeBtcText.setTextColor(Color.parseColor("#FFFF5252"))
                } else {
                    imageBTC.setBackgroundColor(Color.parseColor("#FF00E676"))
                    changeBtcText.text = "+" + recipe.btcDomChange
                    changeBtcText.setTextColor(Color.parseColor("#FF00E676"))
                }
            }
        }
    }

    private fun getData(context: Context) {
        dialog.show()
        CoinData.getDataFromApi(context) {
            if (it == "null")
                Toast.makeText(context, "Contenuto non disponibile", Toast.LENGTH_SHORT).show()
            else {
                listView.layoutManager = LinearLayoutManager(context)
                recipe = CoinData.getData(it)
                val adapter = CoinAdapter(
                    recipe,
                    false,
                    { coin -> onClickItem(coin, context) },
                    { id, _, preferred -> onClickRank(id, preferred, context) }
                )
                listView.adapter = adapter
                listView.addItemDecoration(RecyclerViewItemDecoration(context, R.drawable.divider))
            }
        }
    }

    private fun onClickItem(coinData: CoinData, context: Context) {
        val intent = Intent(context, CoinChartActivity::class.java)
        intent.putExtra("item", coinData)
        context.startActivity(intent)
    }

    fun onClickRank(id: String, preferred: ImageView, context: Context) {
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
        }
    }

}