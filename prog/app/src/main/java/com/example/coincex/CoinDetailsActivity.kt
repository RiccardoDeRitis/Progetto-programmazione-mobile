package com.example.coincex

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.fragment_coin_details.ChartFragment
import com.example.coincex.fragment_coin_details.DetailsFragment
import com.squareup.picasso.Picasso

class CoinDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details_layout)

        val coin = intent.getSerializableExtra("item") as ListCoinData

        val chartButton = findViewById<Button>(R.id.button2)
        val detailsButton = findViewById<Button>(R.id.button7)

        val chartFragment = ChartFragment()
        val detailsFragment = DetailsFragment()

        setCurrentFragment(chartFragment)

        chartButton.setOnClickListener {
            setCurrentFragment(chartFragment)
        }

        detailsButton.setOnClickListener {
            setCurrentFragment(detailsFragment)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container,fragment)
        commit()
    }
}