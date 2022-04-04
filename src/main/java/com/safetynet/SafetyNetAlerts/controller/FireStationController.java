package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.FireDTO;
import com.safetynet.SafetyNetAlerts.dto.FireStationDTO;
import com.safetynet.SafetyNetAlerts.dto.FloodDTO;
import com.safetynet.SafetyNetAlerts.dto.PhoneAlertDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class FireStationController {
    private static final Logger LOGGER = LogManager.getLogger(FireStationController.class);
    @Autowired
    private FireStationService fireStationService;
    //Get all fireStations
    @GetMapping(value = "/firestations")
    public ResponseEntity<Iterable<FireStation>> getAllFireStation(){
        LOGGER.debug("The endpoint(GET /firestations) starts here");
        Iterable<FireStation> allFireStations=  fireStationService.getAllFireStations();
        if (allFireStations != null){
            LOGGER.info("The endpoint(GET /firestations) get all fireStations successfully");
            return ResponseEntity.ok(allFireStations);
        }else {
            LOGGER.error("No fireStation in the DataBase or no success with GET /firestations");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Ajouter d'un mapping caserne/adresse
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation f){
        Boolean isAdded = fireStationService.saveStation(f);
        if(isAdded){
            LOGGER.info("Save a medicalRecord is successful with POST /medicalRecord");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(isAdded)
                    .toUri();
            return ResponseEntity.created(location).build();
        }else {
            LOGGER.error("Could not save a fireStation with POST /firestation");
            return ResponseEntity.notFound().build();
        }
    }
    //Supprimer le mapping d'une caserne par l'adresse comme un indicateur unique
    @DeleteMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable("address") String address){
        LOGGER.debug("The endpoint(DELETE /firestation/address) starts here");
        Boolean isDeleted = fireStationService.deleteStation(address);
        if(isDeleted){
            LOGGER.info("Delete a fireStation with the address "+address+" is successfully deleted from DELETE /firestation/address");
            return ResponseEntity.ok().build();
        }else {
            LOGGER.error("Could not delete a fireStation with the "+address+" from DELETE /firestation/address");
            return ResponseEntity.notFound().build();
        }

    }
    //Mettre à jour le numéro de la caserne d'une adresse
    @PutMapping(value = "/firestation/{address}/{number}")
    public ResponseEntity<Void> updateFireStation(@PathVariable("address") String address, @PathVariable("number") String number){
        LOGGER.debug("The endpoint(PUT /firestation/address/number) starts here");
        FireStation isUpdated = fireStationService.updateStation(address, number);
        if (isUpdated != null){
            LOGGER.info("Update a fireStation : address: "+address+" et number of station : "+number+" is successful with PUT /firestation/address/number");
            return ResponseEntity.ok().build();
        }
        else {
            LOGGER.error("Could not update a fireStation : "+address+" et"+number+" with PUT /firestation/address/number");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/firestation/{stationNbr}")
    public ResponseEntity<Iterable<FireStation>> getByStationNumber(@PathVariable String stationNbr){
        LOGGER.debug("The endpoint(GET /firestation/stationNbr) starts here");
        Iterable<FireStation> fireStations = fireStationService.getAllAddressCoveredByOneFireStation(stationNbr);
        if(fireStations != null){
            LOGGER.info("Get the fireStations covered by station number : "+stationNbr+" is successful with GET /firestation/stationNbr");
            return ResponseEntity.ok(fireStations);
        }else {
            LOGGER.error("Could not get  the fireStations by station number : "+stationNbr+" with GET /firestation/stationNbr");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/fire")
    public ResponseEntity<FireDTO> getInfosOfPersonsLiveSameAddress(@RequestParam(name="address") String address){
        LOGGER.debug("The endpoint url(GET /fire?address=<address>) starts here");
        FireDTO fireDto = fireStationService.getInfosOfPersonsLiveSameAddress(address);
        if(fireDto != null){
            LOGGER.info("Get a list of family live at a address "+address+" covered by a fireStation, with GET /fire?address=<address>");
            return ResponseEntity.ok().body(fireDto);
        }else
            LOGGER.error("Could not get the list by the address : "+address+"with GET /fire?address=<address>");
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<Iterable<PhoneAlertDTO>> getAllPhoneNumbersOfPersonsCoveredByOneFireStation(@RequestParam(name="firestation") String fireStationNumber){
        LOGGER.debug("The endpoint url(GET /phoneAlert?firestation=<firestation_number) starts here");
        List<PhoneAlertDTO> phoneAlertDTOS = fireStationService.getAllPhoneNumbersOfPersonsCoveredByOneFireStation(fireStationNumber);
        if (phoneAlertDTOS != null){
            LOGGER.info("Get a list of phone number when the fireStationNumber :"+fireStationNumber+", with GET /phoneAlert?firestation=<firestation_number");
            return ResponseEntity.ok().body(phoneAlertDTOS);
        }else {
            LOGGER.error("Could not get the list phone number with : "+fireStationNumber+" with GET /fire?address=<address>");
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/firestation")
    public ResponseEntity<List<FireStationDTO>> getAllPersonsInfoFromAGivenFireStationNumber(@RequestParam(value = "stationNumber") String stationNumber){
        LOGGER.debug("The endpoint url(GET /firestation?stationNumber=<station_number>) starts here");
        List<FireStationDTO> allPersonInfoByStation = fireStationService.getAllPersonsInfoFromAGivenFireStationNumber(stationNumber);
        if(allPersonInfoByStation != null){
            LOGGER.info("Get a list of persons covered by fireStation :"+stationNumber+", with GET /firestation?stationNumber=<station_number>");
            return ResponseEntity.ok().body(allPersonInfoByStation);
        }else {
            LOGGER.error("Could not get the list of persons with given"+stationNumber+" with GET /firestation?stationNumber=<station_number>");
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodDTO>> getPersonsByAddressFromListOfStation_Number(@RequestParam(value = "stations") List<String> station_numbers){
        LOGGER.debug("The endpoint url(GET /flood?stations=<a list of station_numbers>) starts here");
        List<FloodDTO> personsByAddressFromListOfStation_number = fireStationService.getPersonsByAddressFromListOfStation_Number(station_numbers);
        if(personsByAddressFromListOfStation_number != null){
            LOGGER.info("Get a list of persons covered by fireStation given list of "+station_numbers+", with GET /flood?stations=<a list of station_numbers>");
            return ResponseEntity.ok().body(personsByAddressFromListOfStation_number);
        }else {
            LOGGER.error("Could not get the list of persons with given list of staion_numbers "+station_numbers+" with GET /flood?stations=<a list of station_numbers>");
            return ResponseEntity.notFound().build();
        }
    }

}

