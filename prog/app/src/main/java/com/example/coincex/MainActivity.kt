package com.example.coincex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.coincex.fragment_menu.FavoritesFragment
import com.example.coincex.fragment_menu.LoginFragment
import com.example.coincex.fragment_menu.MarketFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val marketFragment = MarketFragment()
        val favoriteFragment = FavoritesFragment()
        val loginFragment = LoginFragment()

        setCurrentFragment(marketFragment)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> setCurrentFragment(marketFragment)
                R.id.prefe -> setCurrentFragment(favoriteFragment)
                R.id.login -> setCurrentFragment(loginFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
    }

}