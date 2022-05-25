package com.example.coincex.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.R
import com.example.coincex.api_data.ListCoinData
import com.squareup.picasso.Picasso

class CoinChartActivity: AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_chart_layout)

        val coin = intent.getSerializableExtra("item") as ListCoinData

        val logo = findViewById<ImageView>(R.id.logo_image)
        val symbol = findViewById<TextView>(R.id.name_coin)
        val rank = findViewById<TextView>(R.id.textView38)
        val max24H = findViewById<TextView>(R.id.textView28)
        val min24H = findViewById<TextView>(R.id.textView35)
        val ath = findViewById<TextView>(R.id.textView34)
        val dateAth = findViewById<TextView>(R.id.textView32)
        val changeAthPercent = findViewById<TextView>(R.id.textView30)
        val circulatingSupply = findViewById<TextView>(R.id.textView40)
        val maxSupply = findViewById<TextView>(R.id.textView44)
        val moreDetails = findViewById<Button>(R.id.button7)

        val picasso = Picasso.get()
        picasso.load(coin.imageLogo).into(logo)

        symbol.text = coin.symbol
        rank.text = coin.rank
        max24H.text = coin.max24h
        min24H.text = coin.min24h
        ath.text = coin.ath
        dateAth.text = coin.dateAth
        circulatingSupply.text = coin.circulatingSupply
        maxSupply.text = coin.maxSupply
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

        val chart = findViewById<WebView>(R.id.tradingview)
        chart.settings.javaScriptEnabled = true
        chart.setBackgroundColor(Color.BLACK)
        chart.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null)

        // Listener per aprire l'activity CoinDetailsActivity passandogli symbol e logo
        moreDetails.setOnClickListener {
            val intent = Intent(applicationContext, CoinDetailsActivity::class.java)
            intent.putExtra("symbol", coin.name.lowercase())
            intent.putExtra("logo", coin.imageLogo)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}