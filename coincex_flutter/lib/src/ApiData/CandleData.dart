import 'dart:convert';
import 'package:candlesticks/candlesticks.dart';
import 'package:http/http.dart' as http;

class CandleData {

  static Future<List<Candle>?> getCandleData(String symbol) async{

    var result = await http.get(Uri.parse("https://api.binance.com/api/v3/klines?symbol=${symbol}USDT&interval=1h"));
    List<Candle> listCandle = [];
    if (result.statusCode == 200) {
      var toJson = jsonDecode(result.body).reversed.toList();
      for (int i = 0; i < toJson.length; i++) {
        var time = DateTime.fromMillisecondsSinceEpoch(toJson[i][0]);
        var open = double.parse(toJson[i][1]);
        var high = double.parse(toJson[i][2]);
        var low = double.parse(toJson[i][3]);
        var close = double.parse(toJson[i][4]);
        var volume = double.parse(toJson[i][5]);
        listCandle.add(Candle(date: time,
            high: high,
            low: low,
            open: open,
            close: close,
            volume: volume));
      }
      return listCandle;
    }
    else {
      return null;
    }
  }

}