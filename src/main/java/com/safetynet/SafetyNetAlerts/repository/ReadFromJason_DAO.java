package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
public class ReadFromJason_DAO {
    @Autowired
    CustomProperties cp;

    public ReadFromJason_DAO(){

    }

    public Repository readFromJsonFile(){

        Repository repository = new Repository();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            repository = objectMapper.readValue(new File(cp.getFileJson()), Repository.class);

        } catch (IOException e) {
            System.out.println("Data.json file not found or not loading correctly.");
            e.printStackTrace();
        }

        return repository;
    }
}
