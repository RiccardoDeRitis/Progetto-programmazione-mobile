package com.example.explicitintentexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChooseQuantity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_quantity)

        val seekBar = findViewById<SeekBar>(R.id.quantity)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                findViewById<TextView>(R.id.textView8).text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //
            }
        })

        val bundle = intent.extras
        findViewById<TextView>(R.id.prodotto).text = bundle?.getString("prodotto")
        findViewById<TextView>(R.id.brand).text = bundle?.getString("brand")
        findViewById<TextView>(R.id.prezzo).text = bundle?.getDouble("prezzo").toString()


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val quantity = findViewById<SeekBar>(R.id.quantity).progress
            val intent = Intent()
            intent.putExtra("quantity",quantity)
            intent.putExtra("prodotto",bundle?.getString("prodotto"))
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }


}