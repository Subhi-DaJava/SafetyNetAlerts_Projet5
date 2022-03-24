package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalRecordRepository implements CRUD_Method<MedicalRecord>{
    @Autowired
    private ReadFromJason_DAO readFromJason_dao;
    private Repository repository;
    private List<MedicalRecord> medicalRecords;

    public MedicalRecordRepository() {
    }

    private void loadMedicalRecordsFromJasonFile(){
        repository = readFromJason_dao.readFromJsonFile();
        medicalRecords = repository.getMedicalrecords();
    }

    @Override
    public List<MedicalRecord> getAll() {
        if (repository == null)
            loadMedicalRecordsFromJasonFile();
        return medicalRecords;
    }

    @Override
    public Boolean save(MedicalRecord medicalRecord) {
        if ((repository == null))
            loadMedicalRecordsFromJasonFile();
        return medicalRecords.add(medicalRecord);
    }

    @Override
    public Boolean delete(MedicalRecord medicalRecord) {
        if (repository == null)
            loadMedicalRecordsFromJasonFile();
        return medicalRecords.remove(medicalRecord);
    }

    @Override
    public MedicalRecord update(int i, MedicalRecord medicalRecord) {
        if(repository == null)
            loadMedicalRecordsFromJasonFile();
        return medicalRecords.set(i,medicalRecord);
    }
}
