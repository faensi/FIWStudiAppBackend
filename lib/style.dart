import 'package:flutter/material.dart';
import 'helper_functions.dart';

// liste von Konstanten die es noch nicht in die App geschafft haben
// diese sollen feste designgrößen beschreiben

const LageTextSize = 26.0;
const MediumTextSize = 20.0;
const BodyTextSize = 16.0;

//Colors:
const studiAppPurple = const Color(0xff591452);
const studiAppGreen = const Color.fromRGBO(143, 212, 0, 1.0);
const studiAppGrey = const Color(0xFF757575);
const studiAppWhite = Colors.white;
const studiAppBlack = Colors.black;

const String FontNameDefault = 'Inconsolata';

const appBarTextStyle = TextStyle(
  fontFamily: FontNameDefault,
  fontWeight: FontWeight.w300,
  fontSize: MediumTextSize,
  color: studiAppWhite,
);

const TitleTextStyle = TextStyle(
  fontFamily: FontNameDefault,
  fontWeight: FontWeight.w300,
  fontSize: LageTextSize,
);

const Body1TextStyle = TextStyle(
  fontFamily: FontNameDefault,
  fontWeight: FontWeight.w300,
  fontSize: BodyTextSize,
  color: studiAppBlack,
);

final themeData = ThemeData(
  primarySwatch: HelperFunctions.createMaterialColor(studiAppGreen),
  primaryColor: studiAppGreen,
  accentColor: studiAppPurple,
  scaffoldBackgroundColor: studiAppWhite,
  primaryTextTheme: TextTheme(
    body1: TextStyle(
      fontSize: 16,
      color: studiAppBlack,
    ),
    display1: TextStyle(
      fontSize: 18,
      decoration: TextDecoration.underline,
      color: studiAppBlack,
    ),
    display2: TextStyle(
      fontSize: 36,
      color: studiAppBlack,
    ),
  ),
  appBarTheme: AppBarTheme(
    textTheme: TextTheme(
      title: TextStyle(
        fontSize: 22,
        fontWeight: FontWeight.bold,
        color: studiAppWhite,
      ),
    ),
  ),
  primaryIconTheme: IconThemeData(
    color: studiAppWhite,
  ),
);
