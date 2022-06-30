import 'package:coincex/src/Home/CoinChart.dart';
import 'package:coincex/src/Home/FavoritePage.dart';
import 'package:flutter/material.dart';
import 'package:coincex/src/Home/SearchCoinPage.dart';

import 'Home/MarketPage.dart';
import 'Home/NewsPage.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'CoinCex',
      routes: {
        '/chart': (context) => const CoinChart(),
        '/search': (context) => const SearchCoinPage()
      },
      theme: ThemeData.dark(),
      home: const HomePage(title: 'CoinCex'),
    );
  }
}

class HomePage extends StatefulWidget {

  const HomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<HomePage> createState() => _HomePageState();

}

class _HomePageState extends State<HomePage> {

  int _selectedIndex = 0;
  static final List<Widget> _pages = [
    const MarketPage(),
    const FavoritePage(),
    const NewsPage()
  ];

  @override
  Widget build(BuildContext context) {

    return Scaffold(
        resizeToAvoidBottomInset: false,
        backgroundColor: Colors.black,
        body: SafeArea(
          child: IndexedStack(
              index: _selectedIndex,
              children: _pages
          )
        ),
        bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.shifting,
          unselectedItemColor: Colors.grey,
          selectedItemColor: const Color.fromRGBO(1, 135, 134, 0.8),
          items: const [
            BottomNavigationBarItem(
                icon: Icon(Icons.candlestick_chart),
                label: "Market",
                backgroundColor: Colors.black38
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.star),
                label: "Favorite",
                backgroundColor: Colors.black26
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.newspaper),
                label: "News",
                backgroundColor: Colors.black12
            ),
          ],
          currentIndex: _selectedIndex,
          onTap: _change,
        )
    );
  }

  void _change(int index) {
    _pages.removeAt(1);
    _pages.insert(1, FavoritePage());
    setState(() => _selectedIndex = index);
  }

}
