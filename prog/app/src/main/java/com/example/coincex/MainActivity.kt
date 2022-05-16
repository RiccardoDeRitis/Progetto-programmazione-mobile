package com.example.coincex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.coincex.fragment_menu.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val marketFragment = MarketFragment() // Fragment principale
        val favoriteFragment = FavoritesFragment() // Fragment preferiti
        val loginFragment = LoginFragment() // Fragment di Login
        val walletFragment = WalletFragment() // Fragment del wallet
        val newsFragment = NewsFragment() // Fragment per news

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.selectedItemId = R.id.home

        // Funzione che imposta il fragment principale a quello corrente
        setCurrentFragment(marketFragment)

        // Listener che imposta il fragment in base al click nel BottomNavigationView
        navigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.wallet -> setCurrentFragment(walletFragment)
                R.id.home -> setCurrentFragment(marketFragment)
                R.id.prefe -> setCurrentFragment(favoriteFragment)
                R.id.login -> setCurrentFragment(loginFragment)
                R.id.notizie -> setCurrentFragment(newsFragment)
            }
            true
        }
    }

    // Funzione che inserisce il fragment passato nel fragment container presente nell'activity_main
    private fun setCurrentFragment(fragment: Fragment, ) = supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
    }

}