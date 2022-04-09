package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.ChildAlertDTO;
import com.safetynet.SafetyNetAlerts.dto.ChildInfos;
import com.safetynet.SafetyNetAlerts.dto.PersonListOfSameAddressDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    SolutionFormatter solutionFormatter;

    public Iterable<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordRepository.getAll();
    }
    public Boolean saveMedicalRecord(MedicalRecord medicalRecord){
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
    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord){
        List<MedicalRecord> allMedicalRecords = medicalRecordRepository.getAll();

        for (int i = 0; i < allMedicalRecords.size(); i++){
            if(allMedicalRecords.get(i).getFirstName().equals(firstName)
                    && allMedicalRecords.get(i).getLastName().equals(lastName)){
                return medicalRecordRepository.update(i,medicalRecord);
            }
        }
        return null;
    }
    public List<MedicalRecord> getMedicalRecordsBySameFamilyName(String lastName){
        return medicalRecordRepository.getByType(lastName);
    }
    //Retourner une list d'enfants habitent dans une adresse, la liste comprend le nom, âge, une liste des autres membres du foyer
    public ChildAlertDTO getChildAndHisFamilyMemberByAGivenAddress(String address){
        List<ChildInfos> childInfosList = new ArrayList<>();
        List<PersonListOfSameAddressDTO> personListOfSameAddress = new ArrayList<>();

        for(Person personByAddress : personRepository.getByType(address)){
            for (MedicalRecord medicalRecordByAddress : medicalRecordRepository.getAll()){
                if(medicalRecordByAddress.getFirstName().equals(personByAddress.getFirstName()) && medicalRecordByAddress.getLastName().equals(personByAddress.getLastName())){
                    if(solutionFormatter.formatterStringToDate(medicalRecordByAddress.getBirthdate()) <= 18){
                        int ageChild = solutionFormatter.formatterStringToDate(medicalRecordByAddress.getBirthdate());
                        ChildInfos newChildInfos = new ChildInfos(medicalRecordByAddress.getFirstName(),medicalRecordByAddress.getLastName(),ageChild);
                        childInfosList.add(newChildInfos);
                    }else {
                        List<String> medicalRecords = new ArrayList<>();
                        int ageAdult = solutionFormatter.formatterStringToDate(medicalRecordByAddress.getBirthdate());
                        medicalRecords.addAll(medicalRecordByAddress.getMedications());
                        medicalRecords.addAll(medicalRecordByAddress.getAllergies());
                        PersonListOfSameAddressDTO newPersonListOfSameAddress =
                                new PersonListOfSameAddressDTO(personByAddress.getFirstName(),personByAddress.getLastName(),personByAddress.getPhone(),ageAdult,medicalRecords);
                        personListOfSameAddress.add(newPersonListOfSameAddress);
                    }

                }

            }
        }
        if(childInfosList.isEmpty() && personListOfSameAddress.isEmpty()) {
            return null;
        }else if(childInfosList.isEmpty() && personListOfSameAddress != null){
            childInfosList = new ArrayList<>();
            personListOfSameAddress = new ArrayList<>();
            return new ChildAlertDTO(childInfosList, personListOfSameAddress);
        }
        return new ChildAlertDTO(childInfosList, personListOfSameAddress);
    }

}
