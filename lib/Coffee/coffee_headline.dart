import 'package:flutter/material.dart';

class CoffeeHeadline extends StatelessWidget {
  static const double _hPad = 16.0;
  @override
  Widget build(BuildContext context) {
    return Column(
      // Anordnung der Elemente, entweder Horizontal oder Vertikal + die Startposition
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Container(
          // genaue Eckdaten von von allen seiten wie groß das feld sein soll
          padding: const EdgeInsets.fromLTRB(_hPad, 12.0, _hPad, 4.0),
          child: Text(
            "Kaffeestatus",
            // fontsize ist die Beschreibung der Schriftgröße
            style: Theme.of(context).primaryTextTheme.display2,
          ),
        ),
      ],
    );
  }
}
