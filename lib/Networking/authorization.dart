import 'package:FIW_Studi_App/globals.dart' as globals;
import 'package:flutter/cupertino.dart';

import '../helper_functions.dart';

void getAInfo() {
  //---------unfinished because waiting for Backend-Auth-Feature
/*
Future<http.Response> getAInfo() async {


  var url = 'https://apistaging.fiw.fhws.de/studi-app/api/';
  String username = globals.kNumber;
  String password = globals.password;

  String basicAuth =
      'Basic ' + base64Encode(utf8.encode('$username:$password'));
  Map data = {};
  var body = json.encode(data);
  var response = await http.post(url,
      headers: {
        HttpHeaders.authorizationHeader: basicAuth,
        "Content-Type": "application/json"
      },
      body: body);
  return response;
   */
}

Future<void> login() async {
  getAInfo();
  //instead of true -> json... something... body... something...== something
  if (true) globals.isLoggedIn = true;
  await HelperFunctions.storeLoginInSharedPref();
}

Future<void> logout() async {
  globals.isLoggedIn = false;
  globals.kNumber = "";
  globals.password = "";
  await HelperFunctions.storeLoginInSharedPref();
}
