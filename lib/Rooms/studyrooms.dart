import 'dart:convert';
import 'package:FIW_Studi_App/Rooms/bookings.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'Model/booking.dart';
import 'Model/room.dart';

class Rooms extends StatefulWidget {
  @override
  _RoomsState createState() => _RoomsState();
}

class _RoomsState extends State<Rooms> {
  List<Room> roomDetails = [];
  List<Booking> bookingDetails = [];

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
                ]),
          ),
        ));
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Study Rooms',
          style: TextStyle(
            color: Colors.black,
          ),
        ),
      ),
      body: FutureBuilder(
        future: getRoomData(),
        builder: (context, projectSnap) {
          return ListView.builder(
            itemCount: roomDetails == null ? 0 : roomDetails.length,
            itemBuilder: (context, index) {
              return Card(
                child: ListTile(
                  title: Text(roomDetails[index].roomName + "\n",
                      textAlign: TextAlign.center,
                      style: TextStyle(fontSize: 25.0, color: Colors.black)),
                  subtitle: Text(
                    checkTime(index),
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 25.0,
                        color: _getColorFromHex(
                            roomDetails[index].roomStatusColor)),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }

  Future<List<Room>> getRoomData() async {
    http.Response response = await http.get(Uri.encodeFull(
        'https://apistaging.fiw.fhws.de/fiwis2/api/roomstatuses/'));
    var roomsData = jsonDecode(utf8.decode(response.bodyBytes));

    for (var bookval in roomsData) {
      Room room = Room(
          bookval['bookingUrl'],
          bookval['cns'],
          bookval['id'],
          bookval['personUrl'],
          bookval['href'],
          bookval['rel'],
          bookval['type'],
          bookval['roomName'],
          bookval['roomStatus'],
          bookval['roomStatusColor'],
          bookval['roomStatusDisplay'],
          bookval['self']);
      getBookingData(room);
      room.list = getBookingData(room) as List<Booking>;
      roomDetails.add(room);
    }
    return roomDetails;
  }

  Future<List<Booking>> getBookingData(Room room) async {
    http.Response response = await http.get(Uri.encodeFull(room.bookingUrl));
    var bookData = jsonDecode(utf8.decode(response.bodyBytes));

    for (var bookval in bookData) {
      Booking room = Booking(
        bookval['description'],
        bookval['endTime'],
        bookval['externalCalendarUid'],
        bookval['externalCalendarName'],
        bookval['id'],
        bookval['roomName'],
        bookval['self'],
        bookval['href'],
        bookval['rel'],
        bookval['type'],
        bookval['startTime'],
      );
      bookingDetails.add(room);
    }
    return bookingDetails;
  }

  Color _getColorFromHex(String hexColor) {
    hexColor = hexColor.replaceAll("#", "");
    if (hexColor.length == 6) {
      hexColor = "FF" + hexColor;
    }
    if (hexColor.length == 8) {
      return Color(int.parse("0x$hexColor"));
    }
  }

  String checkTime(int index) {
    List<Booking> booking = roomDetails[index].list;
    bool free;
    for (int i = 0; i < booking.length; i++) {
      var etime = DateTime.parse(booking[i].endTime);
      var stime = DateTime.parse(booking[i].startTime);
      if (DateTime.now().isBefore(etime) && DateTime.now().isAfter(stime)) {
        free = false;
        return "Room is not free";
      }
    }
    return "Room is free";
  }
}
