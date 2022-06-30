import 'package:flutter/material.dart';
import 'package:coincex/src/ApiData/SearchDataCoin.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../ApiData/CoinData.dart';

class SearchCoinPage extends StatefulWidget {
  const SearchCoinPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => SearchCoin();

}

class SearchCoin extends State<SearchCoinPage> {

  final _controller = TextEditingController();
  bool search = false;

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: SingleChildScrollView(
        physics: const ScrollPhysics(),
        child: Column(
          children: [
            Container(
              height: 50,
              width: MediaQuery.of(context).size.width,
              margin: const EdgeInsets.only(left: 20, right: 20, top: 50),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.blueAccent),
                borderRadius: BorderRadius.circular(20),
              ),
              child: Row(
                children: [
                  IconButton(
                    icon: const Icon(Icons.search),
                    onPressed: () { _search(); },
                    color: Colors.blueAccent,
                  ),
                  Expanded(
                      child: TextField(
                        controller: _controller,
                        textAlign: TextAlign.left,
                        decoration: const InputDecoration(
                            hintText: "Search coin by name or symbol..",
                            border: InputBorder.none
                        ),
                      )
                  )
                ],
              ),
            ),
            Builder(
                builder: (context) {
                  if (search) {
                    return _searchQuery(_controller.text);
                  }
                  else {
                    return const Text("");
                  }
                }
            )
          ],
        ),
      ),
    );
  }
  
  SingleChildScrollView _searchQuery(String query) {

    Future<List<SearchDataCoin>?> futureSearch = SearchDataCoin.getSearchData(query);

    return SingleChildScrollView(
        physics: const ScrollPhysics(),
        child: FutureBuilder<List<SearchDataCoin>?>(
            future: futureSearch,
            builder: (context, snapshot) {
              if (snapshot.connectionState ==
                  ConnectionState.done) {
                if (snapshot.data != null) {
                  return ListView.separated(
                      separatorBuilder: (context, index) {
                        return const Divider(
                          height: 10,
                          color: Colors.white,
                          endIndent: 10,
                          indent: 10,
                        );
                      },
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: snapshot.data!.length,
                      itemBuilder: (context, index) {
                        SearchDataCoin coin = snapshot.data![index];
                        _changeColor(coin.id);
                        return GestureDetector(
                          onTap: () async {
                            CoinData coinData = await SearchDataCoin.getCoinData(coin.id);
                            _push(coinData);
                          },
                          child: SizedBox(
                            height: 70,
                            child: Row(
                              children: [
                                const Padding(
                                    padding: EdgeInsets.only(left: 10, bottom: 50)
                                ),
                                Image.network(
                                    coin.logo,
                                    fit: BoxFit.cover,
                                    height: 30
                                ),
                                const Padding(
                                    padding: EdgeInsets.only(left: 10)
                                ),
                                Text(
                                  coin.symbol,
                                  style: const TextStyle(
                                      color: Colors.white,
                                      fontSize: 14,
                                      fontWeight: FontWeight.bold
                                  ),
                                ),
                                const Padding(
                                    padding: EdgeInsets.only(left: 10)
                                ),
                                Text(
                                  _name(coin.name),
                                  style: const TextStyle(
                                    color: Colors.white,
                                    fontSize: 14,
                                  ),
                                ),
                                Expanded(
                                    child: Row(
                                      mainAxisAlignment: MainAxisAlignment.end,
                                      children: [
                                        IconButton(
                                          onPressed: () => _preferred(coin.id),
                                          icon: const Icon(Icons.star),
                                          color: Colors.yellowAccent
                                        )
                                      ],
                                    )
                                )
                              ],
                            ),
                          ),
                        );
                      }
                  );
                }
                else {
                  return const Text("");
                }
              }
              else {
                return const Text("");
              }
            }
        ),
      );
  }

  void _search() {
    setState(() {
      search = true;
    });
  }

  String _name(String name) {
    if (name.length > 12) {
      return name.substring(0, 12);
    } else {
      return name;
    }
  }

  void _push(CoinData coin) {
    Navigator.pushNamed(context, '/chart', arguments: coin);
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

  void _changeColor(String id) {

  }

}


