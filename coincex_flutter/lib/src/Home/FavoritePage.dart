import 'package:flutter/material.dart';
import 'package:coincex/src/ApiData/CoinData.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FavoritePage extends StatefulWidget {
  const FavoritePage({Key? key}) : super(key: key);


  @override
  State<StatefulWidget> createState() => Favorite();

}

class Favorite extends State<FavoritePage> {

  static String _title = "";

  Future<List<CoinData>> setList() async {
    List<CoinData> listCoin = [];
    final pref = await SharedPreferences.getInstance();
    final keys = pref.getKeys();
    if (keys.isNotEmpty) {
      setTitleSucc();
      for (String key in keys) {
        for (CoinData coin in CoinData.listCoin) {
          if (coin.id == key) {
            listCoin.add(coin);
          }
        }
      }
      return listCoin;
    }
    else {
      setTitleFail();
      return listCoin;
    }
  }

  @override
  void initState() {
    super.initState();
    setState(() {
      setList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        physics: const ScrollPhysics(),
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
              children: [
                const Padding(
                    padding: EdgeInsets.only(left: 20)
                ),
                Text(
                    _title,
                    textAlign: TextAlign.start,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.white,
                    )
                ),
              ],
            ),
            const Padding(
                padding: EdgeInsets.only(top: 50, left: 20)
            ),
            Column(
              children: [
                FutureBuilder<List<CoinData>>(
                  future: setList(),
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.done) {
                      snapshot.data!.sort((a, b) => a.id.compareTo(b.id));
                      return ListView.separated(
                          separatorBuilder: (context, index) {
                            return const Divider(
                              height: 10,
                              color: Colors.white,
                              endIndent: 20,
                              indent: 10,
                            );
                          },
                          shrinkWrap: true,
                          itemCount: snapshot.data!.length,
                          itemBuilder: (context, index) {
                            CoinData coin = snapshot.data![index];
                            return GestureDetector(
                              onTap: () {
                                Navigator.pushNamed(context, '/chart', arguments: coin);
                              },
                              child: Row(
                                  children: [
                                    const Padding(
                                      padding: EdgeInsets.only(
                                          bottom: 70),
                                    ),
                                    const Padding(
                                        padding: EdgeInsets.only(
                                            left: 10)
                                    ),
                                    GestureDetector(
                                        onTap: () {
                                          _removePreferred(coin.id);
                                          setState(() {
                                            setList();
                                          });
                                        },
                                        child: Column(
                                          children: [
                                            Image.network(
                                              coin.imageLogo,
                                              fit: BoxFit.cover,
                                              height: 30,
                                            )
                                          ],
                                        )
                                    ),
                                    const Padding(
                                      padding: EdgeInsets.only(
                                          left: 22),
                                    ),
                                    Column(
                                      mainAxisAlignment: MainAxisAlignment
                                          .start,
                                      crossAxisAlignment: CrossAxisAlignment
                                          .start,
                                      children: [
                                        Row(
                                            mainAxisAlignment: MainAxisAlignment
                                                .start,
                                            children: [
                                              Text(
                                                  coin.symbol,
                                                  style: const TextStyle(
                                                      color: Colors.white,
                                                      fontSize: 14,
                                                      fontWeight: FontWeight
                                                          .bold
                                                  )
                                              ),
                                              const Padding(
                                                padding: EdgeInsets.only(left: 5),
                                              ),
                                              Text(
                                                  coin.name,
                                                  style: const TextStyle(
                                                    color: Colors.white,
                                                    fontSize: 13,
                                                  )
                                              ),
                                            ]
                                        ),
                                        Row(
                                            mainAxisAlignment: MainAxisAlignment
                                                .start,
                                            children: [
                                              Text(
                                                  coin.cap,
                                                  style: const TextStyle(
                                                      color: Colors
                                                          .white,
                                                      fontSize: 13,
                                                      fontWeight: FontWeight
                                                          .bold
                                                  )
                                              ),
                                              const Padding(
                                                padding: EdgeInsets
                                                    .only(left: 5),
                                              ),
                                              Text(
                                                  coin.volume,
                                                  style: const TextStyle(
                                                    color: Colors.grey,
                                                    fontSize: 13,
                                                  )
                                              )
                                            ]
                                        ),
                                      ],
                                    ),
                                    Expanded(
                                      child: Column(
                                          mainAxisSize: MainAxisSize.min,
                                          crossAxisAlignment: CrossAxisAlignment
                                              .end,
                                          children: [
                                            Text(
                                              coin.price,
                                              style: const TextStyle(
                                                  color: Colors.white,
                                                  fontSize: 14,
                                                  fontWeight: FontWeight
                                                      .bold
                                              ),
                                            ),
                                            Row(
                                                mainAxisSize: MainAxisSize.min,
                                                crossAxisAlignment: CrossAxisAlignment
                                                    .end,
                                                children: [
                                                  Text(
                                                      coin.change24h,
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          color: (_changeColor(
                                                              coin.change24h) ==
                                                              true
                                                              ? Colors.green
                                                              : Colors.redAccent)
                                                      )
                                                  ),
                                                  const Padding(
                                                      padding: EdgeInsets
                                                          .only(left: 5)
                                                  ),
                                                  Text(
                                                      coin.changePercent,
                                                      style: TextStyle(
                                                          fontSize: 12,
                                                          color: (_changeColor(
                                                              coin.changePercent) ==
                                                              true
                                                              ? Colors.green
                                                              : Colors.redAccent),
                                                          fontWeight: FontWeight
                                                              .bold
                                                      )
                                                  )
                                                ]
                                            )
                                          ]
                                      ),
                                    ),
                                    const Padding(
                                        padding: EdgeInsets.only(
                                            bottom: 10, left: 20)
                                    ),
                                  ]
                              ),
                            );
                          }
                      );
                    }
                    else {
                      setTitleFail();
                      return const Text("");
                    }
                  },
                ),
              ],
            )
          ],
        )
    );
  }

  void setTitleSucc() {
    _title = "I tuoi asset preferiti sono :";
  }

  void setTitleFail() {
    _title = "Non stai seguendo alcun asset.\n Prova a seguire qualche asset!";
  }

  bool _changeColor(String change24h) {
    if (change24h.contains("+")) {
      return true;
    }
    return false;
  }

  void _removePreferred(String id) async{
    final pref = await SharedPreferences.getInstance();
    await pref.remove(id);
    Fluttertoast.showToast(
        msg: "Rimosso dai preferiti",
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIosWeb: 1,
        backgroundColor: Colors.blueAccent,
        textColor: Colors.white,
        fontSize: 16.0
    );
  }

}

