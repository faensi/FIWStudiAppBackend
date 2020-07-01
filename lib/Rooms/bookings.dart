import 'package:flutter/material.dart';

import 'Model/booking.dart';

class RadioWidgetDemo extends StatefulWidget {
  static Future<List<Booking>> bookings;
  static List<Booking> bookingDetails;

  RadioWidgetDemo(
      Future<List<Booking>> bookings, List<Booking> bookingDetails) {
    RadioWidgetDemo.bookings = bookings;
    RadioWidgetDemo.bookingDetails = bookingDetails;
  }

  final String title = "Today's bookings";

  @override
  RadioWidgetDemoState createState() => RadioWidgetDemoState();
}

class RadioWidgetDemoState extends State<RadioWidgetDemo> {
  int selectedRadioTile;

  @override
  void initState() {
    super.initState();
    selectedRadioTile = 0;
  }

  setSelectedRadioTile(int val) {
    setState(() {
      selectedRadioTile = val;
    });
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
      body: FutureBuilder(
        future: RadioWidgetDemo.bookings,
        builder: (context, projectSnap) {
          return ListView.builder(
            itemCount: RadioWidgetDemo.bookingDetails == null
                ? 0
                : RadioWidgetDemo.bookingDetails.length,
            itemBuilder: (context, index) {
              return Card(
                  child: ListTile(
                      title: Text(
                          RadioWidgetDemo.bookingDetails[index].description +
                              "\n",
                          textAlign: TextAlign.center,
                          style:
                              TextStyle(fontSize: 25.0, color: Colors.black)),
                      subtitle: Text(
                          RadioWidgetDemo.bookingDetails[index].description)));
            },
          );
        },
      ),
    );
  }
}
