package com.example.coincex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.ListCoinData
import com.squareup.picasso.Picasso

class CoinDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details_layout)

        val coin = intent.getSerializableExtra("item") as ListCoinData
        val imageLogo = findViewById<ImageView>(R.id.logo_image)
        val symbol = findViewById<TextView>(R.id.name_coin)
        val volume = findViewById<TextView>(R.id.textView35)

        val picasso = Picasso.get()
        picasso.load(coin.imageLogo).into(imageLogo)

        symbol.text = coin.symbol
        volume.text = coin.volume
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}