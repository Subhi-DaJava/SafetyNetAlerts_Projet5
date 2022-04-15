package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class FireStationRepository implements Methods_CRUD<FireStation> {
    @Autowired
    private DataJSONConverter readFromJason_dao;

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
        //fireStations(Mapping, désérialiser) affected by repository.getFireStations()
        if(repository == null )
            loadFireStationsFromJsonFile();
        return  fireStations;
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

       fireStations.set(i,fireStation);

        return fireStations.set(i,fireStation);
    }

    //Retourne une liste of FireStation by Station_number
    @Override
    public List<FireStation> getByType(String type) {
        if(repository == null)
            loadFireStationsFromJsonFile();
        List<FireStation> fireStationsByStation = new ArrayList<>();
        for(FireStation fireStation : fireStations){
            if (fireStation.getStation().equals(type)){
                fireStationsByStation.add(fireStation);
            }
        }
        return fireStationsByStation;
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
    }
}
