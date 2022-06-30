import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:coincex/src/ApiData/NewsData.dart';

class NewsPage extends StatefulWidget {

  const NewsPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => News();

}

class News extends State<NewsPage> {

  Future<List<NewsData>> futureNews = NewsData.getNewsData();

  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
      onRefresh: () async{
        await Future.delayed(const Duration(seconds: 2));
        _updateNews();
      },
      child: SingleChildScrollView(
          physics: const ScrollPhysics(),
          child: Center(
            child: Column(
              children: [
                const Padding(
                    padding: EdgeInsets.only(top: 30)
                ),
                Image.asset(
                  "Image/coinex.png",
                  alignment: Alignment.center,
                  width: 200,
                  height: 100,
                  fit: BoxFit.cover,
                ),
                const Padding(
                    padding: EdgeInsets.only(top: 20)
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: const [
                    Padding(
                        padding: EdgeInsets.only(left: 20)
                    ),
                    Text(
                        "Crypto news",
                        textAlign: TextAlign.start,
                        style: TextStyle(
                          fontSize: 16,
                          color: Colors.white,
                        )
                    ),
                  ],
                ),
                const Padding(
                    padding: EdgeInsets.only(top: 50, left: 20)
                ),
                _updateNews()
              ],
            ),
          )
      )
    );

  }

  String _setTitle(String description) {
    if (description.length > 100) {
      return "${description.substring(0,100)}...";
    }
    else {
      return description;
    }
  }

  FutureBuilder<List<NewsData>> _updateNews() {
    return FutureBuilder<List<NewsData>>(
        future: futureNews,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            return ListView.separated(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                NewsData news = snapshot.data!.elementAt(index);
                return GestureDetector(
                  onTap: () async {
                    if(await canLaunchUrl(Uri.parse(news.url))) {
                      await launchUrl(
                        Uri.parse(news.url),
                      );
                    }
                    else {
                      throw "Errore";
                    }
                  },
                  child: SizedBox(
                    height: 130,
                    child: Row(
                        children: [
                          const Padding(
                              padding: EdgeInsets.only(left: 5)
                          ),
                          Column(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Image.network(
                                news.image,
                                width: 90,
                                height: 100,
                                fit: BoxFit.cover,
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  Text(
                                    news.date.substring(0, 10),
                                    style: const TextStyle(
                                        fontSize: 11,
                                        color: Colors.blueAccent
                                    ),
                                  )
                                ],
                              )
                            ],
                          ),
                          const Padding(
                              padding: EdgeInsets.only(left: 10)
                          ),
                          Expanded(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    news.title,
                                    style: const TextStyle(
                                        fontSize: 14,
                                        fontWeight: FontWeight.bold,
                                        color: Colors.white
                                    ),
                                  ),
                                  Text(
                                    (_setTitle(news.description)),
                                    style: const TextStyle(
                                        fontSize: 13,
                                        color: Colors.white70
                                    ),
                                  ),
                                  Text(
                                    news.source,
                                    style: const TextStyle(
                                        fontSize: 11,
                                        color: Colors.blueAccent
                                    ),
                                  )
                                ],
                              )
                          ),
                          const Padding(
                              padding: EdgeInsets.only(right: 15)
                          )
                        ]
                    ),
                  ),
                );
              }, separatorBuilder: (BuildContext context, int index) {
              return const Divider(height: 10,
                  color: Colors.white,
                  endIndent: 5,
                  indent: 5);
            },
            );
          }
          else {
            return const Text("Errore");
          }
        }
    );
  }

}