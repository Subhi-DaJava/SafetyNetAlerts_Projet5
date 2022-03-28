package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.PhoneAlertDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
    @Autowired
    FireStationService fireStationService;

    //Api REST pour afficher toutes les FireStations
    @GetMapping(value = "/firestation")
    public ResponseEntity<Iterable<FireStation>> getAllFireStations(){
        Iterable<FireStation> allFireStations = fireStationService.getAllFireStations();
        if(allFireStations == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(allFireStations);
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
    ////Mettre à jour le numéro de la caserne d'une adresse
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
    //Trier les adresses par la caserne de pompiers
    @GetMapping(value = "/firestation/{station}")
    public ResponseEntity<Iterable<FireStation>> getByAddress(@PathVariable("station") String station){

        Iterable<FireStation> listAddressByStation = fireStationService.getAddressByStation(station);
        if (listAddressByStation != null){
            return ResponseEntity.ok().body(listAddressByStation);
        }else {
            return ResponseEntity.notFound().build();
        }
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
}

