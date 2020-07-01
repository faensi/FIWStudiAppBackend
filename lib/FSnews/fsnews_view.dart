import 'package:FIW_Studi_App/FSnews/fsnews_create_view.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Networking/networking_functions.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;
import 'dart:async';

class FSnewsView extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _FSnewsViewState();
  }
}

class _FSnewsViewState extends State<FSnewsView> {
  Timer _timer;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("FSNews"),
      ),
      body: ListView.builder(
        itemCount: globals.newsData == null
            ? 0
            : globals.newsData.length <= 10
                ? globals.newsData.length
                : 10, //-> maximum of 10 elements
        itemBuilder: (BuildContext context, int index) {
          // we will only use (globals.newsData.length - 1 - index) because we want the latest news
          return Container(
            child: Center(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  Card(
                    child: Container(
                      padding: EdgeInsets.all(20.0),
                      child: Wrap(
                        children: <Widget>[
                          Row(
                            children: <Widget>[
                              Text(
                                globals.newsData[globals.newsData.length -
                                        1 -
                                        index]["title"]
                                    .toString(),
                                style:
                                    Theme.of(context).primaryTextTheme.display1,
                              ),
                            ],
                          ),
                          Row(
                            children: <Widget>[
                              Expanded(
                                child: Text(
                                  globals.newsData[globals.newsData.length -
                                          1 -
                                          index]["text"]
                                      .toString(),
                                ),
                              ),
                            ],
                          ),
                          Row(
                            children: <Widget>[
                              Text("Author: "),
                              Text(
                                globals.newsData[globals.newsData.length -
                                        1 -
                                        index]["userName"]
                                    .toString(),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
      floatingActionButton:
          globals.isLoggedIn ? _buildButtonIfLoggedIn() : null,
    );
  }

  Widget _buildButtonIfLoggedIn() {
    return FloatingActionButton(
      child: Icon(
        Icons.add,
      ),
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => FSnewsCreateView()),
        );
      },
    );
  }

  @override
  void initState() {
    super.initState();
    _iniData();
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  Future<void> _iniData() async {
    await getNewsData();
    _timer = Timer.periodic(Duration(seconds: 3), (Timer t) => setState(() {}));
  }
}
