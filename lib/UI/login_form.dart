import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;
import 'package:FIW_Studi_App/Networking/authorization.dart' as auth;

class LoginForm extends StatelessWidget {
  static final TextEditingController _user = new TextEditingController();
  static final TextEditingController _pass = new TextEditingController();

  String get username => _user.text;
  String get password => _pass.text;

  @override
  Widget build(BuildContext login) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text('Login'),
      ),
      body: SafeArea(
        child: ListView(
          padding: EdgeInsets.symmetric(horizontal: 24.0),
          children: <Widget>[
            SizedBox(height: 80.0),
            Column(
              children: <Widget>[
                Image.asset(''),
                SizedBox(height: 16.0),
                Text(
                  'Admin Login für Fachschaftler',
                  style: TextStyle(fontSize: 20),
                ),
              ],
            ),
            SizedBox(height: 120),
            TextField(
              autocorrect: false,
              controller: _user,
              decoration: InputDecoration(
                filled: true,
                labelText: 'k-nummer',
              ),
            ),
            SizedBox(height: 12.0),
            TextField(
              autocorrect: false,
              controller: _pass,
              decoration: InputDecoration(
                filled: true,
                labelText: 'Passwort',
              ),
              obscureText: true,
            ),
            ButtonBar(
              children: <Widget>[
                FlatButton(
                  child: Text('Cancel'),
                  onPressed: () {
                    Navigator.pop(login);
                  },
                ),
                RaisedButton(
                  child: Text('Next'),
                  onPressed: () {
                    globals.kNumber = this.username;
                    globals.password = this.password;
                    auth.login();
                    Navigator.pop(login);
                  },
                )
              ],
            ),
          ],
        ),
      ),
    );
  }
}
