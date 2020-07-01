import 'package:FIW_Studi_App/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Coffee/coffee_view.dart';
import 'dart:async';
import 'package:FIW_Studi_App/globals.dart' as globals;
import 'package:FIW_Studi_App/Networking/networking_functions.dart';

import '../globals.dart';

class CoffeeButton extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CoffeeButtonState();
  }
}

class _CoffeeButtonState extends State<CoffeeButton> {
  Timer _timer;

  @override
  Widget build(BuildContext context) {
    // erzeugt Container mit Größe 200x200 Pixel für den KaffeeButton
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Container(
          constraints: BoxConstraints.expand(
            height: 200,
            width: 200,
          ),
          child: Center(
            child: GestureDetector(
              onTap: () => setState(() {
                getCData();
                HelperFunctions.setCoffeeImageByState();
              }),
              child: Image.asset(cofImageAdr),
            ),
          ),
        ),
        SizedBox(
          height: 14,
        ),
        Container(
          child: Center(
            child: Text(
              cofStatus,
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
          ),
        )
      ],
    );
  }

  @override
  void initState() {
    super.initState();
    _iniData();
  }

  //dispose ist dazu da, damit es keine Memory leaks gibt
  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  Future<void> _iniData() async {
    _timer = Timer.periodic(Duration(seconds: 3), (Timer t) {
      if (mounted) setState(() {});
    });
  }
}
