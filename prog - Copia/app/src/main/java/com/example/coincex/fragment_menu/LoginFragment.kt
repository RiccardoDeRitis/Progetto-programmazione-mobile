package com.example.coincex.fragment_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.coincex.R
import com.example.coincex.SignInActivity

class LoginFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val signIn = view.findViewById<Button>(R.id.button5)

        signIn.setOnClickListener {
            val intent = Intent(view.context, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}