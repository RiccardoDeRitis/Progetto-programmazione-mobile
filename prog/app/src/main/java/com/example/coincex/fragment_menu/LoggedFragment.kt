package com.example.coincex.fragment_menu

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coincex.MainActivity
import com.example.coincex.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoggedFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.logged_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val auth: FirebaseAuth = Firebase.auth
        val db = Firebase.firestore

        val logout = view.findViewById<Button>(R.id.button11)

        val resetPassword = view.findViewById<Button>(R.id.button4)
        val resetApi = view.findViewById<Button>(R.id.button8)

        val nome = view.findViewById<TextView>(R.id.textView61)
        val cognome = view.findViewById<TextView>(R.id.textView63)
        val telefono = view.findViewById<TextView>(R.id.textView69)
        val email = view.findViewById<TextView>(R.id.textView65)
        val username = view.findViewById<TextView>(R.id.textView67)

        val thisUser = MainActivity.currentUser

        nome.text = thisUser.nome
        cognome.text = thisUser.cognome
        telefono.text = thisUser.telefono
        email.text = thisUser.email
        username.text = thisUser.username

        logout.setOnClickListener {
            auth.signOut()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, LoginFragment())
                commit()
            }
            Toast.makeText(view.context, "Arrivederci ${email.text}", Toast.LENGTH_SHORT).show()
        }

        resetPassword.setOnClickListener {
            auth.sendPasswordResetEmail(thisUser.email).addOnCompleteListener {
                if (it.isSuccessful)
                    Toast.makeText(view.context, "Email sent.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(view.context, "Errore", Toast.LENGTH_SHORT).show()
            }
        }

        resetApi.setOnClickListener {
            val dialog = Dialog(view.context)
            dialog.setContentView(R.layout.dialog_reset_api)

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT

            val reset = dialog.findViewById<Button>(R.id.buttonResetApi)
            val apikey = dialog.findViewById<EditText>(R.id.editApi)
            val secret = dialog.findViewById<EditText>(R.id.editSecret)

            reset.setOnClickListener {
                if (apikey.text.toString() != "" && secret.text.toString() != "") {
                    db.collection("Utente").document(thisUser.email)
                        .update(mapOf(
                            "ApiLey" to apikey.text.toString(),
                            "SecretKey" to secret.text.toString()
                        ))
                        .addOnSuccessListener {
                            Toast.makeText(view.context,"Reset apikey successfully!",Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }.addOnFailureListener {
                            Toast.makeText(view.context,"Error!",Toast.LENGTH_SHORT).show()
                    }
                }
                else
                    Toast.makeText(view.context,"Inserisci i dati!",Toast.LENGTH_SHORT).show()
            }
            dialog.show()
            dialog.window?.attributes = lp
        }

    }

}