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
