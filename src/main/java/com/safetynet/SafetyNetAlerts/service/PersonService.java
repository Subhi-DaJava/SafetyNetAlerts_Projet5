package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PersonService {
    private static Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    //L'injection de dépendance
    @Autowired
    SolutionFormatter solutionFormatter;

    public PersonService() {
    }
    //Get all persons
    public Iterable<Person> getAllPersons(){

        return personRepository.getAll();
    }
    public Boolean savePerson(Person p){
        return personRepository.save(p);
    }

    //Delete a person (fistName & lastName required)
    public Boolean deletePerson(String firstName, String lastName){
        logger.debug("Delete a person by his firstName and lastName");
        for(Person p : personRepository.getAll()){
            if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                return personRepository.delete(p);
        }
        return false;
    }
    //Update a person's all attributs sauf firstName & lastName
    public Person updatePerson(Person p){
        Person candidate = null;
        List<Person> allPersons = personRepository.getAll();
        for (int i = 0; i < allPersons.size(); i++){
            if(allPersons.get(i).getFirstName().equals(p.getFirstName())
                    && allPersons.get(i).getLastName().equals(p.getLastName())){
                candidate = personRepository.update(i,p);
            }
        }
        return candidate;
    }
    //Trier les personnes par son adresse
    public Iterable<Person> getByAddress(String address){
        return personRepository.getByType(address);
    }

    //Retourner une list<Email> ou null de tous les habitants d'une ville
    public List<CommunityEmailDTO> getAllEmailsFromGivenCity(String city){
        List<CommunityEmailDTO> emailList = new ArrayList<>();
        Set<String> emailSet = new HashSet<>();
        for(Person person : personRepository.getAll()){
            if (person.getCity().contains(city)){
                emailSet.add(person.getEmail());
            }
        }
        for(String email : emailSet){
            CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO(email);
            emailList.add(communityEmailDTO);
        }
        return emailList;
    }

    //Retourner le nom, l'adresse, l'âge, l'adresse mail, les antécédents médicaux(médicaments,posologie, allergies)
    public List<PersonInfoDTO> getInformationOfSameFamily(String firstName, String lastName) {
        List<PersonInfoDTO> personInfoDTOS = new ArrayList<>();
        int age;
        for (Person person : personRepository.getAll()) {
            if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName)) {
                for (MedicalRecord medicalRecord : medicalRecordRepository.getAll()) {
                    if (person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                        age = solutionFormatter.formatterStringToDate(medicalRecord.getBirthdate());
                        Set<String> mRecords = new HashSet<>();
                        mRecords.addAll(medicalRecord.getMedications());
                        mRecords.addAll(medicalRecord.getAllergies());
                        PersonInfoDTO newPersonInfoDTO = new PersonInfoDTO(person.getFirstName(),person.getLastName(), person.getAddress(), age, person.getEmail(), mRecords);
                        personInfoDTOS.add(newPersonInfoDTO);
                    }

                }

            }
        }
        return personInfoDTOS;
    }
}
