package com.example.coincex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.api_data.ProfileCoinData
import com.squareup.picasso.Picasso

class CoinDetails2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details2_layout)

        val name = intent.getStringExtra("symbol")
        val logo = intent.getStringExtra("logo")

        val symbol = findViewById<TextView>(R.id.textView33)
        val imageLogo = findViewById<ImageView>(R.id.imageView4)

        symbol.text = name?.replaceFirstChar { it.uppercase() }
        val picasso = Picasso.get()
        picasso.load(logo).into(imageLogo)

        val title = findViewById<TextView>(R.id.title_profile)
        val details = findViewById<TextView>(R.id.details)
        val category = findViewById<TextView>(R.id.category)
        val sector = findViewById<TextView>(R.id.sector)
        val whitepaper = findViewById<TextView>(R.id.whitepaper)
        val website = findViewById<TextView>(R.id.website)
        val backgroundDetails = findViewById<TextView>(R.id.details_back)

        ProfileCoinData.getProfileFromApi(applicationContext, name!!) {
            val profile = ProfileCoinData.getDataProfile(it)

            title.text = profile.title
            details.text = profile.details
            category.text = profile.category
            sector.text = profile.sector
            whitepaper.text = profile.whitepaper
            website.text = profile.website
            backgroundDetails.text = profile.background_details
        }

    }
}