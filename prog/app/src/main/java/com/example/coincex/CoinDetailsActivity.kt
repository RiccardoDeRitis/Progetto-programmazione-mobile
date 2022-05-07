package com.example.coincex

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.ListCoinData
import com.squareup.picasso.Picasso

class CoinDetailsActivity: AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details_layout)

        val coin = intent.getSerializableExtra("item") as ListCoinData

        val logo = findViewById<ImageView>(R.id.logo_image)
        val symbol = findViewById<TextView>(R.id.name_coin)

        val max24H = findViewById<TextView>(R.id.textView28)
        val min24H = findViewById<TextView>(R.id.textView35)
        val ath = findViewById<TextView>(R.id.textView34)

        val picasso = Picasso.get()
        picasso.load(coin.imageLogo).into(logo)

        symbol.text = coin.symbol

        val url = "https://www.tradingview.com/chart/?symbol=${coin.symbol}USDT"

        val tradingView = findViewById<WebView>(R.id.tradingview)
        tradingView.settings.loadsImagesAutomatically = true
        tradingView.settings.javaScriptCanOpenWindowsAutomatically = true
        tradingView.settings.javaScriptEnabled = true
        tradingView.webViewClient = WebViewClass()
        tradingView.loadUrl(url)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}