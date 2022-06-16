package com.example.coincex.fragment_menu

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.coincex.MainActivity
import com.example.coincex.R
import com.example.coincex.activity.SignInActivity
import com.example.coincex.data_class.UserDataClass
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val auth = Firebase.auth
        val db = Firebase.firestore

        val dialog = Dialog(view.context)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT

        val email = view.findViewById<EditText>(R.id.email_edit)
        val password = view.findViewById<EditText>(R.id.pass_edit)
        val accedi = view.findViewById<Button>(R.id.button3)
        val ricordami = view.findViewById<CheckBox>(R.id.checkBox)

        val resetPassword = view.findViewById<Button>(R.id.button4)

        if (view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "null") != "null") {
            ricordami.isChecked = true
            email.setText(view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "null"))
            password.setText(view.context.getSharedPreferences("User", Context.MODE_PRIVATE).getString("password", "null"))
        }

        accedi.setOnClickListener {
            dialog.show()
            dialog.window?.attributes = lp
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

            if (email.text.toString() != "" && password.text.toString() != "")
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        db.collection("Utente").document(email.text.toString()).get().addOnSuccessListener { doc ->
                            MainActivity.currentUser = UserDataClass(
                                doc["Nome"].toString(),
                                doc["Cognome"].toString(),
                                doc["Telefono"].toString(),
                                doc["E-mail"].toString(),
                                doc["Username"].toString(),
                                doc["SecretKey"].toString(),
                                doc["ApiKey"].toString()
                            )
                            activity?.supportFragmentManager?.beginTransaction()?.apply {
                                replace(R.id.fragment_container, LoggedFragment())
                                commit()
                            }
                            dialog.dismiss()
                            Toast.makeText(context, "Benvenuto ${doc["E-mail"]}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        dialog.dismiss()
                        Toast.makeText(context, "Email o Password errati, riprova", Toast.LENGTH_SHORT).show()
                    }
                }
            else {
                dialog.dismiss()
                Toast.makeText(view.context, "Inserisci i campi richiesti!", Toast.LENGTH_SHORT).show()
            }

        }

        resetPassword.setOnClickListener {
            val dialogReset = Dialog(view.context)
            dialogReset.setContentView(R.layout.dialog_reset_password)

            val reset = dialogReset.findViewById<Button>(R.id.button12)
            val emailReset = dialogReset.findViewById<EditText>(R.id.editTextTextPersonName)

            reset.setOnClickListener {
                if (emailReset.text.toString() != "")
                    auth.sendPasswordResetEmail(emailReset.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(view.context, "Email sent.", Toast.LENGTH_SHORT).show()
                            dialogReset.dismiss()
                        }
                        else
                            Toast.makeText(view.context, "Email non esistente, registrati", Toast.LENGTH_SHORT).show()
                    }
                else
                    Toast.makeText(view.context, "Inserisci una email!", Toast.LENGTH_SHORT).show()
            }
            dialogReset.show()
            dialogReset.window?.attributes = lp
        }

        val signIn = view.findViewById<Button>(R.id.button5)

        signIn.setOnClickListener {
            val intent = Intent(view.context, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}