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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.mindrot.jbcrypt.BCrypt

class LoginFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val email = view.findViewById<EditText>(R.id.email_edit)
        val password = view.findViewById<EditText>(R.id.pass_edit)

        val accedi = view.findViewById<Button>(R.id.button3)

        var bool = false

        val db = Firebase.firestore

        accedi.setOnClickListener {
            db.collection("Utente").get().addOnSuccessListener {
                for (user in it)
                    if (user["E-mail"].toString() == email.text.toString() && BCrypt.checkpw(password.text.toString(), user["Password"].toString())) {
                        bool = true
                        check(view.context, bool)
                        break
                    }
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

    private fun check(context: Context, bool: Boolean) {
        if (bool)
            Toast.makeText(context, "Hai eseguito l'accesso", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Username o password errati, riprova", Toast.LENGTH_SHORT).show()
    }

}