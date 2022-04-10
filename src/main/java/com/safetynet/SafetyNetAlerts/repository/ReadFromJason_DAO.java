package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynet.SafetyNetAlerts.util.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
public class ReadFromJason_DAO {
    @Autowired
    private CustomProperties cp;

    public ReadFromJason_DAO(){

    }

    public Repository readFromJsonFile(){

        Repository repository = new Repository();
        //Désérialiser
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //read json file and convert to repository object
            repository = objectMapper.readValue(new File(cp.getFileJson()), Repository.class);

        } catch (IOException e) {
            System.out.println("Data.json file not found or not loading correctly.");
            e.printStackTrace();
        }

        return repository;
    }
}
