//variables for login
String kNumber;
String password;
bool isLoggedIn = false;

//variables for Note Class
int semester = 0;
String degreeProgramm = "";
int offset = 0;
int insertSize = 10;

//variables for Button from API/coffee-machine/getState
Map<String, dynamic> cData;
String cState;
DateTime cLastTime;
String cUserName;

//time functions:

//news:
List<dynamic> newsData;

//coffee:
String cofImageAdr = "Images/coffee_full.png";
String cofStatus = "Der Status wird geladen";
