package com.example.coincex.data_class

import java.io.Serializable

data class WalletCoinDataClass(
    val logo: String,
    val symbol: String,
    val name: String,
    val quantity: Double,
    val price: Double
): Serializable