package com.example.coincex

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.api_data.ConvertCoinData
import com.squareup.picasso.Picasso

class BuyActivity: AppCompatActivity() {

    private lateinit var logoBuy: ImageView
    private lateinit var nameBuy: TextView

    private lateinit var logoSell: ImageView
    private lateinit var nameSell: TextView

    private lateinit var logoConvert: ImageView
    private lateinit var nameConvert: TextView

    private lateinit var disponibile: TextView

    private lateinit var dialog: Dialog

    private val picasso = Picasso.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_layout)

        logoBuy = findViewById(R.id.logo_image5)
        nameBuy = findViewById(R.id.textView78)
        logoSell = findViewById(R.id.logo_image7)
        nameSell = findViewById(R.id.textView82)

        dialog = Dialog(this@BuyActivity)
        dialog.setContentView(R.layout.dialog_search_coin_buy)

        val walletData = intent.getSerializableExtra("asset") as ArrayList<WalletCoinDataClass>
        val layoutBuy = findViewById<RelativeLayout>(R.id.relativeLayout)
        val layoutConvert = findViewById<RelativeLayout>(R.id.relativeLayout2)

        logoConvert = dialog.findViewById(R.id.logo_image6)
        nameConvert = dialog.findViewById(R.id.textView85)

        picasso.load(walletData[0].logo).into(logoConvert)
        nameConvert.text = walletData[0].symbol

        disponibile = findViewById(R.id.textView80)
        disponibile.text = walletData[0].quantity.toString()

        picasso.load(walletData[0].logo).into(logoBuy)
        picasso.load(walletData[1].logo).into(logoSell)

        nameBuy.text = walletData[0].symbol
        nameSell.text = walletData[1].symbol

        layoutBuy.setOnClickListener {

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT

            val listCoinConvert = dialog.findViewById<RecyclerView>(R.id.listCoinConvert)
            listCoinConvert.layoutManager = LinearLayoutManager(applicationContext)

            val adapter = ConvertAdapter(walletData) { coin -> onClickItem(coin) }
            listCoinConvert.adapter = adapter
            dialog.show()

            dialog.window?.attributes = lp
        }

        layoutConvert.setOnClickListener {

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT

            ConvertCoinData.getDataFromApi(applicationContext) {
                val listConvertData = ConvertCoinData.getData(it, nameBuy.text.toString())
                val adapter = ConvertAdapter(listConvertData) { coin -> onClickItemConvert(coin) }
                val listCoinConvert = dialog.findViewById<RecyclerView>(R.id.listCoinConvert)
                listCoinConvert.layoutManager = LinearLayoutManager(applicationContext)

                listCoinConvert.adapter = adapter

                dialog.show()
                dialog.window?.attributes = lp
            }
        }

    }

    private fun onClickItem(coin: WalletCoinDataClass) {
        nameBuy.text = coin.symbol
        picasso.load(coin.logo).into(logoBuy)
        disponibile.text = coin.quantity.toString()
        picasso.load(coin.logo).into(logoConvert)
        nameConvert.text = coin.symbol
        dialog.dismiss()
    }

    private fun onClickItemConvert(coin: WalletCoinDataClass) {

    }



}