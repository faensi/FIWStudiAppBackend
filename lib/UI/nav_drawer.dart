import 'package:FIW_Studi_App/FSnews/fsnews_view.dart';
import 'package:FIW_Studi_App/Coffee/coffee_view.dart';
import 'package:FIW_Studi_App/Rooms/rooms1_card.dart';
import 'package:FIW_Studi_App/Rooms/rooms2_card.dart';
import 'package:FIW_Studi_App/Scheduler/schedule_view.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/UI/login_form.dart';
import 'package:FIW_Studi_App/Networking/networking_functions.dart' as net;
import '../globals.dart' as globals;
import '../style.dart';
import 'package:FIW_Studi_App/Networking/authorization.dart' as auth;

class NavDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      // setzt die Elemente im Children in eine Liste
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          //fslogin --> true
          // beschreibt den Drawer Kopf
          UserAccountsDrawerHeader(
            accountName: globals.isLoggedIn
                ? Text("Fachschaftler: " + globals.kNumber)
                : null,
            accountEmail: null,
            //beschreibt das Bild im Drawer
            currentAccountPicture: globals.isLoggedIn
                ? CircleAvatar(
                    backgroundColor: studiAppPurple,
                    child: Text("FSI"),
                  )
                : null,
          ),
          //beschreibt ein Element der ListView
          ListTile(
            title: Text("Kaffeemaschine"),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => CoffeeView()),
              );
            },
          ),
          ListTile(
            title: Text("FS-News"),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => FSnewsView()),
              );
            },
          ),
          ListTile(
            title: Text("Vorlesungsssplaner"),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ScheduleView()),
              );
            },
          ),
          ListTile(
            title: Text("Kaim VP"),
            onTap: () {
              net.launchURL(context);
            },
          ),
          ListTile(
            title: Text("Rooms1"),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => Rooms1Card()),
              );
            },
          ),
          ListTile(
            title: Text("Rooms2"),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => Rooms2Card()),
              );
            },
          ),
          Divider(),
          globals.isLoggedIn
              ? ListTile(
                  title: Text("Logout"),
                  onTap: () {
                    auth.logout();
                    Navigator.pop(context);
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => LoginForm()),
                    );
                  },
                )
              : ListTile(
                  title: Text("Login"),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => LoginForm()),
                    );
                  },
                ),
          // fügt eine Leerzeile ein
          Divider(),
          ListTile(
            title: Text("Close"),
            trailing: Icon(Icons.close),
            onTap: () {
              Navigator.pop(context);
            },
          ),
        ],
      ),
    );
  }
}
