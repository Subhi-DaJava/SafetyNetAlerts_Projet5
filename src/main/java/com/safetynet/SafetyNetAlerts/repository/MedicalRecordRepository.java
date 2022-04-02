package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        System.out.println(medicalRecords.size()+" medicalRecors are in the DataBase.");
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
        //Inserts the specified element at the specified position in this list (optional operation).
        //Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
        return medicalRecords.set(i,medicalRecord);
    }
    //Get persons by same family name
    @Override
    public List<MedicalRecord> getByType(String lastName) {
        List<MedicalRecord> medicalRecordListByLastName = new ArrayList<>();
        if(repository == null)
            loadMedicalRecordsFromJasonFile();

        for (MedicalRecord medicalRecord : medicalRecords){
            if(medicalRecord.getLastName().equals(lastName)){
                medicalRecordListByLastName.add(medicalRecord);
            }
        }
        return medicalRecordListByLastName;
    }

}
