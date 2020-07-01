import 'package:FIW_Studi_App/style.dart';
import 'package:flutter/material.dart';
import 'package:FIW_Studi_App/Scheduler/schedule_detail.dart';
import 'package:FIW_Studi_App/globals.dart' as globals;

class ScheduleSemester extends StatelessWidget {
  final List<int> semesterList = [1, 2, 3, 4, 5, 6, 7];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Wähle deinen Semester aus"),
      ),
      body: _buildSemesterListView(),
    );
  }

  Widget _buildSemesterListView() {
    return ListView.builder(
      itemCount: 7,
      itemBuilder: (BuildContext context, int index) {
        return Container(
          child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                GestureDetector(
                  onTap: () {
                    globals.semester = semesterList[index];
                    Navigator.push(
                      context, // Hier Semestervariable abändern.
                      MaterialPageRoute(
                        builder: (context) => ScheduleDetail(),
                      ),
                    );
                  },
                  child: Card(
                    child: Container(
                      padding: EdgeInsets.all(15.0),
                      child: Row(
                        children: <Widget>[
                          Text(semesterList[index].toString() + ". Semester",
                              style: TextStyle(
                                  fontSize: 18.0, color: studiAppBlack)),
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
