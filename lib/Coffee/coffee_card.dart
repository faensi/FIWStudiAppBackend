import 'dart:async';
import 'package:FIW_Studi_App/Networking/networking_functions.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Coffee/coffee_view.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;
import '../helper_functions.dart';

class CoffeeCard extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CoffeeCardState();
  }
}

class _CoffeeCardState extends State<CoffeeCard> {
  Timer _timer;

  @override
  Widget build(BuildContext context) {
    // macht die "Card" anklickbar
    final image = globals.cofImageAdr;
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => CoffeeView()),
        );
      },
      child: Card(
        clipBehavior: Clip.antiAlias,
        child: Column(
          // Anordnung der Elemente, entweder Horizontal oder Vertikal
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            // Rellation von Breite und höhe der image
            AspectRatio(
              aspectRatio: 18.0 / 11.0,
              child: Image.asset(image),
            ),
            Padding(
              // genaue Eckdaten von von allen seiten wie groß das feld sein soll
              padding: EdgeInsets.fromLTRB(16.0, 12.0, 16.0, 8.0),
              child: Column(
                // Anordnung der Elemente, entweder Horizontal oder Vertikal
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Divider(
                    height: 20,
                  ),
                  Center(child: Text('Kaffeemaschine')),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    _iniData();
  }

  //dispose ist dazu da, damit es keine Memoryleaks gibt
  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  Future<void> _iniData() async {
    _updateCoffeeImage();
    _timer = Timer.periodic(Duration(seconds: 3), (Timer t) {
      if (mounted)
        setState(() {
          _updateCoffeeImage();
        });
    });
  }

  Future<void> _updateCoffeeImage() async {
    await getCData();
    HelperFunctions.setCoffeeImageByState();
  }
}
