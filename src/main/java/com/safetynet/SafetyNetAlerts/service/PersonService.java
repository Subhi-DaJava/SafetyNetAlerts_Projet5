package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.dto.FireDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatter;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatterImpl;
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
    //Retourner la liste des habitants(que le NOM) d'une adresse donnée, ainsi que le numéro de la caserne de pompiers la desservante,
    // les antécédents médicaux(médicaments,posologie et allergie)
    public List<FireDTO> getAllAddressPhoneAgeMedicalRecordAndNumberOfFireStation(String address) {
        List<FireDTO> fireDTOList = new ArrayList<>();
        SolutionFormatter solutionFormatter = new SolutionFormatterImpl();
        String station = null;
        int age;
        //Period age= null;
        for (Person person : personRepository.getAll()) {
            if (person.getAddress().contains(address)) {
                for (FireStation fireStation : fireStationRepository.getAll()) {
                    if (fireStation.getAddress().equals(person.getAddress())) {
                        station = fireStation.getStation();
                    }
                }
                for (MedicalRecord medicalRecord : medicalRecordRepository.getAll()) {
                    if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                       age = solutionFormatter.formatterStringToDate(medicalRecord.getBirthdate(),"MM/dd/yyyy");
                        Set<String> medicationsSet = new HashSet<>();
                        Set<String> allergiesSet = new HashSet<>();
                        medicationsSet.addAll(medicalRecord.getMedications());
                        allergiesSet.addAll(medicalRecord.getAllergies());

                        FireDTO newFireDTO = new FireDTO(person.getLastName(), person.getPhone(), station, age, medicationsSet,allergiesSet);
                        fireDTOList.add(newFireDTO);
                    }
                }

            }
        }
        System.out.println(fireDTOList.size()+" people are in this address.");
        return fireDTOList;

    }
}
