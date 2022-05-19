package com.example.coincex.fragment_menu

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coincex.R
import com.example.coincex.api_data.WalletData
import ir.mahozad.android.PieChart

class WalletFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

        var listAsset: HashMap<String, Float>
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        WalletData.getDataFromApi(view.context) { result ->
            listAsset = WalletData.getData(result)
            listAsset.remove("VIB")
            val list = listAsset.keys.toList()
            WalletData.getPriceAsset(view.context, list as ArrayList<String>) { res ->
                val priceList = WalletData.getDataPrice(res)
                var totBalance = 0.0F
                for (asset in listAsset) {
                    totBalance += asset.value* priceList[asset.key]!!
                }
                pieChart.apply {
                    var i = 0
                    slices = listAsset.toList().map {
                        PieChart.Slice((priceList[it.first]!!*it.second)/totBalance, listColor[i++], legend = it.first)
                    }
                    gradientType = PieChart.GradientType.RADIAL
                    legendsIcon = PieChart.DefaultIcons.SQUARE
                }
            }
        }

    }

}