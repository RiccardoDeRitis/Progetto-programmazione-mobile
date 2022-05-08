package com.example.coincex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CoinDetails2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details2_layout)

        val symbol = intent.getStringExtra("symbol")


    }
}