import 'package:flutter/material.dart';
import 'package:candlesticks/candlesticks.dart';
import 'package:coincex/src/ApiData/CandleData.dart';
import '../ApiData/CoinData.dart';

class CoinChart extends StatelessWidget{
  const CoinChart({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    final coin = ModalRoute.of(context)!.settings.arguments as CoinData;
    Future<List<Candle>?> futureCandle = CandleData.getCandleData(coin.symbol);

    return Scaffold(
      backgroundColor: Colors.black,
      body: Column(
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
              padding: EdgeInsets.only(top: 40)
          ),
          Row(
            children: [
              const Padding(
                  padding: EdgeInsets.only(left: 10)
              ),
              Image.network(
                coin.imageLogo,
                fit: BoxFit.cover,
                height: 35,
              ),
              const Padding(
                  padding: EdgeInsets.only(left: 5)
              ),
              Text(
                coin.name,
                style: const TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.bold,
                  color: Colors.white
                ),
              ),
              const Padding(
                  padding: EdgeInsets.only(left: 20)
              ),
              SizedBox(
                height: 50,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          "Highest 24h",
                          style: TextStyle(
                              fontSize: 12,
                              color: Colors.white,
                              fontWeight: FontWeight.bold
                          ),
                        ),
                        Text(
                          coin.max24h,
                          style: const TextStyle(
                              fontSize: 11,
                              color: Colors.white
                          ),
                        ),
                      ],
                    ),
                    const Padding(
                        padding: EdgeInsets.only(left: 20)
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          "Least 24h",
                          style: TextStyle(
                              fontSize: 12,
                              color: Colors.white,
                              fontWeight: FontWeight.bold
                          ),
                        ),
                        Text(
                          coin.min24h,
                          style: const TextStyle(
                              fontSize: 11,
                              color: Colors.white
                          ),
                        )
                      ],
                    ),
                    const Padding(
                        padding: EdgeInsets.only(left: 20)
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          "All time high",
                          style: TextStyle(
                              fontSize: 12,
                              color: Colors.white,
                              fontWeight: FontWeight.bold
                          ),
                        ),
                        Row(
                          children: [
                            Text(
                              coin.ath,
                              style: const TextStyle(
                                  fontSize: 11,
                                  color: Colors.white
                              ),
                            ),
                            const Padding(
                                padding: EdgeInsets.only(left: 5)
                            ),
                            Text(
                              "(${coin.athChangePercent})",
                              style: const TextStyle(
                                  fontSize: 11,
                                  color: Colors.white
                              ),
                            )
                          ],
                        ),
                        Text(
                          coin.dateAth.substring(0,10),
                          style: const TextStyle(
                              fontSize: 11,
                              color: Colors.white
                          ),
                        )
                      ],
                    )
                  ],
                ),
              )
            ],
          ),
          const Padding(
              padding: EdgeInsets.only(top: 15)
          ),
          Row(
            children: [
              const Padding(
                  padding: EdgeInsets.only(left: 10)
              ),
              const Text(
                "Rank : ",
                style: TextStyle(
                    fontSize: 13,
                    color: Colors.white
                ),
              ),
              Text(
                coin.rank,
                style: const TextStyle(
                    fontSize: 13,
                    color: Colors.white
                ),
              )
            ],
          ),
          Row(
            children: [
              const Padding(
                  padding: EdgeInsets.only(left: 10)
              ),
              const Text(
                "Circulating supply : ",
                style: TextStyle(
                    fontSize: 13,
                    color: Colors.white
                ),
              ),
              const Padding(
                  padding: EdgeInsets.only(left: 3)
              ),
              Text(
                coin.circulatingSupply,
                style: const TextStyle(
                    fontSize: 13,
                    fontWeight: FontWeight.bold,
                    color: Colors.white
                ),
              ),
              const Padding(
                  padding: EdgeInsets.only(left: 20)
              ),
              const Text(
                "Max supply : ",
                style: TextStyle(
                    fontSize: 13,
                    fontWeight: FontWeight.bold,
                    color: Colors.white
                ),
              ),
              const Padding(
                  padding: EdgeInsets.only(left: 3)
              ),
              Text(
                coin.maxSupply,
                style: const TextStyle(
                    fontSize: 13,
                    color: Colors.white
                ),
              )
            ],
          ),
          const Padding(
              padding: EdgeInsets.only(top: 40)
          ),
          SizedBox(
            height: 500,
            child: FutureBuilder<List<Candle>?>(
              future: futureCandle,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  if (snapshot.data != null) {
                    return Candlesticks(
                        candles: snapshot.data!
                    );
                  } else {
                    return const Center( child: Text("Impossibile caricare il grafico"));
                  }
                } else {
                  return const Text("");
                }
              },
            )
          )
        ],
      )
    );
  }

}