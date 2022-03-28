package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.PhoneAlertDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    @Autowired
    PersonRepository personRepository;

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
    public Iterable<FireStation> getAddressByStation(String station){
        return fireStationRepository.getByType(station);
    }
    //Retourner une liste des numéros de téléphones des résidents desservis par la caserne de pompiers
    public List<PhoneAlertDTO> getAllPhoneNumbersOfPersonsCoveredByOneFireStation(String fireStationNumber){
        List<PhoneAlertDTO> allPhonesAlerts = new ArrayList<>();
        //Pour vérifier si un phone number est-il dans la list allPhonesAlerts
        boolean isPhoneAlertExisting = false;

        for(FireStation fireStation : fireStationRepository.getAll()){
            if(fireStation.getStation().equals(fireStationNumber)){
                //Affecter (l'adresse correspondante) la variable addressPerson si fireStationNumber se trouve dans la BDD
                String addressPerson = fireStation.getAddress();
                //Itérer chaque instance(objet) de Person et affecter(ou instancier) (person.getPhone()=phone number comme argument) PhoneAlertDTO(String phone)
                for(Person person : personRepository.getAll()){
                    if(person.getAddress().equals(addressPerson)){
                        PhoneAlertDTO newPhoneAlert = new PhoneAlertDTO(person.getPhone());
                        //Vérifier si le person.getPhone() se trouve dans la liste allPhonesAlerts
                        for (PhoneAlertDTO allPhones : allPhonesAlerts){
                            if(allPhones.getPhone().equals(newPhoneAlert.getPhone())){
                                isPhoneAlertExisting = true;
                            }
                        }
                        if(!isPhoneAlertExisting){
                            allPhonesAlerts.add(newPhoneAlert);
                        }
                    }

                }
            }
            isPhoneAlertExisting = false;
        }
        System.out.println(allPhonesAlerts.size()+" phone numbers have been found.");
        return allPhonesAlerts;
    }
}
