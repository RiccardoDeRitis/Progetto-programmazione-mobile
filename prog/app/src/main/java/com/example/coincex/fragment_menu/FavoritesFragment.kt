package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coincex.api_data.ListCoinData
import com.example.coincex.R

class FavoritesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val title = view.findViewById<TextView>(R.id.titleFavorite)

        val listCoinFavorite = view.findViewById<ListView>(R.id.listCoinFavorite)
        val favoriteCoin = ArrayList<ListCoinData>()

        val positionArray = ArrayList<Int>()
        val recipeList = MarketFragment.recipeList
        for (i in 0 until recipeList.size) {
            if (i == MarketFragment.getPreferences(i.toString(), view.context)) {
                favoriteCoin.add(recipeList[i])
                positionArray.add(i)
            }
        }

        if (favoriteCoin.isEmpty()) {
            title.text = "Non stai seguendo alcun asset, torna indietro e tieni traccia dei tuoi asset preferiti"
            title.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        else {
            title.text = "I tuoi asset preferiti :"
            val adapter = CoinAdapter(view.context, favoriteCoin)
            listCoinFavorite.adapter = adapter
        }

        val posistionItemSelected = ArrayList<Int>()

        listCoinFavorite.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        listCoinFavorite.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {

            override fun onItemCheckedStateChanged(mode: ActionMode, position: Int, id: Long, checked: Boolean) {
                mode.title="${listCoinFavorite.checkedItemCount} selezionati"
                if (checked)
                    posistionItemSelected.add(position)
                else
                    posistionItemSelected.remove(position)
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.menu_share -> {
                        deletePreference(positionArray, posistionItemSelected, view.context)
                        posistionItemSelected.clear()
                        setCurrentFragment(FavoritesFragment())
                        Toast.makeText(view.context, "Eliminato dai preferiti", Toast.LENGTH_SHORT).show()
                        mode.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val menuInflater: MenuInflater = mode.menuInflater
                menuInflater.inflate(R.menu.pressed_menu, menu)
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                posistionItemSelected.clear()
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }
        })

    }

    private fun setCurrentFragment(fragment: Fragment) = parentFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container,fragment)
        commit()
    }

    fun deletePreference(positionArray: ArrayList<Int>, positionItemSelected: ArrayList<Int>, context: Context) {
        val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val itemRemove = ArrayList<Int>()
        for (i in positionItemSelected)
            itemRemove.add(positionArray[i])

        for (i in itemRemove)
            editor.remove(i.toString())
        editor.apply()
    }
}