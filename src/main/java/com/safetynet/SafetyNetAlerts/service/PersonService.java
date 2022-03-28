package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

        System.out.println(emailList.size()+" emails have been found.");
        return emailList;
    }


}
