package com.example.coincex.fragment_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.coincex.R
import com.example.coincex.activity.SignInActivity

class NotLoggedFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.not_logged_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val signin = view.findViewById<Button>(R.id.button2)

        signin.setOnClickListener {
            val intent = Intent(view.context, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}