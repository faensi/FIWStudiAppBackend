import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Networking/networking_functions.dart' as net;

class KaimWVCard extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _KaimWVState();
  }
}

class _KaimWVState extends State<KaimWVCard> {
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        net.launchURL(context);
      },
      child: Card(
        clipBehavior: Clip.antiAlias,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            AspectRatio(
              aspectRatio: 18.0 / 11.0,
              child: Image.asset('Images/unicorn.png'),
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
                  Center(child: Text('KaimVP')),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
