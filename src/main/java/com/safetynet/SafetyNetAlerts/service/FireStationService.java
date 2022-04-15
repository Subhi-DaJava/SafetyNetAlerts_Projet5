package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatter;
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
    @Autowired
    private SolutionFormatter solutionFormatter;

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
    public FireStation updateStation(String address, FireStation stationNumberUpdated) {
        List<FireStation> allFireStations = fireStationRepository.getAll();
        FireStation candidate = null;
        for (int i = 0; i < allFireStations.size(); i++) {
            if (allFireStations.get(i).getAddress().equals(address)) {
               candidate = fireStationRepository.update(i, stationNumberUpdated);
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
    public FireDTO getInfosOfPersonsLiveAtSameAddress(String address) {
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOS = new ArrayList<>();
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
                    PersonListOfSameAddressDTO newPersonList = new PersonListOfSameAddressDTO(medicalRecord.getFirstName(),medicalRecord.getLastName(), person.getPhone(), age, medicalRecords);
                    personListOfSameAddressDTOS.add(newPersonList);

                }
            }
        }
        FireDTO fireDTO = new FireDTO(personListOfSameAddressDTOS, stationNumber);
        if(fireDTO.getListOfPersonAtSameAddress().isEmpty() && fireDTO.getStation()==null)
            return null;
        return fireDTO;
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
        if(allPhonesAlerts.isEmpty())
            return null;
        return allPhonesAlerts;
    }
    //Retourne une liste des personnes couvertes par la caserne de pompiers correspondante, liste : nom, adresse, tel, décompte d'adulte et d'enfants
    public List<FireStationDTO> getAllPersonsInfoFromAGivenFireStationNumber(String fireStationNumber){
        List<FireStationDTO> personList = new ArrayList<>();
        List<HomeListCoveredByOneFireStationDTO> homeDTOList = new ArrayList<>();
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
        for (FireStationDTO fireStationDTO : personList){
            if(fireStationDTO.getHomeListCoveredByOneFireStation().isEmpty())
                return null;
        }
        return personList;
    }
    //Retourne une liste de tous les foyers desservis par la caserne, cette liste regroupe les personnes par adresse : nom, tel, âge et antécédents médicaux
    public List<FloodDTO> getPersonsByAddressFromListOfStation_Number(List<String> listStation_Number){
        List<FloodDTO> floodDTOList = new ArrayList<>();
        for (String stationNumber : listStation_Number){
            for (FireStation fireStation : fireStationRepository.getByType(stationNumber)){
                //Regroupe les personnes dans la même address
                List<PersonListOfSameAddressDTO> personsListOfSameAddress = new ArrayList<>();
                for (Person person : personRepository.getByType(fireStation.getAddress())){
                    for (MedicalRecord medicalRecord : medicalRecordRepository.getAll()){
                        if(person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())){
                            int age = solutionFormatter.formatterStringToDate(medicalRecord.getBirthdate());
                            List<String> medicalRecords = new ArrayList<>();
                            medicalRecords.addAll(medicalRecord.getMedications());
                            medicalRecords.addAll(medicalRecord.getAllergies());
                            PersonListOfSameAddressDTO personsListsOfSameAddress =
                                    new PersonListOfSameAddressDTO(medicalRecord.getFirstName(),medicalRecord.getLastName(),person.getPhone(),age,medicalRecords);
                            personsListOfSameAddress.add(personsListsOfSameAddress);
                        }
                    }
                }
                FloodDTO newFlood = new FloodDTO(personsListOfSameAddress,fireStation.getAddress());
                floodDTOList.add(newFlood);
            }
        }
        if (floodDTOList.isEmpty())
            return null;

        return floodDTOList;
    }


}
