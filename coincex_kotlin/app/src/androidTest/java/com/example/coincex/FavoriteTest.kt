package com.example.coincex

import android.content.Context
import android.widget.ImageView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coincex.api_data.CoinData
import com.example.coincex.fragment_menu.FavoritesFragment
import com.example.coincex.fragment_menu.MarketFragment

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class FavoriteTest {

    private lateinit var coinData: CoinData
    lateinit var context: Context

    @Before
    fun setUp() {
        coinData = CoinData(
            "bitcoin",
            "25000",
            "20000",
            "10000",
            "15000",
            "50000",
            "25%",
            "2022-06-15",
            "1",
            "bitcoin.jpg",
            "BTC",
            "bitcoin",
            "1000000",
            "50000",
            "23555",
            "-2000",
            "-2%")
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun test() {
        val market = MarketFragment()
        market.onClickRank(coinData.id,ImageView(context),context)
        assertEquals(FavoritesFragment.getPreferences(coinData.id, context), "bitcoin")
    }

}