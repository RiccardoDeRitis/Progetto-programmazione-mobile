package com.example.coincex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SignInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}