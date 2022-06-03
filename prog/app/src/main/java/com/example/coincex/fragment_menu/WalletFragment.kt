package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.*
import com.example.coincex.activity.BuyActivity
import com.example.coincex.api_data.WalletData
import com.example.coincex.data_class.AssetAllocationDataClass
import com.example.coincex.data_class.WalletCoinDataClass
import com.example.coincex.list_adapter.AssetAllocationAdapter
import com.example.coincex.list_adapter.WalletAdapter
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.lang.Exception
import java.text.DecimalFormat

class WalletFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val coinWallet = view.findViewById<RecyclerView>(R.id.coinWallet)
        val assetWallet = view.findViewById<RecyclerView>(R.id.coinWallet2)
        val layout = view.findViewById<ConstraintLayout>(R.id.layoutWallet)

        val convert = view.findViewById<Button>(R.id.button9)

        val currentUser = MainActivity.currentUser

        val color1 = Color.parseColor("#007fff")
        val color2 = Color.parseColor("#ffa500")
        val color3 = Color.parseColor("#00bd2d")
        val color4 = Color.parseColor("#e5be01")
        val color5 = Color.parseColor("#FF01BAA7")

        val listColor = ArrayList<Int>()
        listColor.add(color1)
        listColor.add(color2)
        listColor.add(color3)
        listColor.add(color4)
        listColor.add(color5)

        val pieChart = view.findViewById<PieChart>(R.id.piechart)
        val walletCoinData = ArrayList<WalletCoinDataClass>()
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar6)
        val tot = view.findViewById<TextView>(R.id.textView91)

        progressBar.visibility = View.VISIBLE
        WalletData.getDataFromApi(view.context, currentUser.apikey, currentUser.secretKey) { result ->
            try {
                val listAsset = WalletData.getData(result) // WalletData asset quantity
                WalletData.getPriceAsset(view.context, listAsset) { res ->
                    val priceList = WalletData.getDataPrice(res) // WalletData asset price
                    var totBalance = 0.0

                    val assetData = ArrayList<AssetAllocationDataClass>()

                    val recipeList = MarketFragment.recipeList

                    for (asset in listAsset) {
                        val element = priceList.stream().filter {
                            it.name == asset.name
                        }.findFirst().orElse(null)
                        val i = priceList.indexOf(element)
                        val element2 = recipeList.stream().filter {
                            it.symbol == asset.name
                        }.findFirst().orElse(null)
                        val k = recipeList.indexOf(element2)
                        if (k == -1)
                            walletCoinData.add(
                                WalletCoinDataClass(
                                    "n/a",
                                    priceList[i].name,
                                    "N/A",
                                    asset.value,
                                    priceList[i].value
                                )
                            )
                        else {
                            if (asset.name == "USDT")
                                walletCoinData.add(
                                    WalletCoinDataClass(
                                        recipeList[k].imageLogo,
                                        recipeList[k].symbol,
                                        recipeList[k].name,
                                        asset.value,
                                        1.0
                                    )
                                )
                            else
                                walletCoinData.add(
                                    WalletCoinDataClass(
                                        recipeList[k].imageLogo,
                                        priceList[i].name,
                                        recipeList[k].name,
                                        asset.value,
                                        priceList[i].value
                                    )
                                )
                        }
                        totBalance += if (asset.name == "USDT")
                            asset.value
                        else
                            asset.value * priceList[i].value
                    }

                    for ((i, coin) in walletCoinData.withIndex()) {
                        assetData.add(AssetAllocationDataClass(listColor[i], coin.name, (((coin.price*coin.quantity)/totBalance)*100).toFloat()))
                    }

                    pieChart.apply {
                        var i = 0
                        for (coin in walletCoinData)
                            addPieSlice(PieModel(((coin.price*coin.quantity)/totBalance).toFloat(), listColor[i++]))
                    }

                    tot.text = DecimalFormat("#.##").format(totBalance)+" $"

                    pieChart.startAnimation()

                    assetWallet.layoutManager = LinearLayoutManager(view.context)
                    val adapter2 = AssetAllocationAdapter(assetData)
                    assetWallet.adapter = adapter2

                    coinWallet.layoutManager = LinearLayoutManager(view.context)
                    val adapter = WalletAdapter(walletCoinData)
                    coinWallet.adapter = adapter
                    convert.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    layout.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Toast.makeText(view.context, "Errore nel caricamento dei dati, riprovare", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }

        convert.setOnClickListener {
            val intent = Intent(view.context, BuyActivity::class.java)
            intent.putExtra("asset", walletCoinData)
            view.context.startActivity(intent)
        }

    }
}