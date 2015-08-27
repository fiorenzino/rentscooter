Application to rent scooter.
ejb3 JSR-299 (web beans) JSF Facelets SmartFlower

http://code.google.com/p/smartflower/

This is a little application for managing rent scooter (for a little motorcycle dealership).
The operator must be able to create a user registry, scooter registry, create contract for rent.
The customer can rent with a single contract rent a scooter for same days and another scooter for authers days.
(1 contract - multi day scooter reservation)

Each scooter has a daily rate that varies based on time of booking.
And finally i must create a pdf for print the contract.


The system is composed of a planing monthly show on a daily free and booked scooters.


---


To generate pdf i want use:
  * flying-saucer-renderer https://xhtmlrenderer.dev.java.net/ + itext http://www.lowagie.com/iText/

(read this article: http://today.java.net/pub/a/today/2006/10/31/combine-facelets-and-flying-saucer-renderer.html)