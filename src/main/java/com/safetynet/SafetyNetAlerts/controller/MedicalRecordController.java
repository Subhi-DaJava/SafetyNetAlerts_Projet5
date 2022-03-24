package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {
    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping(value = "/medicalRecord")
    public ResponseEntity<Iterable<MedicalRecord>> getAllMedicalRecord(){
        Iterable<MedicalRecord> allMedicalRecords = medicalRecordService.getAllMedicalRecords();
        if (allMedicalRecords == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(allMedicalRecords);
    }
}
