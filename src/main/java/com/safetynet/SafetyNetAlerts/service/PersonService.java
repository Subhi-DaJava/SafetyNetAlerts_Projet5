package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

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
        return personRepository.getByAddress(address);
    }
}
