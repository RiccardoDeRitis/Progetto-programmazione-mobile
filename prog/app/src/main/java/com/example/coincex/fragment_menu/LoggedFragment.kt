package com.example.coincex.fragment_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coincex.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoggedFragment: Fragment() {

    lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.logged_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val db = Firebase.firestore

        auth = Firebase.auth
        val user = auth.currentUser

        val modifica = view.findViewById<Button>(R.id.button)

        val nome = view.findViewById<TextView>(R.id.textView61)
        val cognome = view.findViewById<TextView>(R.id.textView63)
        val telefono = view.findViewById<TextView>(R.id.textView69)
        val email = view.findViewById<TextView>(R.id.textView65)
        val username = view.findViewById<TextView>(R.id.textView67)

        val nomeEdit = view.findViewById<EditText>(R.id.name_edit)
        val cognomeEdit = view.findViewById<EditText>(R.id.cognome_edit)
        val telefonoEdit = view.findViewById<EditText>(R.id.telefono_edit)
        val emailEdit = view.findViewById<EditText>(R.id.mail_edit)
        val userEdit = view.findViewById<EditText>(R.id.username_edit)

        db.collection("Utente").document(user?.email.toString()).get().addOnSuccessListener {
            nome.text = it["Nome"].toString()
            cognome.text = it["Cognome"].toString()
            telefono.text = it["Telefono"].toString()
            email.text = it["E-mail"].toString()
            username.text = it["Username"].toString()
        }



        modifica.setOnClickListener {
            nome.visibility = View.GONE
            cognome.visibility = View.GONE
            telefono.visibility = View.GONE
            email.visibility = View.GONE
            username.visibility = View.GONE

            nomeEdit.visibility = View.VISIBLE
            cognomeEdit.visibility = View.VISIBLE
            telefonoEdit.visibility = View.VISIBLE
            emailEdit.visibility = View.VISIBLE
            userEdit.visibility = View.VISIBLE

            nomeEdit.text = nome.editableText
            cognomeEdit.text = cognome.editableText
            telefonoEdit.text = telefono.editableText
            emailEdit.text = email.editableText
            userEdit.text = username.editableText
        }

    }

}