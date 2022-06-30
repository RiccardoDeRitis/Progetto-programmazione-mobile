package com.example.coincex

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.coincex.data_class.UserDataClass
import com.example.coincex.fragment_menu.*
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var currentUser: UserDataClass
    }

    private var screenHeight = 0f

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = Firebase.auth
        val db = Firebase.firestore

        // Se un utente all'avvio è già registrato otteniamo informazioni su di esso
        if (auth.currentUser != null)
            db.collection("Utente").document(auth.currentUser!!.email.toString()).get().addOnSuccessListener { doc ->
                currentUser = UserDataClass(
                    doc["Nome"].toString(),
                    doc["Cognome"].toString(),
                    doc["Telefono"].toString(),
                    doc["E-mail"].toString(),
                    doc["Username"].toString(),
                    doc["SecretKey"].toString(),
                    doc["ApiKey"].toString()
                )
            }

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)
        val container = findViewById<FrameLayout>(R.id.container)

        val rocket = findViewById<ImageView>(R.id.rocket)
        val doge = findViewById<ImageView>(R.id.doge)

        val marketFragment = MarketFragment() // Fragment del mercato (Principale)
        val favoriteFragment = FavoritesFragment() // Fragment preferiti
        val loginFragment = LoginFragment() // Fragment di Login
        val walletFragment = WalletFragment() // Fragment del wallet
        val notLoggedFragment = NotLoggedFragment() // Fragment per utente non loggato per il wallet
        val newsFragment = NewsFragment() // Fragment per news
        val loggedFragment = LoggedFragment() // Fragment per utente loggato

        // Animazione all'avvio del razzo cno Doge con accellerazione che dura 2.6 sec. ed inizia dopo 0.4 sec.
        Handler(Looper.getMainLooper()).postDelayed({
            val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                rocket.translationY = value
                doge.translationY = value
            }

            valueAnimator.interpolator = AccelerateInterpolator(1.5f)
            valueAnimator.duration = 2600L

            valueAnimator.start()
        },400)

        // Una volta terminato il razzo viene mostrata l'interfaccia principale (Market)
        Handler(Looper.getMainLooper()).postDelayed({
            container.visibility = View.GONE
            navigation.visibility = View.VISIBLE
            fragmentContainer.visibility = View.VISIBLE
            navigation.selectedItemId = R.id.home
            setCurrentFragment(marketFragment)
        },3000)

        // Listener che imposta il fragment in base al click del BottomNavigationView
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