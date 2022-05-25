package com.example.coincex.fragment_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coincex.MainActivity
import com.example.coincex.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoggedFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.logged_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val auth: FirebaseAuth = Firebase.auth

        val modifica = view.findViewById<Button>(R.id.button)
        val logout = view.findViewById<Button>(R.id.button11)

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

        val thisUser = MainActivity.currentUser

        nome.text = thisUser.nome
        cognome.text = thisUser.cognome
        telefono.text = thisUser.telefono
        email.text = thisUser.email
        username.text = thisUser.username


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

        logout.setOnClickListener {
            auth.signOut()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, LoginFragment())
                commit()
            }
            Toast.makeText(view.context, "Arrivederci ${email.text}", Toast.LENGTH_SHORT).show()
        }

    }

}