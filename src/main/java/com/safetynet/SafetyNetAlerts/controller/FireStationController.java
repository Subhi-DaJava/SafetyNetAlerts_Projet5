package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

