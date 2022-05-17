package com.example.coincex.fragment_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coincex.R
import com.example.coincex.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.mindrot.jbcrypt.BCrypt

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

        val db = Firebase.firestore

        accedi.setOnClickListener {
            db.collection("Utente").get().addOnSuccessListener {
                check(view.context,it,email.text.toString(),password.text.toString())
            }.addOnFailureListener {
                Toast.makeText(view.context, "Errore di comunicazione con il database, riprovare più tardi", Toast.LENGTH_SHORT).show()
            }
        }

        val signIn = view.findViewById<Button>(R.id.button5)

        signIn.setOnClickListener {
            val intent = Intent(view.context, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun check(context: Context, query: QuerySnapshot, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful)
                Toast.makeText(context, "Benvenuto ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Errore in fase di Login", Toast.LENGTH_SHORT).show()
        }
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, LoggedFragment())
            commit()
        }
    }
}