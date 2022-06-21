package com.example.coincex.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.R
import com.example.coincex.api_data.ProfileCoinData
import com.squareup.picasso.Picasso

class CoinDetailsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_details_layout)

        val name = intent.getStringExtra("symbol")
        val logo = intent.getStringExtra("logo")

        val symbol = findViewById<TextView>(R.id.textView33)
        val imageLogo = findViewById<ImageView>(R.id.imageView4)

        val dialog = Dialog(this@CoinDetailsActivity)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val scroll = findViewById<ScrollView>(R.id.scroll)

        dialog.show()

        symbol.text = name?.replaceFirstChar { it.uppercase() }
        val picasso = Picasso.get()
        picasso.load(logo).into(imageLogo)

        val title = findViewById<TextView>(R.id.title_profile)
        val details = findViewById<TextView>(R.id.details)
        val category = findViewById<TextView>(R.id.category)
        val sector = findViewById<TextView>(R.id.sector)
        val whitepaper = findViewById<TextView>(R.id.whitepaper)
        val website = findViewById<TextView>(R.id.website)
        val token = findViewById<TextView>(R.id.token)
        val block = findViewById<TextView>(R.id.block)
        val launch = findViewById<TextView>(R.id.launch)
        val supply = findViewById<TextView>(R.id.supply)
        val emission = findViewById<TextView>(R.id.emission)
        val consensusName = findViewById<TextView>(R.id.consensusName)
        val consensusDetails = findViewById<TextView>(R.id.consensusDetails)
        val reward = findViewById<TextView>(R.id.reward)
        val mining = findViewById<TextView>(R.id.mining)
        val technology = findViewById<TextView>(R.id.technology)
        val governance = findViewById<TextView>(R.id.governance)

        // Ottiene i dati da Messari.io e li inserisci nelle dovute TextView
        ProfileCoinData.getProfileFromApi(applicationContext, name!!) {
            val profile = ProfileCoinData.getDataProfile(it)

            title.text = profile.title
            details.text = Html.fromHtml(profile.details,1)
            category.text = profile.category
            sector.text = profile.sector
            whitepaper.text = Html.fromHtml(profile.whitepaper,1)
            website.text = Html.fromHtml(profile.website,1)
            token.text = Html.fromHtml(profile.token_usage,1)
            block.text = Html.fromHtml(profile.blockchain_explorer,1)
            launch.text = Html.fromHtml(profile.launch_details,1)
            supply.text = Html.fromHtml(profile.supply_details,1)
            emission.text = profile.emission_type
            consensusName.text = Html.fromHtml(profile.consensus_name,1)
            consensusDetails.text = Html.fromHtml(profile.consensus_details,1)
            reward.text = profile.block_reward
            mining.text = profile.algorithm
            technology.text = Html.fromHtml(profile.technology_details,1)
            governance.text = Html.fromHtml(profile.governance_details,1)

            dialog.dismiss()
            scroll.visibility = View.VISIBLE

        }

    }
}