package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// Data class per info dettagliate di una coin
data class ProfileCoinData(
    val title: String,
    val details: String,
    val category: String,
    val sector: String,
    val whitepaper: String,
    val website: String,
    val background_details: String,
    val regulation: String,
    val token_name: String,
    val token_type: String,
    val name_explorer: String,
    val blockchain_explorer: String,
    val token_usage: String,
    val launch_details: String,
    val supply_details: String,
    val emission_type: String,
    val consensus_name: String,
    val consensus_details: String,
    val block_reward: String,
    val algorithm: String,
    val technology_details: String,
    val governance_details: String,
    val is_decentralized: String) {

    companion object {

        fun getProfileFromApi(context: Context, symbol: String, callback:(result: String) -> Unit) {
            val url = "https://data.messari.io/api/v2/assets/${symbol}/profile"
            val queue = Volley.newRequestQueue(context)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
                    callback(it)
                }
            ) {
                callback(it.toString())
            }
            queue.add(stringRequest)
        }

        fun getDataProfile(data: String): ProfileCoinData {
            val jsonData = JSONObject(data).getJSONObject("data").getJSONObject("profile")
            val title = jsonData.getJSONObject("general").getJSONObject("overview").getString("tagline")
            val category = jsonData.getJSONObject("general").getJSONObject("overview").getString("category")
            val sector = jsonData.getJSONObject("general").getJSONObject("overview").getString("sector")
            val details = jsonData.getJSONObject("general").getJSONObject("overview").getString("project_details")
            val website = jsonData.getJSONObject("general").getJSONObject("overview").getJSONArray("official_links").getJSONObject(0).getString("link")
            val whitepaper = jsonData.getJSONObject("general").getJSONObject("overview").getJSONArray("official_links").getJSONObject(1).getString("link")
            val backgroundDetails = jsonData.getJSONObject("general").getJSONObject("background").getString("background_details")
            val regulation = jsonData.getJSONObject("general").getJSONObject("regulation").getString("regulatory_details")
            val tokenName = jsonData.getJSONObject("economics").getJSONObject("token").getString("token_name")
            val tokenType = jsonData.getJSONObject("economics").getJSONObject("token").getString("token_type")
            val nameExplorer = jsonData.getJSONObject("economics").getJSONObject("token").getJSONArray("block_explorers").getJSONObject(0).getString("name")
            val blockchainExplorer = jsonData.getJSONObject("economics").getJSONObject("token").getJSONArray("block_explorers").getJSONObject(0).getString("link")
            val tokenUsage = jsonData.getJSONObject("economics").getJSONObject("token").getString("token_usage_details")
            val launchDetails = jsonData.getJSONObject("economics").getJSONObject("launch").getJSONObject("general").getString("launch_details")
            val supplyDetails = jsonData.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("supply").getString("supply_curve_details")
            val emissionType =  jsonData.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("supply").getString("general_emission_type")
            val consensusName = jsonData.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("general_consensus_mechanism")
            val consensusDetails = jsonData.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("consensus_details")
            val blockReward = jsonData.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("block_reward").toString()
            val algorithm = jsonData.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("mining_algorithm")
            val technologyDetails = jsonData.getJSONObject("technology").getJSONObject("overview").getString("technology_details")
            val governanceDetails = jsonData.getJSONObject("governance").getString("governance_details")
            val decentralized = jsonData.getJSONObject("governance").getJSONObject("onchain_governance").getString("is_treasury_decentralized").toString()
            return ProfileCoinData(title,details,category,sector,whitepaper,website,backgroundDetails,regulation,tokenName,tokenType,nameExplorer,blockchainExplorer,tokenUsage,launchDetails,supplyDetails,emissionType,consensusName,consensusDetails,blockReward,algorithm,technologyDetails,governanceDetails,decentralized)
        }

    }
}