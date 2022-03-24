package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FireStationRepository fireStationRepository;

    public MedicalRecordService() {
    }
    public Iterable<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordRepository.getAll();
    }
    public Boolean saveMedicalRocord(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);
    }
    public Boolean deleteMedicalRecord(String firstName, String lastName){
        List<MedicalRecord> allMedicalRecords = medicalRecordRepository.getAll();
        for(MedicalRecord medicalRecord : allMedicalRecords){
            if(medicalRecord.getFirstName().equals(firstName)
                    && medicalRecord.getLastName().equals(lastName)){
                return medicalRecordRepository.delete(medicalRecord);
            }
        }
        return false;
    }
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord){
        List<MedicalRecord> allMedicalRecords = medicalRecordRepository.getAll();

        for (int i = 0; i < allMedicalRecords.size(); i++){
            if(allMedicalRecords.get(i).getFirstName().equals(medicalRecord.getFirstName())
                    && allMedicalRecords.get(i).getLastName().equals(medicalRecord.getLastName())){
                return medicalRecordRepository.update(i,medicalRecord);
            }
        }
        return null;
    }
}
