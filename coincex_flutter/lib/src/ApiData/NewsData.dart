import 'dart:convert';
import 'package:http/http.dart' as http;

class NewsData {

  String title;
  String description;
  String url;
  String image;
  String date;
  String source;

  NewsData(this.title, this.description, this.url, this.image, this.date, this.source);

  static Future<List<NewsData>> getNewsData() async{

    List<NewsData> listNews = [];
    var result = await http.get(Uri.parse("https://gnews.io/api/v4/search?q=cryptocurrency&token=56111f69aed38c3370b79abe1bb3c74d&sortBy=publishedAt"));
    var toJson = jsonDecode(result.body)["articles"];

    for (int i=0;i<toJson.length;i++) {
      var title = toJson[i]["title"];
      var description = toJson[i]["description"];
      var url = toJson[i]["url"];
      var image = toJson[i]["image"];
      var date = toJson[i]["publishedAt"];
      var source = toJson[i]["source"]["name"];
      listNews.add(NewsData(title, description, url, image, date, source));
    }
    return listNews;
  }

}



