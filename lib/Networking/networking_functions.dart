import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:FIW_Studi_App/globals.dart' as globals;
import 'package:flutter_custom_tabs/flutter_custom_tabs.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/helper_functions.dart';

final String _coffeeGetUrl =
    "https://apistaging.fiw.fhws.de/studi-app/api/coffee-machine/getCurrentState";

Future<http.Response> postNews(String title, String text) async {
  var url = 'https://apistaging.fiw.fhws.de/studi-app/api/news';
  String username = globals.kNumber;
  String password = globals.password;

  Map data = {
    "image": "",
    "text": text,
    "time": HelperFunctions.getIso8601Time(),
    "title": title,
    "userName": username,
  };
  var body = json.encode(data);
  var response = await http.post(url,
      headers: {
        HttpHeaders.authorizationHeader: _basicAuth(username, password),
        "Content-Type": "application/json"
      },
      body: body);
  return response;
}

Future<http.Response> postState(int pState) async {
  var url = 'https://apistaging.fiw.fhws.de/studi-app/api/coffee-machine/';

  Map data = {
    "state": pState,
    "statusTime": HelperFunctions.getIso8601Time(),
    "userName": globals.kNumber,
  };
  var body = json.encode(data);

  var response = await http.post(url,
      headers: {"Content-Type": "application/json"}, body: body);
  return response;
}

String _basicAuth(username, password) {
  return 'Basic ' + base64Encode(utf8.encode('$username:$password'));
}

Future<String> getNewsData() async {
  http.Response response = await http.get(
    Uri.encodeFull('https://apistaging.fiw.fhws.de/studi-app/api/news'),
    headers: {
      /*
        HttpHeaders.authorizationHeader:
            basicAuth(globals.kNumber, globals.password),
        */
      "Accept": "application/json"
    },
  );
  globals.newsData = jsonDecode(utf8.decode(response.bodyBytes));
  return "Success!";
}

Future<String> getCData() async {
  http.Response response = await http.get(
    Uri.encodeFull(_coffeeGetUrl),
    headers: {"Accept": "application/json"},
  );
  globals.cData = jsonDecode(response.body);
  globals.cState = globals.cData["state"].toString();
  globals.cLastTime = DateTime.parse(globals.cData["statusTime"]);
  globals.cUserName = globals.cData[
      "userName"]; //momentan nicht in Verwendung; hier Aufgrund der Datenabnk
  return "Success!";
}

Future<void> launchURL(BuildContext context) async {
  try {
    await launch(
      'https://unicorns.diamonds/vp',
      option: new CustomTabsOption(
        toolbarColor: Theme.of(context).primaryColor,
        enableDefaultShare: true,
        enableUrlBarHiding: true,
        showPageTitle: true,
        animation: new CustomTabsAnimation.slideIn(),
        // or user defined animation.
        extraCustomTabs: <String>[
          // ref. https://play.google.com/store/apps/details?id=org.mozilla.firefox
          'org.mozilla.firefox',
          // ref. https://play.google.com/store/apps/details?id=com.microsoft.emmx
          'com.microsoft.emmx',
        ],
      ),
    );
  } catch (e) {
    // An exception is thrown if browser app is not installed on Android device.
    debugPrint(e.toString());
  }
}
