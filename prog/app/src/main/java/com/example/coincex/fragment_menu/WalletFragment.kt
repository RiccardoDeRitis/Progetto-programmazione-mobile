package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.WalletAdapter
import com.example.coincex.WalletCoinDataClass
import com.example.coincex.api_data.WalletData
import ir.mahozad.android.PieChart
import java.text.DecimalFormat

class WalletFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val coinWallet = view.findViewById<RecyclerView>(R.id.coinWallet)
        val tot = view.findViewById<TextView>(R.id.textView71)
        val totText = view.findViewById<TextView>(R.id.textView70)

        val color1 = Color.parseColor("#e5be01")
        val color2 = Color.parseColor("#FFBB86FC")
        val color3 = Color.parseColor("#007fff")
        val color4 = Color.parseColor("#cb3234")
        val color5 = Color.parseColor("#FF01BAA7")

        val listColor = ArrayList<Int>()
        listColor.add(color1)
        listColor.add(color2)
        listColor.add(color3)
        listColor.add(color4)
        listColor.add(color5)

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        val walletCoinData = ArrayList<WalletCoinDataClass>()
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar6)

        progressBar.visibility = View.VISIBLE
        WalletData.getDataFromApi(view.context) { result ->
            val listAsset = WalletData.getData(result) // WalletData asset quantity

            WalletData.getPriceAsset(view.context, listAsset) { res ->
                val priceList = WalletData.getDataPrice(res) // WalletData asset price
                var totBalance = 0.0

                for ((j, asset) in listAsset.withIndex()) {
                    val element = priceList.stream().filter {
                        it.name == asset.name
                    }.findFirst().orElse(null)
                    val i = priceList.indexOf(element)
                    walletCoinData.add(WalletCoinDataClass(listColor[j],
                        priceList[i].name,
                        asset.value,
                        priceList[i].value))
                    totBalance += asset.value*priceList[i].value
                }

                pieChart.apply {
                    var i = 0
                    slices = walletCoinData.toList().map {
                        PieChart.Slice(((it.price*it.quantity)/totBalance).toFloat(), listColor[i++], legend = it.name)
                    }
                }

                coinWallet.layoutManager = LinearLayoutManager(view.context)
                val adapter = WalletAdapter(walletCoinData)
                coinWallet.adapter = adapter
                progressBar.visibility = View.GONE
                pieChart.visibility = View.VISIBLE
                totText.visibility = View.VISIBLE
                tot.text = DecimalFormat("#.##").format(totBalance).toString()+"$"
            }
        }

    }

}