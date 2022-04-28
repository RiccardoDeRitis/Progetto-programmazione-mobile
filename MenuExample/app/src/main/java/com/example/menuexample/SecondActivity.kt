package com.example.menuexample

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat

var like: Boolean = false

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(findViewById(R.id.toolbar2))

    }
    //Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
    //Select one option in the toolbar
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.opzione1 -> {
            // User chose the "Settings" item, show the app settings UI...
            Toast.makeText(this,"Hai cliccato Opzione 1",Toast.LENGTH_SHORT).show()
            true
        }

        R.id.opzione2 -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            Toast.makeText(this,"Hai cliccato Opzione 2",Toast.LENGTH_SHORT).show()
            true
        }

        R.id.like -> {
            val icon = item.icon
            if(!like)
                DrawableCompat.setTint(icon, Color.RED)
            else
                DrawableCompat.setTint(icon, Color.BLACK)
            like = !like
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}