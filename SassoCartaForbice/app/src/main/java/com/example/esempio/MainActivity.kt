package com.example.esempio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var i=0
    var j=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val score1 = findViewById<TextView>(R.id.Punteggio1)
        val score2 = findViewById<TextView>(R.id.Punteggio2)

        val giocata = findViewById<TextView>(R.id.Giocata)
        val avversario = findViewById<TextView>(R.id.Avversario)

        val carta = findViewById<ImageButton>(R.id.CartaButton)
        val forbice = findViewById<ImageButton>(R.id.ForbiceButton)
        val sasso = findViewById<ImageButton>(R.id.SassoButton)

        carta.setOnClickListener {
            giocata.text = "Carta"
            randomPlay(avversario)
            score(giocata, avversario, score1, score2)
        }

        forbice.setOnClickListener {
            giocata.text = "Forbice"
            randomPlay(avversario)
            score(giocata, avversario, score1, score2)
        }

        sasso.setOnClickListener {
            giocata.text = "Sasso"
            randomPlay(avversario)
            score(giocata, avversario, score1, score2)
        }

    }

    fun randomPlay(avversario: TextView) {
        when (Random.nextInt(1,4)) {
            1 -> avversario.text = "Carta"
            2 -> avversario.text = "Forbice"
            3 -> avversario.text = "Sasso"
        }
    }

    fun score(giocata: TextView, avversario: TextView, score1: TextView, score2: TextView){
        if(giocata.text == "Carta" && avversario.text == "Forbice") j++
        if(giocata.text == "Carta" && avversario.text == "Sasso") i++

        if(giocata.text == "Forbice" && avversario.text == "Carta") i++
        if(giocata.text == "Forbice" && avversario.text == "Sasso") j++

        if(giocata.text == "Sasso" && avversario.text == "Carta") j++
        if(giocata.text == "Sasso" && avversario.text == "Forbice") i++

        score1.text = i.toString()
        score2.text = j.toString()
    }

}
