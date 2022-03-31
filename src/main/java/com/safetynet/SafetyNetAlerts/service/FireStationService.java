package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.*;
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

import java.util.*;

@Service
public class FireStationService {
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PersonRepository personRepository;

    public FireStationService() {
    }
    public Iterable<FireStation> getAllFireStations(){
        return fireStationRepository.getAll();
    }

    public Boolean saveStation(FireStation fireStation){
        return fireStationRepository.save(fireStation);
    }
    //Delete the fireStation with the address(like the Key Unique)
    public Boolean deleteStation(String address){
        for(FireStation fireStation : fireStationRepository.getAll()){
            if(fireStation.getAddress().equals(address))
                return fireStationRepository.delete(fireStation);
        }
        return false;
    }
    //Update the number of the FireStation with the address
    public FireStation updateStation(String address, String number) {
        List<FireStation> allFireStations = fireStationRepository.getAll();
        FireStation candidate = null;
        for (int i = 0; i < allFireStations.size(); i++) {
            if (allFireStations.get(i).getAddress().equals(address)) {
                candidate = allFireStations.get(i);
                candidate.setStation(number);
                fireStationRepository.update(i, candidate);
            }

        }
        return candidate;
    }
    //Trier les adresses par la caserne de pompiers
    public Iterable<FireStation> getAllAddressCoveredByOneFireStation(String stationNbr){
        return fireStationRepository.getByType(stationNbr);
    }

    //Retourner la liste des habitants(que le NOM) d'une adresse donnée, ainsi que le numéro de la caserne de pompiers la desservante,
    // les antécédents médicaux(médicaments,posologie et allergie)
    public FireDTO getInfosOfPersonsLiveSameAddress(String address) {
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOS = new ArrayList<>();
        SolutionFormatter solutionFormatter = new SolutionFormatterImpl();
        String stationNumber = null;
        int age;

        for (FireStation fireStation : fireStationRepository.getAll()) {
            if (fireStation.getAddress().equals(address)) {
                stationNumber = fireStation.getStation();
            }
        }
        for (Person person : personRepository.getByType(address)) {
            for (MedicalRecord medicalRecord : medicalRecordRepository.getAll()) {
                if (person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
                    age = solutionFormatter.formatterStringToDate(medicalRecord.getBirthdate());
                    List<String> medicalRecords = new ArrayList<>();
                    medicalRecords.addAll(medicalRecord.getMedications());
                    medicalRecords.addAll(medicalRecord.getAllergies());
                    PersonListOfSameAddressDTO newPersonList = new PersonListOfSameAddressDTO(medicalRecord.getLastName(), person.getPhone(), age, medicalRecords);
                    personListOfSameAddressDTOS.add(newPersonList);

                }
            }
        }
        FireDTO personListOfSameAddress = new FireDTO(personListOfSameAddressDTOS,stationNumber);
        return personListOfSameAddress;

    }
    //Retourner une liste des numéros de téléphones des résidents desservis par la caserne de pompiers
    public List<PhoneAlertDTO> getAllPhoneNumbersOfPersonsCoveredByOneFireStation(String fireStationNumber){
        List<PhoneAlertDTO> allPhonesAlerts = new ArrayList<>();
        Set<String>  phoneNumberSet = new HashSet<>();
        for(FireStation fireStation : fireStationRepository.getAll()){
            if(fireStation.getStation().equals(fireStationNumber)){
                for(Person person : personRepository.getAll()){
                    if(person.getAddress().equals(fireStation.getAddress())){
                        phoneNumberSet.add(person.getPhone());
                    }
                }
            }

        }
        for(String phoneNumber : phoneNumberSet) {
            PhoneAlertDTO newPhoneAlert = new PhoneAlertDTO(phoneNumber);
            allPhonesAlerts.add(newPhoneAlert);
        }
        System.out.println(allPhonesAlerts.size()+" phone numbers have been found.");
        return allPhonesAlerts;
    }
    public List<FireStationDTO> getAllPersonsInfoFromAGivenFireStationNumber(String fireStationNumber){
        List<FireStationDTO> personList = new ArrayList<>();
        List<HomeListCoveredByOneFireStationDTO> homeDTOList = new ArrayList<>();
        SolutionFormatter solutionFormatter = new SolutionFormatterImpl();
        int countAdult = 0;
        int countChild = 0;

        for (FireStation addressByFireStation : fireStationRepository.getByType(fireStationNumber)){
            for (Person person : personRepository.getByType(addressByFireStation.getAddress())){

                    for (MedicalRecord medicalRecord : medicalRecordRepository.getAll()){
                        if(person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())){
                            int age = solutionFormatter.formatterStringToDate(medicalRecord.getBirthdate());
                            if(age > 18){
                                countAdult++;
                            }else {
                                countChild++;
                            }
                            HomeListCoveredByOneFireStationDTO newHomeDTO = new HomeListCoveredByOneFireStationDTO(person.getFirstName(),person.getLastName(), person.getAddress(), person.getPhone());
                            homeDTOList.add(newHomeDTO);
                        }
                    }

            }

        }
        FireStationDTO newFireStationDto = new FireStationDTO(homeDTOList,countAdult,countChild);
        personList.add(newFireStationDto);
        return personList;
    }

}
