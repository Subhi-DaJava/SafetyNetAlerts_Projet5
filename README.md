SafetyNet Alerts
L'application SafetyNet Alerts. Developpe avec spring boot. Le but de cette application est d'envoyer des informations aux systèmes de services d'urgence.

Prérequis
Java 11
Maven 3.6.2

Démarrer l'app
Run `SafetyNetAlertsApplication`
Linge de commande : `mvn package`
puis
Avec fichier jar : `java -jar target/SafetyNetAlerts-Alerts-0.0.1-SNAPSHOT.jar`
Ou
`mvn spring-boot:run`

Tester l'app
Ligne de commande : `mvn verify`
Générer les rapports de test jacoco et surefire : `mvn site`
Importer les collections(dans le dossier TestsPostman) dans `Postman` puis tester les endpoints et les URLs.

Actuator
http://localhost:8080/actuator/info
http://localhost:8080/actuator/httptrace
http://localhost:8080/actuator/metrics

Les Endpoints : opération CRUD

Person endpoints
GET http://localhost:8080/person : get All Persons
GET http://localhost:8080/person/address : get all Persons by address
POST http://localhost:8080/person/ : save a Person
http://localhost:8080/person/firstName/LastName : Update a Person
DELETE http://localhost:8080/person/firstName/LastName: delete a Person

FireStation endpoints
GET http://localhost:8080/firestations : get All FireStations
GET http://localhost:8080/firestation/statonNbr : get all FireStations with the station number
POST http://localhost:8080/firestation : save a FireStation
PUT http://localhost:8080/firestation/address : update a FireStation
DELETE http://localhost:8080/firestation/address : delete a FireStation

Medical record endpoints
GET http://localhost:8080/medicalRecord : get All MedicalRecords
GET http://localhost:8080/medicalRecord/lastName : get all MedicalRecords with the last name
POST http://localhost:8080/medicalRecord : save a MedicalRecord
PUT http://localhost:8080/medicalRecord/firstName/lastName : update a MedicalRecord
DELETE http://localhost:8080/medicalRecord/firstName/lastName : delete a MedicalRecord

Alerts URLS

GET http://localhost:8080/firestation?stationNumber =`<station_number>` : Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.

GET http://localhost:8080/childAlert?address =`<address>` : Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.

GET http://localhost:8080/phoneAlert?firestation =`<firestation_number>` : Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.

GET http://localhost:8080/fire?address =`<address>` : Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne de pompiers la desservante.

GET http://localhost:8080/flood/stations?stations =`<a list of station_numbers>` : Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les personnes par adresse.

GET http://localhost:8080/personInfo?firstName=<firstName>&lastName =`<lastName>` : Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments, posologie, allergies) de chaque habitant.

GET http://localhost:8080/communityEmail?city =`<city>` : Cette url doit retourner les adresses mail de tous les habitants de la ville.