package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.*
import com.example.coincex.api_data.WalletData
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.lang.Exception

class WalletFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val coinWallet = view.findViewById<RecyclerView>(R.id.coinWallet)
        val assetWallet = view.findViewById<RecyclerView>(R.id.coinWallet2)
        val layout = view.findViewById<ConstraintLayout>(R.id.layoutWallet)

        val currentUser = MainActivity.currentUser

        val color1 = Color.parseColor("#007fff")
        val color2 = Color.parseColor("#cb3234")
        val color3 = Color.parseColor("#e5be01")
        val color4 = Color.parseColor("#FFBB86FC")
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

        progressBar.visibility = View.VISIBLE
        WalletData.getDataFromApi(view.context, currentUser.apikey, currentUser.secretKey) { result ->
            try {
                val listAsset = WalletData.getData(result) // WalletData asset quantity

                WalletData.getPriceAsset(view.context, listAsset) { res ->
                    val priceList = WalletData.getDataPrice(res) // WalletData asset price
                    var totBalance = 0.0

                    val assetData = ArrayList<AssetAllocationDataClass>()

                    for ((j, asset) in listAsset.withIndex()) {
                        val element = priceList.stream().filter {
                            it.name == asset.name
                        }.findFirst().orElse(null)
                        val i = priceList.indexOf(element)
                        walletCoinData.add(
                            WalletCoinDataClass(
                                listColor[j],
                                priceList[i].name,
                                asset.value,
                                priceList[i].value
                            )
                        )
                        totBalance += asset.value * priceList[i].value
                    }

                    for (coin in walletCoinData) {
                        assetData.add(AssetAllocationDataClass(coin.color, coin.name, (((coin.price*coin.quantity)/totBalance)*100).toFloat()))
                    }

                    pieChart.apply {
                        var i = 0
                        for (coin in walletCoinData)
                            addPieSlice(PieModel(((coin.price*coin.quantity)/totBalance).toFloat(), listColor[i++]))
                    }

                    pieChart.startAnimation()

                    assetWallet.layoutManager = LinearLayoutManager(view.context)
                    val adapter2 = AssetAllocationAdapter(assetData)
                    assetWallet.adapter = adapter2

                    coinWallet.layoutManager = LinearLayoutManager(view.context)
                    val adapter = WalletAdapter(walletCoinData)
                    coinWallet.adapter = adapter
                    progressBar.visibility = View.GONE
                    layout.visibility = View.VISIBLE

                }
            } catch (e: Exception) {
                Toast.makeText(view.context, "Errore nel caricamento dei dati, riprovare", Toast.LENGTH_SHORT).show()
            }
        }
    }
}