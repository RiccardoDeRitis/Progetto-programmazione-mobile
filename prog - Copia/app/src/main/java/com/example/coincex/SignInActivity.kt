package com.example.coincex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)

        val nome = findViewById<EditText>(R.id.nome)
        val cognome = findViewById<EditText>(R.id.cognome)
        val username = findViewById<EditText>(R.id.username)
        val telefono = findViewById<EditText>(R.id.telefono)
        val password = findViewById<EditText>(R.id.password)
        val email = findViewById<EditText>(R.id.email)
        val apikey = findViewById<EditText>(R.id.apikey)
        val secretkey = findViewById<EditText>(R.id.secretkey)

        val createApi = findViewById<Button>(R.id.button6)

        createApi.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.binance.com/en/support/faq/360002502072")
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}