package com.example.coincex.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coincex.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)

        val auth: FirebaseAuth = Firebase.auth

        val nome = findViewById<EditText>(R.id.nome)
        val cognome = findViewById<EditText>(R.id.cognome)
        val username = findViewById<EditText>(R.id.username)
        val telefono = findViewById<EditText>(R.id.telefono)
        val password = findViewById<EditText>(R.id.password)
        val email = findViewById<EditText>(R.id.email)
        val apikey = findViewById<EditText>(R.id.apikey)
        val secretkey = findViewById<EditText>(R.id.secretkey)
        val registrati = findViewById<Button>(R.id.registrati)

        val createApi = findViewById<Button>(R.id.button6)

        // Tutorial su come creare correttamente le API di Binance
        createApi.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.binance.com/en/support/faq/360002502072")
            startActivity(intent)
        }

        val db = Firebase.firestore
        val dialog = Dialog(this@SignInActivity)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Click che registra un nuovo utente se ha inserito correttamente tutti i campi
        registrati.setOnClickListener {
            dialog.show()
            if (nome.text.toString() != "" && cognome.text.toString() != "" &&
                username.text.toString() != "" && telefono.text.toString() != "" &&
                password.text.toString() != "" && email.text.toString() != "" &&
                apikey.text.toString() != "" && secretkey.text.toString() != "") {
                    if (password.text.toString().length >= 6) {
                        val user = hashMapOf(
                            "Nome" to nome.text.toString(),
                            "Cognome" to cognome.text.toString(),
                            "E-mail" to email.text.toString(),
                            "Telefono" to telefono.text.toString(),
                            "Username" to username.text.toString(),
                            "Password" to password.text.toString(),
                            "ApiKey" to apikey.text.toString(),
                            "SecretKey" to secretkey.text.toString()
                        )

                        db.collection("Utente").document(email.text.toString()).set(user).addOnSuccessListener {
                            auth.createUserWithEmailAndPassword(user["E-mail"].toString(), user["Password"].toString()).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    dialog.dismiss()
                                    Toast.makeText(applicationContext, "Ti sei registrato correttamente", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                else {
                                    dialog.dismiss()
                                    Toast.makeText(applicationContext, "Campi non validi", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }.addOnFailureListener {
                            dialog.dismiss()
                            Toast.makeText(applicationContext, "Errore di comunicazione con il database, riprova", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        dialog.dismiss()
                        Toast.makeText(applicationContext, "La password deve contenere almeno 6 lettere", Toast.LENGTH_SHORT).show()
                    }
            }
            else {
                dialog.dismiss()
                Toast.makeText(applicationContext, "Inserisci tutti i dati!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}