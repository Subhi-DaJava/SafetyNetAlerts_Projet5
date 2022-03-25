package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class FireStationRepository implements CRUD_Method<FireStation>{
    @Autowired
    private ReadFromJason_DAO readFromJason_dao;

    private Repository repository;

    private List<FireStation> fireStations;

    public FireStationRepository() {
    }

    private void loadFireStationsFromJsonFile(){
        //Mapping JSON to Repository
        repository = readFromJason_dao.readFromJsonFile();
        //Mapping firestations to the list FireStations
        fireStations = repository.getFirestations();
    }

    @Override
    public List<FireStation> getAll() {
        if(repository == null )
            loadFireStationsFromJsonFile();
        //fireStations(Mapping, désérialiser) affected by repository.getFireStations()
        return fireStations;
    }

    @Override
    public Boolean save(FireStation fireStation) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        //Add fireStation to the list fireStations
        return fireStations.add(fireStation);
    }

    @Override
    public Boolean delete(FireStation fireStation) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        //Remove fireStation from the List of fireStations
        return fireStations.remove(fireStation);
    }

    @Override
    public FireStation update(int i, FireStation fireStation) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        //Update the element at the index of i of the list fireStations to fireStation
        return fireStations.set(i,fireStation);
    }

    @Override
    public List<FireStation> getByType(String station) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        List<FireStation> stations = new ArrayList<>();
        for(FireStation fireStation : fireStations){
            if (fireStation.getStation().equals(station)){
                stations.add(fireStation);
            }
        }
        return stations;
    }

}
