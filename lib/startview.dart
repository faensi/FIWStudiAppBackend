import 'package:FIW_Studi_App/Coffee/coffee_card.dart';
import 'package:FIW_Studi_App/Scheduler/schedule_card.dart';
import 'package:FIW_Studi_App/FSnews/fsnews_card.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/UI/nav_drawer.dart';
import 'package:FIW_Studi_App/KaimWebview/kaim_webview_card.dart';
import 'Rooms/rooms1_card.dart';
import 'Rooms/rooms2_card.dart';
import 'helper_functions.dart';
import 'style.dart';
import 'globals.dart' as globals;

class StartView extends StatefulWidget {
  @override
  _StartViewState createState() => _StartViewState();
}

class _StartViewState extends State<StartView> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: themeData,
      title: 'FIW Studi App',
      home: Scaffold(
        appBar: AppBar(
          title: Text('FIW Studi App'),
        ),
        drawer: NavDrawer(),
        body: GridView.count(
          //beschreibt die anzahl der Spalten der Angezeigten Karten
          crossAxisCount: 2,
          padding: EdgeInsets.all(16.0),
          childAspectRatio: 8.0 / 9.0,
          children: <Widget>[
            CoffeeCard(),
            FSnewsCard(),
            ScheduleCard(),
            KaimWVCard(),
            Rooms1Card(),
            Rooms2Card(),
          ],
        ),
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    _loadLogin();
  }

  Future<void> _loadLogin() async {
    await HelperFunctions.loadLoginFromSharedPref();
    print("geladen aus shared " + globals.kNumber);
  }
}
