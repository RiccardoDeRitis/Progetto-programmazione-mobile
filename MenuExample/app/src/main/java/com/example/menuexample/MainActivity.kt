package com.example.menuexample

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        /*
        val array: MutableList<String> = mutableListOf()
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.binance.com/api/v3/exchangeInfo"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val jsonData = JSONObject(it)
                val jsonArray = jsonData.getJSONArray("symbols")
                for (i in 0 until 4) {
                    val symbol = jsonArray.getJSONObject(i).getString("symbol")
                    array.add(symbol)
                }
            }
        ) { array.add("ok") }
        queue.add(stringRequest)
        return array
        */
        val listView = findViewById<ListView>(R.id.listView)

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.binance.com/api/v3/exchangeInfo"
        val listCoin : MutableList<String> = mutableListOf()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val symbol : String = JSONObject(response).getJSONArray("symbols").getJSONObject(0).getString("symbol")
                Log.d("Ok : $symbol questo", savedInstanceState.toString())
                listCoin.add(symbol)
                val arrayAdapter2: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,listCoin)
                listView.adapter = arrayAdapter2
            },
            {
                listCoin.add("ok")
            })
        queue.add(stringRequest)

        // val listCoin: MutableList<String> = array

        // Mostra a video la lista con gli elementi dell'array
        // val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contact)


        //Registra un context menu per la lista
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_main, menu)
    }

     override fun onContextItemSelected(item: MenuItem): Boolean {
         val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
         return when (item.itemId) {
             R.id.call -> {
                 Toast.makeText(applicationContext, "calling  (id:${info.id})", Toast.LENGTH_LONG).show()
                 val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:333333333"))
                 startActivity(intent)
                 true
             }
             R.id.sms -> {
                 Toast.makeText(applicationContext, "sms code", Toast.LENGTH_LONG).show()
                 true
             }
             else -> super.onOptionsItemSelected(item)
         }
     }

    //ALERT DIALOG
    fun showAlertDialog(v: View) {
        //Nota: l'alertDialog si può anche creare una sola volta nel onCreate dell'Activity
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Info")
        alertDialog.setMessage("Conferma per procedere alla SecondActivity.")
        alertDialog.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                intent = Intent(applicationContext, SecondActivity::class.java)
                startActivity(intent)
            }
        })
        alertDialog.show()
    }

    //POPUP
    fun showPopup(v: View) {
        // Nota: il popupMenu si può anche creare una sola volta nel onCreate dell'Activity
        val popupMenu: PopupMenu = PopupMenu(this, v)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        //Quando clicco sull'opzione del popup
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_crick ->
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                            .show()
                    R.id.action_ftbal ->
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                            .show()
                    R.id.action_hockey ->
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                            .show()
                }
                true
            })
            popupMenu.show()
        }

    fun showThird(v: View){
        val intent = Intent(this,ThirdActivity::class.java)
        startActivity(intent)
    }

}