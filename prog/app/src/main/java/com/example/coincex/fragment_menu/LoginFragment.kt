package com.example.coincex.fragment_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.coincex.R
import com.example.coincex.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = Firebase.auth

        val email = view.findViewById<EditText>(R.id.email_edit)
        val password = view.findViewById<EditText>(R.id.pass_edit)
        val accedi = view.findViewById<Button>(R.id.button3)
        val ricordami = view.findViewById<CheckBox>(R.id.checkBox)

        if (view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "null") != "null") {
            ricordami.isChecked = true
            email.setText(view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "null"))
            password.setText(view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("password", "null"))
        }

        accedi.setOnClickListener {

            val progress = view.findViewById<ProgressBar>(R.id.progressBar)
            progress.visibility = View.VISIBLE

            if (view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "null") == "null") {
                if (ricordami.isChecked) {
                    val sharedPref = view.context.getSharedPreferences("User", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("email", email.text.toString())
                    editor.putString("password", password.text.toString())
                    editor.apply()
                }
            }
            else {
                if (!ricordami.isChecked) {
                    val sharedPref = view.context.getSharedPreferences("User", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.remove("email")
                    editor.remove("password")
                    editor.apply()
                }
            }

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    progress.visibility = View.GONE
                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragment_container, LoggedFragment())
                        commit()
                    }
                    Toast.makeText(context, "Benvenuto ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
                }
                else {
                    progress.visibility = View.GONE
                    Toast.makeText(context, "Email o Password errati, riprova", Toast.LENGTH_SHORT).show()
                }
            }

        }

        val signIn = view.findViewById<Button>(R.id.button5)

        signIn.setOnClickListener {
            val intent = Intent(view.context, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}