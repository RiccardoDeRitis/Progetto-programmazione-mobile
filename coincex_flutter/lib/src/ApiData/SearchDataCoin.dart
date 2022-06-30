import 'dart:convert';

import 'package:http/http.dart' as http;

import 'CoinData.dart';


class SearchDataCoin {

  String id;
  String logo;
  String symbol;
  String name;

  SearchDataCoin(this.id, this.logo, this.symbol, this.name);

  static Future<List<SearchDataCoin>?> getSearchData(String query) async{

    List<SearchDataCoin> listSearch = [];
    var result = await http.get(Uri.parse("https://api.coingecko.com/api/v3/search?query=$query"));
    if (result.statusCode == 200) {
      var toJson = jsonDecode(result.body)["coins"];
      for (int i=0;i<toJson.length;i++) {
        var id = toJson[i]["id"];
        var logo = toJson[i]["large"];
        var symbol = toJson[i]["symbol"];
        var name = toJson[i]["name"];
        listSearch.add(SearchDataCoin(id, logo, symbol, name));
      }
      return listSearch;
    }
    else {
      return null;
    }
  }

  static Future<CoinData> getCoinData(String idCoin) async{
    var result = await http.get(Uri.parse("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=$idCoin"));
    var toJson = jsonDecode(result.body)[0];

    var id = idCoin;
    var max24h = "${toJson["high_24h"]}\$";
    var min24h = "${toJson["low_24h"]}\$";

    var circulatingSupply = "";
    try {
      if (toJson["circulating_supply"] >= 1000000000) {
        circulatingSupply =
            (toJson["circulating_supply"] / 1000000000).toStringAsFixed(
                2) + " B";
      }
      else {
        if (toJson["circulating_supply"] >= 1000000 &&
            toJson["circulating_supply"] < 1000000000) {
          circulatingSupply =
              (toJson["circulating_supply"] / 1000000).toStringAsFixed(
                  2) + " M";
        }
        else {
          circulatingSupply =
              (toJson["circulating_supply"] / 1000).toStringAsFixed(2) +
                  " K";
        }
      }
    }
    catch (e) {
      circulatingSupply = "n/a";
    }

    var maxSupply = "";
    try {
      if (toJson["max_supply"] >= 1000000000) {
        maxSupply =
            (toJson["max_supply"] / 1000000000).toStringAsFixed(2) +
                " B";
      }
      else {
        if (toJson["max_supply"] >= 1000000 &&
            toJson["max_supply"] < 1000000000) {
          maxSupply =
              (toJson["max_supply"] / 1000000).toStringAsFixed(2) + " M";
        }
        else {
          maxSupply =
              (toJson["max_supply"] / 1000).toStringAsFixed(2) + " K";
        }
      }
    }
    catch (e) {
      maxSupply = "n/a";
    }

    var ath = "${toJson["ath"].toString()}\$";
    var athChangePercent = toJson["ath_change_percentage"]
        .toStringAsFixed(2) + "%";
    var dateAth = toJson["ath_date"];
    var symbol = toJson["symbol"].toString().toUpperCase();

    var rank = "";
    try {
      rank = toJson["market_cap_rank"].toString();
    }
    catch (e) {
      rank = "n/a";
    }

    var name = toJson["name"];
    var imageLogo = toJson["image"];

    var cap = "";
    try {
      if (toJson["market_cap"] > 1000000000) {
        cap = "${"\$" +
            (toJson["market_cap"] / 1000000000).toStringAsFixed(2)} B";
      }
      else {
        cap = "${"\$" +
            (toJson["market_cap"] / 1000000).toStringAsFixed(2)} M";
      }
    }
    catch (e) {
      cap = "n/a";
    }

    var volume = "";
    if (toJson["total_volume"] > 1000000000) {
      volume = "${"\$" +
          (toJson["total_volume"] / 1000000000).toStringAsFixed(2)} B";
    }
    else {
      volume = "${"\$" +
          (toJson["total_volume"] / 1000000).toStringAsFixed(2)} M";
    }

    var price = "";
    try {
      price = "\$" + toJson["current_price"].toStringAsFixed(2);
    }
    catch (e) {
      price = "n/a";
    }

    var change24h = "";
    try {
      if (toJson["price_change_24h"] > 0) {
        change24h =
        "${"+" + toJson["price_change_24h"].toStringAsFixed(2)}\$";
      }
      else {
        change24h = "${toJson["price_change_24h"].toStringAsFixed(2)}\$";
      }
    }
    catch (e) {
      change24h = "n/a";
    }

    var changePercent = "";
    try {
      if (toJson["price_change_percentage_24h"] > 0) {
        changePercent = "${"+" +
            toJson["price_change_percentage_24h"].toStringAsFixed(2)}%";
      }
      else {
        changePercent =
        "${toJson["price_change_percentage_24h"].toStringAsFixed(2)}%";
      }
    }
    catch (e) {
      changePercent = "n/a";
    }

    return CoinData(id, max24h, min24h, circulatingSupply, maxSupply, ath, athChangePercent, dateAth, rank, imageLogo, symbol, name, cap, volume, price, change24h, changePercent);

  }

}