package com.example.coincex

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BuyActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_layout)

        val walletData = intent.getSerializableExtra("asset") as ArrayList<WalletCoinDataClass>
        val layoutBuy = findViewById<RelativeLayout>(R.id.relativeLayout)

        val logoBuy = findViewById<ImageView>(R.id.logo_image5)
        val nameBuy = findViewById<TextView>(R.id.textView78)

        val logoSell = findViewById<ImageView>(R.id.logo_image7)
        val nameSell = findViewById<TextView>(R.id.textView82)

        val disponibile = findViewById<TextView>(R.id.textView80)
        disponibile.text = walletData[0].quantity.toString()

        val picasso = Picasso.get()
        picasso.load(walletData[0].logo).into(logoBuy)
        picasso.load(walletData[1].logo).into(logoSell)

        nameBuy.text = walletData[0].symbol
        nameSell.text = walletData[1].symbol

        layoutBuy.setOnClickListener {
            val dialog = Dialog(this@BuyActivity)

            dialog.setContentView(R.layout.dialog_search_coin_buy)

            val logoConvert = dialog.findViewById<ImageView>(R.id.logo_image6)
            val nameConvert = dialog.findViewById<TextView>(R.id.textView85)

            picasso.load(walletData[0].logo).into(logoConvert)
            nameConvert.text = walletData[0].symbol

            val listCoinConvert = dialog.findViewById<RecyclerView>(R.id.listCoinConvert)
            listCoinConvert.layoutManager = LinearLayoutManager(applicationContext)

            val adapter = ConvertAdapter(walletData)
            listCoinConvert.adapter = adapter
            dialog.show()
        }

    }
}