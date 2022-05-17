package com.example.coincex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.coincex.fragment_menu.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navigation: BottomNavigationView

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    override fun onPause() {
        super.onPause()
        auth.signOut()
        navigation = findViewById(R.id.navigation)
        navigation.selectedItemId = R.id.login
        setCurrentFragment(LoginFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val marketFragment = MarketFragment() // Fragment principale
        val favoriteFragment = FavoritesFragment() // Fragment preferiti
        val loginFragment = LoginFragment() // Fragment di Login
        val walletFragment = WalletFragment() // Fragment del wallet
        val notLoggedFragment = NotLoggedFragment() // Fragment per utente non loggato
        val newsFragment = NewsFragment() // Fragment per news
        val loggedFragment = LoggedFragment() // Fragment per utente loggato

        navigation = findViewById(R.id.navigation)
        navigation.selectedItemId = R.id.home

        // Funzione che imposta il fragment principale a quello corrente
        setCurrentFragment(marketFragment)

        // Listener che imposta il fragment in base al click nel BottomNavigationView
        navigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.wallet -> {
                    if (auth.currentUser == null)
                        setCurrentFragment(notLoggedFragment)
                    else
                        setCurrentFragment(walletFragment)
                }
                R.id.home -> setCurrentFragment(marketFragment)
                R.id.prefe -> setCurrentFragment(favoriteFragment)
                R.id.login -> {
                    if (auth.currentUser == null)
                        setCurrentFragment(loginFragment)
                    else
                        setCurrentFragment(loggedFragment)
                }
                R.id.notizie -> setCurrentFragment(newsFragment)
            }
            true
        }
    }

    // Funzione che inserisce il fragment passato nel fragment container presente nell'activity_main
    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
    }

}