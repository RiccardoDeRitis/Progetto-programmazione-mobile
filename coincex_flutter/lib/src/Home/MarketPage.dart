import 'package:flutter/material.dart';
import 'package:coincex/src/ApiData/GlobalData.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../ApiData/CoinData.dart';


class MarketPage extends StatefulWidget {
  const MarketPage({Key? key}) : super(key: key);


  @override
  State<StatefulWidget> createState() => Market();

}

class Market extends State<MarketPage> {

  Future<List<CoinData>?> futureCoin = CoinData.getCoinData();

  Future<GlobalData> futureGlobal = getGlobalData();

  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
        onRefresh: () async{
          await Future.delayed(const Duration(seconds: 2));
          _updateList();
        },
        child: SingleChildScrollView(
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
              FutureBuilder<GlobalData>(
                  future: futureGlobal,
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.done) {
                      GlobalData globalData = snapshot.data!;
                      return Row(
                        children: [
                          const Padding(
                            padding: EdgeInsets.only(left: 10),
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text(
                                  "Market Cap",
                                  style: TextStyle(
                                      color: Color.fromRGBO(1, 135, 134, 0.8),
                                      fontSize: 14
                                  )
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 2),
                              ),
                              Text(
                                  globalData.marketCap,
                                  style: const TextStyle(
                                      color: Colors.white,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 14
                                  )
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 2),
                              ),
                              Text(
                                globalData.marketChange,
                                style: TextStyle(
                                    color: (_changeColor(
                                        globalData.marketChange) == true ? Colors
                                        .green : Colors.redAccent),
                                    fontWeight: FontWeight.bold,
                                    fontSize: 14
                                ),
                              )
                            ],
                          ),
                          const Padding(
                            padding: EdgeInsets.symmetric(horizontal: 30),
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text(
                                  "Volume 24h",
                                  style: TextStyle(
                                      color: Color.fromRGBO(1, 135, 134, 0.8),
                                      fontSize: 14
                                  )
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 2),
                              ),
                              Text(
                                  globalData.volumeCap,
                                  style: const TextStyle(
                                      color: Colors.white,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 14
                                  )
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 2),
                              ),
                              Text(
                                globalData.volumeChange,
                                style: TextStyle(
                                    color: (_changeColor(
                                        globalData.volumeChange) == true ? Colors
                                        .green : Colors.redAccent),
                                    fontWeight: FontWeight.bold,
                                    fontSize: 14
                                ),
                              )
                            ],
                          ),
                          const Padding(
                            padding: EdgeInsets.symmetric(horizontal: 30),
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text(
                                  "BTC Dominance",
                                  style: TextStyle(
                                      color: Color.fromRGBO(1, 135, 134, 0.8),
                                      fontSize: 14
                                  )
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 2),
                              ),
                              Text(
                                  globalData.btcDom,
                                  style: const TextStyle(
                                      color: Colors.white,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 14
                                  )
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 2),
                              ),
                              Text(
                                globalData.btcDomChange,
                                style: TextStyle(
                                    color: (_changeColor(
                                        globalData.btcDomChange) == true ? Colors
                                        .green : Colors.redAccent),
                                    fontWeight: FontWeight.bold,
                                    fontSize: 14
                                ),
                              )
                            ],
                          )
                        ],
                      );
                    }
                    else {
                      return const Text("");
                    }
                  }
              ),
              const Padding(
                  padding: EdgeInsets.only(bottom: 20, left: 15)
              ),
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  ElevatedButton.icon(
                      style: ElevatedButton.styleFrom(
                          primary: Colors.transparent
                      ),
                      onPressed: () {
                        Navigator.pushNamed(context, '/search');
                      },
                      icon: const Icon(Icons.search),
                      label: const Text("Search")
                  ),
                ],
              ),
              const Padding(
                  padding: EdgeInsets.only(bottom: 20)
              ),
              Row(
                  children: [
                    const Padding(
                      padding: EdgeInsets.only(left: 15),
                    ),
                    Column(
                      children: const [
                        Text(
                          "Rank",
                          style: TextStyle(
                              color: Color.fromRGBO(1, 135, 134, 0.8),
                              fontSize: 11
                          ),
                        )
                      ],
                    ),
                    const Padding(
                      padding: EdgeInsets.symmetric(horizontal: 35),
                    ),
                    Column(
                      children: const [
                        Text(
                          "Capitalizz./Volume",
                          style: TextStyle(
                              color: Color.fromRGBO(1, 135, 134, 0.8),
                              fontSize: 11
                          ),
                        )
                      ],
                    ),
                    const Padding(
                      padding: EdgeInsets.symmetric(horizontal: 40),
                    ),
                    Column(
                      children: const [
                        Text(
                          "Prezzo/Variazione",
                          style: TextStyle(
                              color: Color.fromRGBO(1, 135, 134, 0.8),
                              fontSize: 11
                          ),
                        )
                      ],
                    ),
                  ]
              ),
              const Padding(
                  padding: EdgeInsets.only(top: 20)
              ),
              _updateList()
            ],
          ),
        )
    );
  }

  FutureBuilder<List<CoinData>?> _updateList() {
    return FutureBuilder<List<CoinData>?>(
        future: futureCoin,
        builder: (context, snapshot) {
          if (snapshot.connectionState ==
              ConnectionState.done) {
            if (snapshot.data != null) {
              return ListView.separated(
                  separatorBuilder: (context, index) {
                    return const Divider(
                      height: 10,
                      color: Colors.white,
                      endIndent: 20,
                      indent: 20,
                    );
                  },
                  shrinkWrap: true,
                  physics: const NeverScrollableScrollPhysics(),
                  itemCount: snapshot.data!.length,
                  itemBuilder: (context, index) {
                    CoinData coin = snapshot.data![index];
                    return GestureDetector(
                      onTap: () {
                        Navigator.pushNamed(context, '/chart', arguments: coin);
                      },
                      child: Row(
                          children: [
                            Column(
                              children: [
                                SizedBox(
                                  width: 50,
                                  height: 70,
                                  child: ElevatedButton(
                                    onPressed: () => _preferred(coin.id),
                                    style: ElevatedButton.styleFrom(
                                        primary: Colors.transparent
                                    ),
                                    child: Text(
                                      coin.rank,
                                      style: const TextStyle(
                                        color: Color.fromRGBO(
                                            1, 135, 134, 0.8),
                                        fontSize: 12,
                                      ),
                                    ),
                                  ),
                                )
                              ],
                            ),
                            const Padding(
                                padding: EdgeInsets.only(
                                    left: 10)
                            ),
                            Column(
                              children: [
                                Image.network(
                                  coin.imageLogo,
                                  fit: BoxFit.cover,
                                  height: 30,
                                )
                              ],
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
                                        padding: EdgeInsets.only(
                                            left: 5),
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
                                                      true ? Colors
                                                      .green : Colors
                                                      .redAccent)
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
                                                      coin.changePercent
                                                  ) == true ? Colors.green
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
                                padding: EdgeInsets.only(left: 20)
                            )
                          ]
                      ),
                    );
                  }
              );
            } else {
              return const Center( child: Text("Impossibile la lista"));
            }
          } else {
            return const Text("");
          }
        }
    );
  }

  bool _changeColor(String change24h) {
    if (change24h.contains("+")) {
      return true;
    }
    return false;
  }

  void _preferred(String id) async {
    final pref = await SharedPreferences.getInstance();
    if (!pref.containsKey(id)) {
      await pref.setString(id, id);
      Fluttertoast.showToast(
          msg: "Aggiunto ai preferiti",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.BOTTOM,
          timeInSecForIosWeb: 1,
          backgroundColor: Colors.blueAccent,
          textColor: Colors.white,
          fontSize: 16.0
      );
    }
    else {
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

}

