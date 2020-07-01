import 'dart:convert';
import 'dart:async';
import 'package:http/http.dart' as http;


class Note {
  int sem = 1; // 1-7
  String degreeProgram = "{programm}"; // bec, bin, bwi, mis
  int offset = 0; // Startpunkt
  int insertSize = 10; // Anzahl anzuzeigender Elemente
  var q = ""; // bestimmte Query, nur für eventuelle spätere Updates

// Kurzform des Konstruktors mit this.x=x
  Note(this.sem, this.degreeProgram, this.insertSize);

  Future<List<Course>> getCourseList() async {
// ========== Schritt 1 - REST-API ansprechen und Liste aller weiteren möglichen Routes ziehen ==========
    var url = 'https://fiwis.fiw.fhws.de/fiwis2/api';

    http.Response response = await http.get(Uri.encodeFull(url),
        headers: {"Accept": "*/*"} // universeller Content-Type
        );

    //if(response.statusCode==200) {
    var auswahl = response.headers; // :_InternalLinkedHashMap<String, String>

// ========== Schritt 2 - Aus dem Response die Classes-API bauen ==========

    // Response-Map nach "link" searchen und daraus Array bilden
    var links = auswahl["link"].split(","); // :List<String>
    var classesLink;

    for (int i = 0; i < links.length; i++) {
      if (links[i].contains("classes")) {
        // Suche nach Classes-API
        classesLink = links[i]; // Array-Eintrag speichern
        break;
      }
    }

    var classesApiArray = classesLink.split(
        ";"); // Die Header-Informationen für Classes separieren und einzelne Variablen extrahieren
    var acceptClasses = classesApiArray[2].split("=")[
        1]; // Ergebnis:   Accept: "application/vnd.fhws-class.classview+json"
    var urlClasses = classesApiArray[0]
        .replaceFirst(RegExp('<'), '')
        .replaceFirst(RegExp('>'), '')
        .split("?")[0]; // url für die API Classes herausdestillieren

// ========== Schritt 3 - Attribute für das Filtern der Anfrage vorbereiten ==========

    var classesFilter = getNextUrl(this.offset, this.sem, this.degreeProgram, this.insertSize);
    String ergebnisUrl = urlClasses + classesFilter;


    http.Response responseClasses = await http.get(
      Uri.encodeFull(ergebnisUrl),
      headers: {
        "Accept": "*/*"
      }, // Anmerkung: automatisches Ziehen des MIME-Types generiert einen 400 Fehler, daher "Notlösung"
    );

    var arrayResult;

    if (responseClasses.statusCode == 200) {
      arrayResult = responseClasses.bodyBytes;
    } else {
      print("Unbekanter Fehler" + responseClasses.statusCode.toString());
    }


    var jsonResult;
    bool empty = false;
    do{
      // raise offset
      this.offset += insertSize;
      ergebnisUrl = urlClasses + getNextUrl(this.offset, this.sem, this.degreeProgram, this.insertSize);
      http.Response responseClasses2 = await http.get(
        Uri.encodeFull(ergebnisUrl),
        headers: {
          "Accept": ""
        }, // Anmerkung: automatisches Ziehen des MIME-Types generiert einen 400 Fehler, daher "Notlösung"
      );
      if(responseClasses2.statusCode == 200) {
        if ( responseClasses2.bodyBytes.length < 4) empty = true;
        else {
          if(jsonResult==null) jsonResult = jsonDecode(utf8.decode(arrayResult));
          else jsonResult += jsonDecode(utf8.decode(arrayResult));
        }
        }
      else{
        print("Unbekanter Fehler" + responseClasses.statusCode.toString());
      }
    }while(!empty);



    List<List<String>> resultList = new List(jsonResult.length);
  bool doubled = false;
    for (int i = 0; i < jsonResult.length; i++) {
      //Abfrage ob einmaliges Ereignis (ID = 90...) , dann überspringen
      //Abfrage um sicherzustellen, dass nur Kurse aus dem Studiengang angezeigt werden (Any wird ausgefiltert)
      if (jsonResult[i]["id"].toString().codeUnitAt(0).toString() == '57' &&
              jsonResult[i]["id"].toString().codeUnitAt(1).toString() == '48' ||
          jsonResult[i]["studyGroupsToShow"].contains(degreeProgram.toUpperCase() + sem.toString()) == false) {
        continue;
      }

      // Anmerkung: über    jsonResult[i]["name"]["href"] Zugriff auf URL für Raum & Zeit

      if(i==0){
      }else{
        for(int k=0; k<i;k++)
        if(resultList[k]!=null && resultList[k].contains(jsonResult[i]["name"])){
            print("triggered");
          doubled =true;
        }}

      if(doubled){
        doubled=false;
        continue;
      }

      resultList[i] = [
        jsonResult[i]["name"],
        jsonResult[i]["lecturerNamesToShow"]
      ];

      // Anmerkung: mit jsonResult[i]["icalUrl"]["href"] bekomme ich die URL zu einer ical.Datei
    }

    final filteredList = _convertToListWithLectures(resultList);

    if (filteredList.isEmpty) return List<Course>();
    return filteredList;
  }

  static List<Course> _convertToListWithLectures(List<List<String>> list) {
    List<Course> listWithValues = [];
    for (final li in list) {
      if (li == null || li[0] == null || li[1] == null) continue;
      final lecture = Course(li[0], li[1]);
      listWithValues.add(lecture);
    }
    return listWithValues;
  }

  static String getNextUrl(int offset, int sem, String degreeProgram, int insertSize  ){
  var classesFilter = "?q&semester=" +
      sem.toString() +
      "&program=" +
      degreeProgram +
      "&offset=" +
      offset.toString() +
      "&size=" +
      insertSize.toString();
  return classesFilter;
  }

}

class Course {
  String title;
  String lecturers;

  Course(this.title, this.lecturers);
}
