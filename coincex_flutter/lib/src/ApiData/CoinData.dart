import 'dart:convert';
import 'package:http/http.dart' as http;

class CoinData {

  String id;
  String max24h;
  String min24h;
  String circulatingSupply;
  String maxSupply;
  String ath;
  String athChangePercent;
  String dateAth;
  String rank;
  String imageLogo;
  String symbol;
  String name;
  String cap;
  String volume;
  String price;
  String change24h;
  String changePercent;

  CoinData(this.id, this.max24h, this.min24h, this.circulatingSupply,
      this.maxSupply, this.ath,
      this.athChangePercent, this.dateAth, this.rank, this.imageLogo,
      this.symbol, this.name,
      this.cap, this.volume, this.price, this.change24h, this.changePercent);

  static List<CoinData> listCoin = [];

  static Future<List<CoinData>?> getCoinData() async {

    var result = await http.get(Uri.parse("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd"));
    var toJson = jsonDecode(result.body);
    if (result.statusCode == 200) {
      for (int i = 0; i < toJson.length; i++) {
        var id = toJson[i]["id"];
        var max24h = "${toJson[i]["high_24h"]}\$";
        var min24h = "${toJson[i]["low_24h"]}\$";

        var circulatingSupply = "";
        try {
          if (toJson[i]["circulating_supply"] >= 1000000000) {
            circulatingSupply =
                (toJson[i]["circulating_supply"] / 1000000000).toStringAsFixed(
                    2) + " B";
          }
          else {
            if (toJson[i]["circulating_supply"] >= 1000000 &&
                toJson[i]["circulating_supply"] < 1000000000) {
              circulatingSupply =
                  (toJson[i]["circulating_supply"] / 1000000).toStringAsFixed(
                      2) + " M";
            }
            else {
              circulatingSupply =
                  (toJson[i]["circulating_supply"] / 1000).toStringAsFixed(2) +
                      " K";
            }
          }
        }
        catch (e) {
          circulatingSupply = "n/a";
        }

        var maxSupply = "";
        try {
          if (toJson[i]["max_supply"] >= 1000000000) {
            maxSupply =
                (toJson[i]["max_supply"] / 1000000000).toStringAsFixed(2) +
                    " B";
          }
          else {
            if (toJson[i]["max_supply"] >= 1000000 &&
                toJson[i]["max_supply"] < 1000000000) {
              maxSupply =
                  (toJson[i]["max_supply"] / 1000000).toStringAsFixed(2) + " M";
            }
            else {
              maxSupply =
                  (toJson[i]["max_supply"] / 1000).toStringAsFixed(2) + " K";
            }
          }
        }
        catch (e) {
          maxSupply = "n/a";
        }

        var ath = "${toJson[i]["ath"].toString()}\$";
        var athChangePercent = toJson[i]["ath_change_percentage"]
            .toStringAsFixed(2) + "%";
        var dateAth = toJson[i]["ath_date"];
        var symbol = toJson[i]["symbol"].toString().toUpperCase();

        var rank = "";
        try {
          rank = toJson[i]["market_cap_rank"].toString();
        }
        catch (e) {
          rank = "n/a";
        }

        var name = toJson[i]["name"];
        var imageLogo = toJson[i]["image"];

        var cap = "";
        try {
          if (toJson[i]["market_cap"] > 1000000000) {
            cap = "${"\$" +
                (toJson[i]["market_cap"] / 1000000000).toStringAsFixed(2)} B";
          }
          else {
            cap = "${"\$" +
                (toJson[i]["market_cap"] / 1000000).toStringAsFixed(2)} M";
          }
        }
        catch (e) {
          cap = "n/a";
        }

        var volume = "";
        if (toJson[i]["total_volume"] > 1000000000) {
          volume = "${"\$" +
              (toJson[i]["total_volume"] / 1000000000).toStringAsFixed(2)} B";
        }
        else {
          volume = "${"\$" +
              (toJson[i]["total_volume"] / 1000000).toStringAsFixed(2)} M";
        }

        var price = "";
        try {
          price = "\$" + toJson[i]["current_price"].toStringAsFixed(2);
        }
        catch (e) {
          price = "n/a";
        }

        var change24h = "";
        try {
          if (toJson[i]["price_change_24h"] > 0) {
            change24h =
            "${"+" + toJson[i]["price_change_24h"].toStringAsFixed(2)}\$";
          }
          else {
            change24h = "${toJson[i]["price_change_24h"].toStringAsFixed(2)}\$";
          }
        }
        catch (e) {
          change24h = "n/a";
        }

        var changePercent = "";
        try {
          if (toJson[i]["price_change_percentage_24h"] > 0) {
            changePercent = "${"+" +
                toJson[i]["price_change_percentage_24h"].toStringAsFixed(2)}%";
          }
          else {
            changePercent =
            "${toJson[i]["price_change_percentage_24h"].toStringAsFixed(2)}%";
          }
        }
        catch (e) {
          changePercent = "n/a";
        }

        listCoin.add(CoinData(
            id,
            max24h,
            min24h,
            circulatingSupply,
            maxSupply,
            ath,
            athChangePercent,
            dateAth,
            rank,
            imageLogo,
            symbol,
            name,
            cap,
            volume,
            price,
            change24h,
            changePercent));
      }

      return listCoin;
    }
    else {
      return null;
    }
  }

}