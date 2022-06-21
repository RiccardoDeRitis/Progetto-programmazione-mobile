package com.example.coincex.fragment_menu

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coincex.*
import com.example.coincex.activity.BuyActivity
import com.example.coincex.api_data.WalletData
import com.example.coincex.data_class.AssetAllocationDataClass
import com.example.coincex.data_class.WalletCoinDataClass
import com.example.coincex.list_adapter.AssetAllocationAdapter
import com.example.coincex.list_adapter.WalletAdapter
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.lang.Exception
import java.text.DecimalFormat

class WalletFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val coinWallet = view.findViewById<RecyclerView>(R.id.coinWallet)
        val assetWallet = view.findViewById<RecyclerView>(R.id.coinWallet2)
        val layout = view.findViewById<ConstraintLayout>(R.id.layoutWallet)

        val convert = view.findViewById<Button>(R.id.button9)

        val currentUser = MainActivity.currentUser

        val color1 = Color.parseColor("#007fff")
        val color2 = Color.parseColor("#ffa500")
        val color3 = Color.parseColor("#00bd2d")
        val color4 = Color.parseColor("#e5be01")
        val color5 = Color.parseColor("#FF01BAA7")

        val listColor = ArrayList<Int>()
        listColor.add(color1)
        listColor.add(color2)
        listColor.add(color3)
        listColor.add(color4)
        listColor.add(color5)

        val dialog = Dialog(view.context)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val pieChart = view.findViewById<PieChart>(R.id.piechart)
        val walletCoinData = ArrayList<WalletCoinDataClass>()
        val tot = view.findViewById<TextView>(R.id.textView91)

        dialog.show()

        // Ottenere i dati sul proprio wallet tramite API di Binance
        WalletData.getDataFromApi(view.context, currentUser.apikey, currentUser.secretKey) { result ->
            try {
                val listAsset = WalletData.getData(result) // Infromazioni sulla quantità delle coin possedute
                WalletData.getPriceAsset(view.context, listAsset) { res ->
                    val priceList = WalletData.getDataPrice(res) // Infromazioni sui prezzi delle coin possedute in tempo reale
                    var totBalance = 0.0 // Variabile utilizzata per calcolare le percentuali di ogni asset

                    val assetData = ArrayList<AssetAllocationDataClass>()

                    val recipeList = MarketFragment.recipe

                    /* Le coin all'interno dei 2 array sono in posizione random e non uguali
                       quindi occorre selezionare ogni coin e ricreare l'array contenente sia la quantità e il prezzo
                     */
                    for (asset in listAsset) {
                        val element = priceList.stream().filter {
                            it.name == asset.name
                        }.findFirst().orElse(null)
                        val i = priceList.indexOf(element)
                        val element2 = recipeList.stream().filter {
                            it.symbol == asset.name
                        }.findFirst().orElse(null)
                        val k = recipeList.indexOf(element2)
                        // Se la coin che abbiamo su binance non è presente nelle prime 100 non verranno mostrate logo e nome completo
                        if (k == -1)
                            walletCoinData.add(
                                WalletCoinDataClass(
                                    "n/a",
                                    priceList[i].name,
                                    "N/A",
                                    asset.value,
                                    priceList[i].value
                                )
                            )
                        else {
                            /* Poichè i prezzi sono presi dalle coppie COIN/USDT se abbiamo USDT nel nostro
                               portafoglio non bisogna prendere il prezzo nell'altro array (priceList) ma sarà la
                               quantità di USDT che abbiamo.
                             */
                            if (asset.name == "USDT")
                                walletCoinData.add(
                                    WalletCoinDataClass(
                                        recipeList[k].imageLogo,
                                        recipeList[k].symbol,
                                        recipeList[k].name,
                                        asset.value,
                                        1.0
                                    )
                                )
                            else
                                /* Caso in cui non la coin presa in considerazione all'interno del for non sia USDT e sia nelle prime 100
                                   allora verranno mostrate tutti i dettagli
                                 */
                                walletCoinData.add(
                                    WalletCoinDataClass(
                                        recipeList[k].imageLogo,
                                        priceList[i].name,
                                        recipeList[k].name,
                                        asset.value,
                                        priceList[i].value
                                    )
                                )
                        }
                        // totBalance sarà tutto l'ammontare del nostro wallet facendo prezzo*quantità
                        totBalance += if (asset.name == "USDT")
                            asset.value
                        else
                            asset.value * priceList[i].value
                    }

                    // Popoliamo l'array che aiuterà a mostare il grafico a torta con colore, nome e percentuale
                    for ((i, coin) in walletCoinData.withIndex())
                        assetData.add(AssetAllocationDataClass(listColor[i], coin.name, (((coin.price*coin.quantity)/totBalance)*100).toFloat()))

                    // Grafico a torta che mostra come è composto il nostro portafoglio
                    pieChart.apply {
                        var i = 0
                        for (coin in walletCoinData)
                            addPieSlice(PieModel(((coin.price*coin.quantity)/totBalance).toFloat(), listColor[i++]))
                    }

                    tot.text = DecimalFormat("#.##").format(totBalance)+" $"

                    pieChart.startAnimation()

                    // Array di fianco il grafico a torta
                    assetWallet.layoutManager = LinearLayoutManager(view.context)
                    val adapter2 = AssetAllocationAdapter(assetData)
                    assetWallet.adapter = adapter2

                    // Array che mostra invece quantità e prezzo
                    coinWallet.layoutManager = LinearLayoutManager(view.context)
                    val adapter = WalletAdapter(walletCoinData)
                    coinWallet.adapter = adapter
                    convert.visibility = View.VISIBLE
                    dialog.dismiss()
                    layout.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Toast.makeText(view.context, "Errore nel caricamento dei dati, riprovare", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        // Click per la conversione delle coin che possediamo
        convert.setOnClickListener {
            val intent = Intent(view.context, BuyActivity::class.java)
            intent.putExtra("asset", walletCoinData)
            view.context.startActivity(intent)
        }

    }
}