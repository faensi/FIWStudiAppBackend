import 'package:FIW_Studi_App/Scheduler/schedule_semester.dart';
import 'package:FIW_Studi_App/style.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;

class ScheduleView extends StatelessWidget {
  final List<String> courseOfStudies = ["bec", "bin", "bwi", "mis"];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Wähle deinen Studiengang aus"),
      ),
      body: _buildSemesterListView(),
    );
  }

  Widget _buildSemesterListView() {
    return ListView.builder(
      itemCount: courseOfStudies.length,
      itemBuilder: (BuildContext context, int index) {
        return Container(
          child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                GestureDetector(
                  onTap: () {
                    globals.degreeProgramm = courseOfStudies[index];
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => ScheduleSemester(),
                      ),
                    );
                  },
                  child: Card(
                    child: Container(
                      padding: EdgeInsets.all(15.0),
                      child: Row(
                        children: <Widget>[
                          Text(
                            courseOfStudies[index],
                            style:
                                TextStyle(fontSize: 18.0, color: studiAppBlack),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
