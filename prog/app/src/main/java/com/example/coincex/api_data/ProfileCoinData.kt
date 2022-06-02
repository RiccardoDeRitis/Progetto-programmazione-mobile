package com.example.coincex.api_data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception

// Data class per info dettagliate di una coin
data class ProfileCoinData(
    val title: String,
    val details: String,
    val category: String,
    val sector: String,
    val whitepaper: String,
    val website: String,
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
    val governance_details: String) {

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
                callback("")
            }
            queue.add(stringRequest)
        }

        fun getDataProfile(data: String): ProfileCoinData {
            val jsonData = try{ JSONObject(data).getJSONObject("data").getJSONObject("profile") }
            catch (e: Exception) { null }
            val title = try{ jsonData!!.getJSONObject("general").getJSONObject("overview").getString("tagline") }
            catch (e: Exception) { "n/a" }
            val category = try{ jsonData!!.getJSONObject("general").getJSONObject("overview").getString("category") }
            catch (e: Exception) { "n/a" }
            val sector = try{ jsonData!!.getJSONObject("general").getJSONObject("overview").getString("sector") }
            catch (e: Exception) { "n/a" }
            val details = try{ jsonData!!.getJSONObject("general").getJSONObject("overview").getString("project_details") }
            catch (e: Exception) { "n/a" }
            val website = try{ jsonData!!.getJSONObject("general").getJSONObject("overview").getJSONArray("official_links").getJSONObject(0).getString("link") }
            catch (e: Exception) { "n/a" }
            val whitepaper = try{ jsonData!!.getJSONObject("general").getJSONObject("overview").getJSONArray("official_links").getJSONObject(1).getString("link") }
            catch (e: Exception) { "n/a" }
            val blockchainExplorer = try{ jsonData!!.getJSONObject("economics").getJSONObject("token").getJSONArray("block_explorers").getJSONObject(0).getString("link") }
            catch (e: Exception) { "n/a" }
            val tokenUsage = try{jsonData!!.getJSONObject("economics").getJSONObject("token").getString("token_usage_details") }
            catch (e: Exception) { "n/a" }
            val launchDetails = try{ jsonData!!.getJSONObject("economics").getJSONObject("launch").getJSONObject("general").getString("launch_details") }
            catch (e: Exception) { "n/a" }
            val supplyDetails = try{ jsonData!!.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("supply").getString("supply_curve_details") }
            catch (e: Exception) { "n/a" }
            val emissionType =  try{ jsonData!!.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("supply").getString("general_emission_type") }
            catch (e: Exception) { "n/a" }
            val consensusName = try{ jsonData!!.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("general_consensus_mechanism") }
            catch (e: Exception) { "n/a" }
            val consensusDetails = try{ jsonData!!.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("consensus_details") }
            catch (e: Exception) { "n/a" }
            val blockReward = try{ jsonData!!.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("block_reward").toString() }
            catch (e: Exception) { "n/a" }
            val algorithm = try{ jsonData!!.getJSONObject("economics").getJSONObject("consensus_and_emission").getJSONObject("consensus").getString("mining_algorithm") }
            catch (e: Exception) { "n/a" }
            val technologyDetails = try{ jsonData!!.getJSONObject("technology").getJSONObject("overview").getString("technology_details") }
            catch (e: Exception) { "n/a" }
            val governanceDetails = try{ jsonData!!.getJSONObject("governance").getString("governance_details") }
            catch (e: Exception) { "n/a" }
            return ProfileCoinData(title,details,category,sector,whitepaper,website,blockchainExplorer,tokenUsage,launchDetails,supplyDetails,emissionType,consensusName,consensusDetails,blockReward,algorithm,technologyDetails,governanceDetails)
        }

    }
}