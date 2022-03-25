package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
