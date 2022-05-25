package com.example.coincex.activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.R
import com.example.coincex.api_data.ConvertCoinData
import com.example.coincex.data_class.WalletCoinDataClass
import com.example.coincex.list_adapter.ConvertAdapter
import com.squareup.picasso.Picasso

class BuyActivity: AppCompatActivity() {

    private lateinit var logoBuy: ImageView
    private lateinit var nameBuy: TextView

    private lateinit var logoCurrentBuy: ImageView
    private lateinit var nameCurrentBuy: TextView

    private lateinit var logoConvert: ImageView
    private lateinit var nameConvert: TextView

    private lateinit var logoCurrentConvert: ImageView
    private lateinit var nameCurrentConvert: TextView

    private lateinit var disponibile: TextView

    private lateinit var dialog: Dialog
    private lateinit var dialogConvert: Dialog

    private val picasso = Picasso.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_layout)

        logoBuy = findViewById(R.id.logo_image5)
        nameBuy = findViewById(R.id.textView78)
        logoConvert = findViewById(R.id.logo_image7)
        nameConvert = findViewById(R.id.textView82)

        dialog = Dialog(this@BuyActivity)
        dialog.setContentView(R.layout.dialog_search_coin_buy)

        dialogConvert = Dialog(this@BuyActivity)
        dialogConvert.setContentView(R.layout.dialog_search_coin_convert)

        val walletData = intent.getSerializableExtra("asset") as ArrayList<WalletCoinDataClass>
        val layoutBuy = findViewById<RelativeLayout>(R.id.relativeLayout)
        val layoutConvert = findViewById<RelativeLayout>(R.id.relativeLayout2)
        val maxBalance = findViewById<RelativeLayout>(R.id.relativeLayout3)

        val editQuantity = findViewById<EditText>(R.id.editQuantity)

        logoCurrentBuy = dialog.findViewById(R.id.logo_image6)
        nameCurrentBuy = dialog.findViewById(R.id.textView85)

        logoCurrentConvert = dialogConvert.findViewById(R.id.logo_image9)
        nameCurrentConvert = dialogConvert.findViewById(R.id.textView93)

        picasso.load(walletData[0].logo).into(logoCurrentBuy)
        nameCurrentBuy.text = walletData[0].symbol

        picasso.load(walletData[1].logo).into(logoCurrentConvert)
        nameCurrentConvert.text = walletData[1].symbol

        disponibile = findViewById(R.id.textView80)
        disponibile.text = walletData[0].quantity.toString()

        picasso.load(walletData[0].logo).into(logoBuy)
        picasso.load(walletData[1].logo).into(logoConvert)

        nameBuy.text = walletData[0].symbol
        nameConvert.text = walletData[1].symbol

        layoutBuy.setOnClickListener {
            val editSearch = dialog.findViewById<SearchView>(R.id.searchBuy)
            editSearch.isIconified = false

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT

            val listCoinConvert = dialog.findViewById<RecyclerView>(R.id.listCoinConvert)
            listCoinConvert.layoutManager = LinearLayoutManager(applicationContext)

            var adapter = ConvertAdapter(walletData) { coin -> onClickItem(coin) }
            listCoinConvert.adapter = adapter
            dialog.show()

            dialog.window?.attributes = lp

            editSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    val filterData = walletData.filter {
                        it.symbol.contains(p0.toString().uppercase())
                    }
                    adapter = ConvertAdapter(filterData as ArrayList<WalletCoinDataClass>) { coin -> onClickItem(coin)}
                    listCoinConvert.adapter = adapter
                    return false
                }

            })
        }

        layoutConvert.setOnClickListener {
            val editSearch = dialogConvert.findViewById<SearchView>(R.id.searchConvert)
            editSearch.isIconified = false

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialogConvert.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT

            val listCoinConvert2 = dialogConvert.findViewById<RecyclerView>(R.id.listCoinConvert2)
            listCoinConvert2.layoutManager = LinearLayoutManager(applicationContext)

            ConvertCoinData.getDataFromApi(applicationContext) {
                val listConvertData = ConvertCoinData.getData(it, nameBuy.text.toString())
                var adapter = ConvertAdapter(listConvertData) { coin -> onClickItemConvert(coin) }

                listCoinConvert2.adapter = adapter

                dialogConvert.show()
                dialogConvert.window?.attributes = lp

                editSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        val filterData = listConvertData.filter { fil ->
                            fil.symbol.contains(p0.toString().uppercase())
                        }
                        adapter = ConvertAdapter(filterData as ArrayList<WalletCoinDataClass>) { coin -> onClickItemConvert(coin)}
                        listCoinConvert2.adapter = adapter
                        return false
                    }
                })
            }

        }

        maxBalance.setOnClickListener {
            editQuantity.setText(disponibile.text.toString())
        }

    }

    private fun onClickItem(coin: WalletCoinDataClass) {
        nameBuy.text = coin.symbol
        picasso.load(coin.logo).into(logoBuy)
        disponibile.text = coin.quantity.toString()
        picasso.load(coin.logo).into(logoCurrentBuy)
        nameCurrentBuy.text = coin.symbol
        dialog.dismiss()
    }

    private fun onClickItemConvert(coin: WalletCoinDataClass) {
        nameConvert.text = coin.symbol
        picasso.load(coin.logo).into(logoConvert)
        picasso.load(coin.logo).into(logoCurrentConvert)
        nameCurrentConvert.text = coin.symbol
        dialogConvert.dismiss()
    }



}