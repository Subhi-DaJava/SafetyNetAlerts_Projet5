package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.FireDTO;
import com.safetynet.SafetyNetAlerts.dto.FireStationDTO;
import com.safetynet.SafetyNetAlerts.dto.FloodDTO;
import com.safetynet.SafetyNetAlerts.dto.PhoneAlertDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;
    //Get all fireStations
    @GetMapping(value = "/firestations")
    public ResponseEntity<Iterable<FireStation>> getAllFireStation(){
        Iterable<FireStation> allFireStations=  fireStationService.getAllFireStations();
        if (allFireStations != null){
            return ResponseEntity.ok(allFireStations);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //Ajouter d'un mapping caserne/adresse
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation f){
        Boolean isAdded = fireStationService.saveStation(f);
        if(isAdded){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //Supprimer le mapping d'une caserne par l'adresse comme un indicateur unique
    @DeleteMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable("address") String address){
        Boolean isDeleted = fireStationService.deleteStation(address);
        if(isDeleted){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    //Mettre à jour le numéro de la caserne d'une adresse
    @PutMapping(value = "/firestation/{address}/{number}")
    public ResponseEntity<Void> updateFireStation(@PathVariable("address") String address, @PathVariable("number") String number){
        FireStation isUpdated = fireStationService.updateStation(address, number);
        if (isUpdated != null){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/firestation/{stationNbr}")
    public ResponseEntity<Iterable<FireStation>> getByStationNumber(@PathVariable String stationNbr){
        Iterable<FireStation> fireStations = fireStationService.getAllAddressCoveredByOneFireStation(stationNbr);
        if(fireStations != null){
            return ResponseEntity.ok(fireStations);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //déplacer dans fireStationController
    @GetMapping(value = "/fire")
    public ResponseEntity<FireDTO> getInfosOfPersonsLiveSameAddress(@RequestParam(name="address") String address){
        FireDTO fireDto = fireStationService.getInfosOfPersonsLiveSameAddress(address);
        if(fireDto != null){
            return ResponseEntity.ok().body(fireDto);
        }else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<Iterable<PhoneAlertDTO>> getAllPhoneNumbersOfPersonsCoveredByOneFireStation(@RequestParam(name="firestation") String fireStationNumber){
        List<PhoneAlertDTO> phoneAlertDTOS = fireStationService.getAllPhoneNumbersOfPersonsCoveredByOneFireStation(fireStationNumber);
        if (phoneAlertDTOS != null){
            return ResponseEntity.ok().body(phoneAlertDTOS);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/firestation")
    public ResponseEntity<List<FireStationDTO>> getAllPersonsInfoFromAGivenFireStationNumber(@RequestParam(value = "stationNumber") String stationNumber){
        List<FireStationDTO> allPersonInfoByStation = fireStationService.getAllPersonsInfoFromAGivenFireStationNumber(stationNumber);
        if(allPersonInfoByStation != null){
            return ResponseEntity.ok(allPersonInfoByStation);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodDTO>> getPersonsByAddressFromListOfStation_Number(@RequestParam(value = "stations") List<String> station_numbers){
        List<FloodDTO> personsByAddressFromListOfStation_number = fireStationService.getPersonsByAddressFromListOfStation_Number(station_numbers);
        if(personsByAddressFromListOfStation_number != null){
            return ResponseEntity.ok().body(personsByAddressFromListOfStation_number);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}

