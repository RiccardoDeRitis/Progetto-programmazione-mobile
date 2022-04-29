package com.example.coincex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.ListCoinData

class CoinDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details_layout)

        val coin = intent.getSerializableExtra("item") as ListCoinData

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}