package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.ChildAlertDTO;
import com.safetynet.SafetyNetAlerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class MedicalRecordController {
    private static final Logger LOGGER= LogManager.getLogger(MedicalRecordController.class);
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping(value = "/medicalRecord")
    public ResponseEntity<Iterable<MedicalRecord>> getAllMedicalRecord(){
        LOGGER.debug("The endpoint(GET /medicalRecord) starts here");
        Iterable<MedicalRecord> allMedicalRecords = medicalRecordService.getAllMedicalRecords();
        if (allMedicalRecords != null) {
            LOGGER.info("The endpoint(GET /medicalRecord) get all medicalRecords successfully");
            return ResponseEntity.ok().body(allMedicalRecords);
        } else {
            LOGGER.error("No medicalRecord in the DataBase or no success with GET /medicalRecord");
           throw  new MedicalRecordNotFoundException("There is no person medicalRecord in dataJson or data file not found");
        }
    }

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        Boolean isSaved = medicalRecordService.saveMedicalRecord(medicalRecord);
        if(isSaved){
            LOGGER.info("Save a "+medicalRecord+" is successful with POST /medicalRecord");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(isSaved)
                    .toUri();
            return ResponseEntity.created(location).build();
        }else {
            LOGGER.error("Could not save a "+medicalRecord+" with POST /medicalRecord");
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> updateMedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @RequestBody MedicalRecord medicalRecord){
        LOGGER.debug("The endpoint(PUT /medicalRecord) starts here");
        MedicalRecord updateMedicalRecord = medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
        if (updateMedicalRecord != null){
            LOGGER.info("Update a medicalRecord "+firstName+" "+lastName+" is successful with PUT /medicalRecord");
            return ResponseEntity.ok().build();
        }else {
            LOGGER.error("Could not update a medicalRecord "+firstName+" "+lastName+" with PUT /medicalRecord");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        LOGGER.debug("The endpoint(DELETE /medicalRecord/firstName/lastName) starts here");
        Boolean isDeleted = medicalRecordService.deleteMedicalRecord(firstName,lastName);
        if(isDeleted){
            LOGGER.info("Delete a medicalRecord, firstName :" +firstName+", and lastName :"+lastName+" is successfully deleted from DELETE /medicalRecord/firstName/lastName");
            return ResponseEntity.ok().build();
        }else {
            LOGGER.error("Could not delete a medicalRecord, firstName :" +firstName+", and lastName :"+lastName+" from DELETE /medicalRecord/firstName/lastName");
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/medicalRecord/{lastName}")
    public ResponseEntity<Iterable<MedicalRecord>> getMedicalRecordsBySameFamilyName(@PathVariable("lastName") String lastName){
        LOGGER.debug("The endpoint(GET /medicalRecord/lastName) starts here");
        List<MedicalRecord> medicalRecordsBySameFamilyName = medicalRecordService.getMedicalRecordsBySameFamilyName(lastName);
        if (!medicalRecordsBySameFamilyName.isEmpty()){
            LOGGER.info("The endpoint(GET /medicalRecord/lastName) get successfully all medicalRecords of same family name");
            return ResponseEntity.ok().body(medicalRecordsBySameFamilyName);
        }else{
            LOGGER.error("Could not get medicalRecords of same family name :"+lastName+" from GET /medicalRecord/lastName");
            throw new MedicalRecordNotFoundException("Any MedicalRecord dose not associate to this lastName"+lastName);
        }
    }
    @GetMapping(value = "/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildList(@RequestParam("address") String address){
        LOGGER.debug("The endpoint url(GET /childAlert?address=<address> starts here");
        ChildAlertDTO listChildByAddress = medicalRecordService.getChildAndHisFamilyMemberByAGivenAddress(address);
        if(listChildByAddress != null) {
            LOGGER.info("Get the child infos with the address "+address+" is successful from GET /childAlert?address=address");
            return ResponseEntity.ok().body(listChildByAddress);
        } else {
            LOGGER.error("Could not get the child info with the address "+address+" from GET /childAlert?address=address");
            return ResponseEntity.notFound().build();
        }
    }
}
