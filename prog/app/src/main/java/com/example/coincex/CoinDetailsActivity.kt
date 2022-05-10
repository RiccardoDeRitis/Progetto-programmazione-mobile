package com.example.coincex

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.api_data.SearchCoinData
import com.squareup.picasso.Picasso

class CoinDetailsActivity: AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details_layout)

        val coin = intent.getSerializableExtra("item") as ListCoinData

        val logo = findViewById<ImageView>(R.id.logo_image)
        val symbol = findViewById<TextView>(R.id.name_coin)

        val max24H = findViewById<TextView>(R.id.textView28)
        val min24H = findViewById<TextView>(R.id.textView35)
        val ath = findViewById<TextView>(R.id.textView34)
        val dateAth = findViewById<TextView>(R.id.textView32)
        val changeAthPercent = findViewById<TextView>(R.id.textView30)
        val moreDetails = findViewById<Button>(R.id.button7)

        val picasso = Picasso.get()
        picasso.load(coin.imageLogo).into(logo)

        symbol.text = coin.symbol
        max24H.text = coin.max24h
        min24H.text = coin.min24h
        ath.text = coin.ath
        dateAth.text = coin.dateAth
        changeAthPercent.text = coin.athChangePercent

       val url = """
                <div class="tradingview-widget-container">
                    <script type="text/javascript" src="https://s3.tradingview.com/tv.js"></script>
                    <script type="text/javascript">
                        new TradingView.widget({
                            "autosize": true,
                            "symbol": "${coin.symbol}USDT",
                            "interval": "D",
                            "timezone": "Europe/Rome",
                            "theme": "dark",
                            "style": "1",
                            "locale": "en",
                            "toolbar_bg": "#141418",
                            "enable_publishing": false,
                            "allow_symbol_change": false,
                            "save_image": false,
                        });
                    </script>
                </div>
       """

        val tradingView = findViewById<WebView>(R.id.tradingview)
        tradingView.settings.javaScriptEnabled = true
        tradingView.setBackgroundColor(Color.BLACK)
        tradingView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null)

        moreDetails.setOnClickListener {
            val intent = Intent(applicationContext, CoinDetails2Activity::class.java)
            intent.putExtra("symbol", coin.symbol)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}