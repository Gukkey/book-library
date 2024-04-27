# Book Library

Java assignment for Olik by me. Took me around 3 to 4 days in my spare time. 

## Stack

Backend: Springboot
Database: Postgres

## How to deploy

1. Head to scripts folder
  `cd scripts`

2. Run the db_install.ps1 folder
  `.\db_init.ps1`

3. Come again to the main folder `cd ..\` and run using maven by
  `mvn spring-boot:run` or by maven jar `mvn package` and then run the jar using `java -jar ./target/book-library-0.0.1-SNAPSHOT.jar`

## ERD Diagram

![Untitled](https://github.com/Gukkey/book-library/assets/74915094/718da408-cb7e-42c9-8083-0b3fec539ab1)

#### Why rental does not have an id of it's own?

While having an id will let it to have the history of rentals I thought it will make having rentals on database more confusing when it comes to getting the rental details of a book, having a simpler table just without history of rentals will be more convenient to check whether a book is rented or not, because if a book is retrived the entry at the rental database will be deleted. So it also means easier pickup of getting the overdue rentals or books that are rented currently. 
