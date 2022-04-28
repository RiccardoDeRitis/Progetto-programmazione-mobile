package com.example.explicitintentexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val CHOOSE_QUANTITY= 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button1).setOnClickListener{
            getQuantity("Gocciole","Pavesi",1.80)
        }
        findViewById<Button>(R.id.button2).setOnClickListener{
            getQuantity("Abbracci","Mulino Bianco",1.70)
        }
        findViewById<Button>(R.id.button3).setOnClickListener{
            getQuantity("Krumiri","Bistefani",2.00)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CHOOSE_QUANTITY && resultCode== Activity.RESULT_OK){
            val quantity = data?.getIntExtra("quantity",0).toString()
            val view = when(data?.getStringExtra("prodotto")) {
                "Gocciole" -> findViewById<TextView>(R.id.numGocciole)
                "Abbracci" -> findViewById<TextView>(R.id.numAbbracci)
                else -> findViewById<TextView>(R.id.numKrumiri)
            }
            view.text = quantity
        }
    }

    fun getQuantity(name: String, brand: String, price:Double){
        val intent = Intent(this,ChooseQuantity::class.java)
        intent.putExtra("prodotto",name)
        intent.putExtra("brand",brand)
        intent.putExtra("prezzo",price)
        startActivityForResult(intent, CHOOSE_QUANTITY)
    }

}