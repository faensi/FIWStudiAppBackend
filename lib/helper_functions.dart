import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;
import 'package:shared_preferences/shared_preferences.dart';

class HelperFunctions {
  static MaterialColor createMaterialColor(Color color) {
    List strengths = <double>[.05];
    Map swatch = <int, Color>{};
    final int r = color.red, g = color.green, b = color.blue;

    for (int i = 1; i < 10; i++) {
      strengths.add(0.1 * i);
    }
    strengths.forEach(
      (strength) {
        final double ds = 0.5 - strength;
        swatch[(strength * 1000).round()] = Color.fromRGBO(
          r + ((ds < 0 ? r : (255 - r)) * ds).round(),
          g + ((ds < 0 ? g : (255 - g)) * ds).round(),
          b + ((ds < 0 ? b : (255 - b)) * ds).round(),
          1,
        );
      },
    );
    return MaterialColor(color.value, swatch);
  }

  static String displayTime() {
    return globals.cLastTime.toString().substring(0, 19);
  }

  static String getIso8601Time() {
    return DateTime.now().toIso8601String().substring(0, 19) + "+00:00";
  }

  static void setCoffeeImageByState() {
    if (globals.cState == "10") {
      globals.cofImageAdr = "Images/coffee_full.png";
      globals.cofStatus =
          "Kaffee gemacht" + "\n" + HelperFunctions.displayTime();
    } else if (globals.cState == "20") {
      globals.cofImageAdr = "Images/coffee_shutoff.png";
      globals.cofStatus =
          "Kaffeemaschine aus" + "\n" + HelperFunctions.displayTime();
    } else if (globals.cState == "30") {
      globals.cofImageAdr = "Images/coffee_empty.png";
      globals.cofStatus =
          "Kaffeemaschine leer" + "\n" + HelperFunctions.displayTime();
    } else if (globals.cState == "40") {
      globals.cofImageAdr = "Images/coffee_broken.png";
      globals.cofStatus =
          "Kaffeemaschine defekt" + "\n" + HelperFunctions.displayTime();
    } else {
      print("Error");
    }
  }

  static Future<void> loadLoginFromSharedPref() async {
    print("loading from shared prefs");
    final prefs = await SharedPreferences.getInstance();

    globals.kNumber = prefs.getString('kNumber') ?? "";
    globals.password = prefs.getString('password') ?? "";
    globals.isLoggedIn = prefs.getBool('isLoggedIn') ?? false;

    print("loaded from shared prefs");
  }

  static Future<void> storeLoginInSharedPref() async {
    print("saving to shared prefs");
    final prefs = await SharedPreferences.getInstance();

    await prefs.setString('kNumber', globals.kNumber);
    await prefs.setString('password', globals.password);
    await prefs.setBool('isLoggedIn', globals.isLoggedIn);
    print("saved to shared prefs");
  }
}
