import 'dart:convert';
import 'package:http/http.dart' as http;

class GlobalData {

  String marketCap;
  String volumeCap;
  String btcDom;
  String marketChange;
  String volumeChange;
  String btcDomChange;

  GlobalData(this.marketCap, this.volumeCap, this.btcDom, this.marketChange,
      this.volumeChange, this.btcDomChange);

  }

  Future<GlobalData> getGlobalData() async{
    var result = await http.get(Uri.parse("https://pro-api.coinmarketcap.com/v1/global-metrics/quotes/latest?CMC_PRO_API_KEY=e3dc6624-b531-4a8e-b501-7474e1e2455a"));;
    var toJson = jsonDecode(result.body);

    var marketCap = "";
    if (toJson["data"]["quote"]["USD"]["total_market_cap"] > 1000000000000) {
      marketCap = "${"\$"+(toJson["data"]["quote"]["USD"]["total_market_cap"]/1000000000000).toStringAsFixed(2)} T";
    }
    else {
      marketCap = "${"\$"+(toJson["data"]["quote"]["USD"]["total_market_cap"]/1000000000).toStringAsFixed(2)} B";
    }
    var volumeCap = "${"\$"+(toJson["data"]["quote"]["USD"]["total_volume_24h"]/1000000000).toStringAsFixed(2)} B";
    var btcDom = toJson["data"]["btc_dominance"].toStringAsFixed(2)+"%";

    var marketChange = "";
    if (toJson["data"]["quote"]["USD"]["total_market_cap_yesterday_percentage_change"] > 0) {
      marketChange = "${"+"+toJson["data"]["quote"]["USD"]["total_market_cap_yesterday_percentage_change"].toStringAsFixed(2)}%";
    }
    else {
      marketChange = toJson["data"]["quote"]["USD"]["total_market_cap_yesterday_percentage_change"].toStringAsFixed(2) + "%";
    }

    var volumeChange = "";
    if (toJson["data"]["quote"]["USD"]["total_volume_24h_yesterday_percentage_change"] > 0) {
      volumeChange = "${"+"+toJson["data"]["quote"]["USD"]["total_volume_24h_yesterday_percentage_change"].toStringAsFixed(2)}%";
    }
    else {
      volumeChange = toJson["data"]["quote"]["USD"]["total_volume_24h_yesterday_percentage_change"].toStringAsFixed(2)+"%";
    }

    var btcDomChange = "";
    if (toJson["data"]["btc_dominance_24h_percentage_change"] > 0) {
      btcDomChange = "${"+"+toJson["data"]["btc_dominance_24h_percentage_change"].toStringAsFixed(2)}%";
    }
    else {
      btcDomChange = toJson["data"]["btc_dominance_24h_percentage_change"].toStringAsFixed(2)+"%";
    }

    return GlobalData(marketCap, volumeCap, btcDom, marketChange, volumeChange, btcDomChange);
  }
