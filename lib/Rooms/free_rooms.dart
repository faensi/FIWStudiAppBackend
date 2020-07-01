import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class FreeRooms extends StatefulWidget {
  @override
  FreeRoomsState createState() => new FreeRoomsState();
}

class FreeRoomsState extends State<FreeRooms> {
  List data;
  var url = "https://fiwis.fiw.fhws.de/fiwis2/api";
  bool free;

  Future _getData() async {
    var urlResponse =
        await http.get(Uri.encodeFull(url), headers: {"Accept": "*/*"});
    var urlHeader = urlResponse.headers;
    var links = urlHeader["link"].split(",");
    var roomLink;
    var roomUrl;
    for (int i = 0; i < links.length; i++) {
      if (links[i].contains("freerooms")) {
        roomLink = links[i];
        break;
      }
    }
    var roomLinkList = roomLink.split(";");

    var acceptHeader = roomLinkList[2].replaceAll(r'\', '').split("=")[1];
    //\"application/vnd.fhws-freeroom.simplefreeroom+json\"
    roomUrl = roomLinkList[0]
        .replaceFirst(RegExp('<'), '')
        .replaceFirst(RegExp('>'), '')
        .split("?")[0];

    var response =
        await http.get(Uri.encodeFull(roomUrl), headers: {"Accept": "*/*"});

    this.setState(() {
      data = json.decode(response.body);
    });

    return data;
  }

  String _checkTime(int index, var data) {
    List timeList = new List(data.length);
    for (int i = 0; i < data.length; i++) {
      timeList[i] = DateTime.parse(data[i]["endTime"]);
    }

    var now = DateTime.now();
    if (now.isBefore(timeList[index])) {
      free = true;
      return "Room is free";
    } else {
      free = false;
      return "Room is not free";
    }
  }

  Color _getColor() {
    if (free == true) {
      return Colors.lightGreen;
    } else {
      return Colors.red;
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Room Statuses"),
      ),
      body: FutureBuilder(
        future: _getData(),
        builder: (context, projectSnap) {
          return ListView.builder(
            itemCount: data == null ? 0 : data.length,
            itemBuilder: (context, index) {
              return Card(
                child: ListTile(
                  title: Text(data[index]["name"] + "\n",
                      textAlign: TextAlign.center,
                      style: TextStyle(fontSize: 25.0, color: Colors.black)),
                  subtitle: Text(_checkTime(index, data),
                      //data[index]["startTime"] + " - " + data[index]["endTime"],
                      textAlign: TextAlign.center,
                      style: TextStyle(fontSize: 25.0, color: _getColor())),
                ),
              );
            },
          );
        },
      ),
    );
  }

  @override
  void initState() {
    super.initState();
    this._getData();
  }
}
