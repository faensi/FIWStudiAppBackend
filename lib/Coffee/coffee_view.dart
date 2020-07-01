import 'package:FIW_Studi_App/Coffee/coffee_button.dart';
import 'package:FIW_Studi_App/Coffee/coffee_headline.dart';
import 'package:FIW_Studi_App/Coffee/coffee_text.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Networking/networking_functions.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;
import 'package:FIW_Studi_App/Networking/authorization.dart' as auth;

class CoffeeView extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CoffeeViewState();
  }
}

class _CoffeeViewState extends State<CoffeeView> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Kaffeemaschine"),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.vpn_key),
            onPressed: () {
              auth.logout();
              setState(() {});
            },
          ),
        ],
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          CoffeeHeadline(),
          Divider(
            height: 40,
          ),
          CoffeeButton(),
          CoffeeText(),
          Divider(
            height: 20,
          ),
          if (globals.isLoggedIn) _loggedInBar(),
        ],
      ),
    );
  }

  Widget _loggedInBar() {
    return ButtonBar(
      alignment: MainAxisAlignment.center,
      children: <Widget>[
        RaisedButton(
          onPressed: () async {
            postState(10);
          },
          child: const Text("Fertig"),
        ),
        RaisedButton(
          onPressed: () async {
            postState(20);
          },
          child: const Text("Aus"),
        ),
        RaisedButton(
          onPressed: () async {
            postState(30);
          },
          child: const Text("Leer"),
        ),
        RaisedButton(
          onPressed: () async {
            postState(40);
          },
          child: const Text("Defekt!"),
        ),
      ],
    );
  }
}
