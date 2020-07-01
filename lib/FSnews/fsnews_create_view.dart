import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Networking/networking_functions.dart' as neNet;
import 'package:FIW_Studi_App/FSnews/fsnews_view.dart';

class FSnewsCreateView extends StatelessWidget {
  static final TextEditingController _title = new TextEditingController();
  static final TextEditingController _text = new TextEditingController();

  String get title => _title.text;
  String get text => _text.text;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      // beschreibt die Appbar
      appBar: AppBar(
        title: Text('FSnewsDetailView'),
      ),
      body: SafeArea(
        child: ListView(
          padding: EdgeInsets.symmetric(horizontal: 24.0),
          children: <Widget>[
            SizedBox(height: 40.0),
            Column(
              children: <Widget>[
                SizedBox(height: 16.0),
                Text(
                  "Create News",
                  style: TextStyle(fontSize: 26),
                ),
              ],
            ),
            SizedBox(height: 64.0),
            TextField(
              controller: _title,
              decoration: InputDecoration(
                hintText: "News-Title",
              ),
              // beschreibt ein Feld in das Text bis zu einer Länge von 64 zeichen eingegeben werden können
              keyboardType: TextInputType.multiline,
              style: TextStyle(fontSize: 20),
              maxLength: 64,
            ),
            Divider(height: 12),
            TextField(
              controller: _text,
              decoration: InputDecoration(hintText: "News-Text"),
              //beschreibt ein Feld in das Text in nicht endlicher Länge eingegeben werden kann
              keyboardType: TextInputType.multiline,
              maxLength: 256,
              maxLines: null,
            ),
            Divider(height: 16),
            RaisedButton(
              child: Text("Create"),
              onPressed: () {
                neNet.postNews(title, text);
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => FSnewsView()),
                );
              },
            )
          ],
        ),
      ),
    );
  }
}
