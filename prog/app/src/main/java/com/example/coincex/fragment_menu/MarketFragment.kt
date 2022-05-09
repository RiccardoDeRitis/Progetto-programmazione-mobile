package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coincex.CoinDetailsActivity
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.GlobalData
import com.example.coincex.R
import com.example.coincex.SearchActivity

class MarketFragment: Fragment() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var listView: ListView
        lateinit var recipeList: ArrayList<ListCoinData>

        fun savePreferences(positionArray: ArrayList<Int>, context: Context) {
            val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            for (position in positionArray)
                editor.putInt(position.toString(), position)
            editor.apply()
        }

        fun getPreferences(key: String, context: Context): Int {
            val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
            return sharedPref.getInt(key, -1)
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

        val positionArray = ArrayList<Int>()

        getData(view.context)

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(view.context, CoinDetailsActivity::class.java)
            intent.putExtra("item", recipeList[position])
            startActivity(intent)
        }

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        listView.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
            override fun onItemCheckedStateChanged(mode: ActionMode, position: Int, id: Long, checked: Boolean) {
                swipeRefresh.isEnabled = false
                mode.title="${listView.checkedItemCount} selezionati"
                if (checked)
                    positionArray.add(position)
                else
                    positionArray.remove(position)
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.menu_share -> {
                        savePreferences(positionArray, view.context)
                        positionArray.clear()
                        swipeRefresh.isEnabled = true
                        Toast.makeText(view.context, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show()
                        mode.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val menuInflater: MenuInflater = mode.menuInflater
                menuInflater.inflate(R.menu.pressed_menu, menu)
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                swipeRefresh.isEnabled = true
                positionArray.clear()
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

        })

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
                recipeList = ListCoinData.getData(it)
                val adapter = CoinAdapter(context, recipeList)
                listView.adapter = adapter
            }
        }
    }

}