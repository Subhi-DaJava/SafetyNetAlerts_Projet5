package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        repository = readFromJason_dao.readFromJsonFile();
        fireStations = repository.getFirestations();
    }

    @Override
    public List<FireStation> getAll() {
        if(repository == null )
            loadFireStationsFromJsonFile();
        return fireStations;
    }

    @Override
    public Boolean save(FireStation fireStation) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        return fireStations.add(fireStation);
    }

    @Override
    public Boolean delete(FireStation fireStation) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        return fireStations.remove(fireStation);
    }

    @Override
    public FireStation update(int i, FireStation fireStation) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        return fireStations.set(i,fireStation);
    }
}
