import 'booking.dart';

class Room {
  String bookingUrl;
  String cns;
  String id;
  String personUrl;
  String href;
  String rel;
  String type;
  String roomName;
  String roomStatus;
  String roomStatusColor;
  String roomStatusDisplay;
  String self;
  List<Booking> list;

  Room(
      this.bookingUrl,
      this.cns,
      this.id,
      this.personUrl,
      this.href,
      this.rel,
      this.type,
      this.roomName,
      this.roomStatus,
      this.roomStatusColor,
      this.roomStatusDisplay,
      this.self);
}
