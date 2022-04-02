package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.ChildAlertDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping(value = "/medicalRecord")
    public ResponseEntity<Iterable<MedicalRecord>> getAllMedicalRecord(){
        Iterable<MedicalRecord> allMedicalRecords = medicalRecordService.getAllMedicalRecords();
        if (allMedicalRecords == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(allMedicalRecords);
    }

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        Boolean isSaved = medicalRecordService.saveMedicalRecord(medicalRecord);

        if(isSaved){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value = "/medicalRecord")
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        MedicalRecord updateMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
        if (updateMedicalRecord != null){
            return ResponseEntity.ok().build();
        }else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        Boolean isDeleted = medicalRecordService.deleteMedicalRecord(firstName,lastName);
        if(isDeleted){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/medicalRecord/{lastName}")
    public ResponseEntity<Iterable<MedicalRecord>> getMedicalRecordsBySameFamilyName(@PathVariable("lastName") String lastName){
        List<MedicalRecord> medicalRecordsBySameFamilyName = medicalRecordService.getMedicalRecordsBySameFamilyName(lastName);
        if (medicalRecordsBySameFamilyName != null){
            return ResponseEntity.ok(medicalRecordsBySameFamilyName);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildList(@RequestParam("address") String address){
        ChildAlertDTO listChildByAddress = medicalRecordService.getChildAndHisFamilyMemberByGivenAddress(address);
        if(listChildByAddress != null){
            return ResponseEntity.ok().body(listChildByAddress);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
