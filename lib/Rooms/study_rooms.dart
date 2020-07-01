import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class Rooms extends StatefulWidget {
  @override
  _RoomsState createState() => _RoomsState();
}

class _RoomsState extends State<Rooms> {
  // ignore: non_constant_identifier_names
  Material MyItems(IconData icon, String heading, int color) {
    return Material(
      color: Color(0xFFAED581),
      elevation: 18.0,
      shadowColor: Color(0xFF9E9E9E),
      borderRadius: BorderRadius.circular(18.0),
      child: Center(
        child: Padding(
          padding: const EdgeInsets.all(9.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  //text
                  Padding(
                    padding: const EdgeInsets.all(9.0),
                    child: Text(
                      heading,
                      style: TextStyle(
                        color: new Color(color),
                        fontSize: 24.0,
                      ),
                    ),
                  ),

                  //Icon
                  Material(
                    color: new Color(color),
                    borderRadius: BorderRadius.circular(18.0),
                    child: Padding(
                      padding: const EdgeInsets.all(18.0),
                      child: Icon(
                        icon,
                        color: Colors.white,
                        size: 32.0,
                      ),
                    ),
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Study Rooms',
          style: TextStyle(
            color: Colors.black,
          ),
        ),
      ),
      body: StaggeredGridView.count(
        crossAxisCount: 1,
        mainAxisSpacing: 18.0,
        padding: EdgeInsets.symmetric(horizontal: 18.0, vertical: 9.0),
        children: <Widget>[
          MyItems(Icons.local_library, "I.2.15", 0xFF004D40),
          MyItems(Icons.import_contacts, "I.2.24", 0xFF004D40),
          MyItems(Icons.laptop, "I.3.19", 0xFF004D40),
          MyItems(Icons.settings_input_hdmi, "I.3.20", 0xFF004D40),
          MyItems(Icons.group, "I.3.21", 0xFF004D40),
          MyItems(Icons.local_library, "I.3.24", 0xFF004D40),
        ],
        staggeredTiles: [
          StaggeredTile.extent(1, 180.0),
          StaggeredTile.extent(1, 180.0),
          StaggeredTile.extent(1, 180.0),
          StaggeredTile.extent(1, 180.0),
          StaggeredTile.extent(1, 180.0),
          StaggeredTile.extent(1, 180.0),
        ],
      ),
    );
  }
}
