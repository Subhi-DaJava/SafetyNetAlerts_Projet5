package com.safetynet.SafetyNetAlerts.TestIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Repository repository;
    @BeforeEach
    public void setUp() throws IOException {
        String fileJson = "C:/Users/asus/openClassRoomsIntelliJ/P5_SprintBoot/Projet_P5/SafetyNetProjet/SafetyNet-Alerts/src/test/java/ressource/data.json";
        repository = objectMapper.readValue(new File(fileJson), Repository.class);
    }

    @AfterEach
    public void tearDown(){
        repository.getPersons().clear();
        repository.getFirestations().clear();
        repository.getMedicalrecords().clear();
    }
    @Test
    public void getAllFireStationTest() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void addFireStationTest() throws Exception{
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FireStation("50 rue de Paris","5"))))
                .andExpect(status().isCreated());
    }
    @Test
    public void deleteFireStation() throws Exception {
        mockMvc.perform(delete("/firestation/{address}","1509 Culver St"))
                .andExpect(status().isOk());
    }
    @Test
    public void updateFireStation() throws Exception {
        mockMvc.perform(put("/firestation/{address}","1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new FireStation("1509 Culver St","4"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAddressByStationNumberTest() throws Exception {
            mockMvc.perform(get("/firestation/{stationNbr}","3"))
                    .andExpect(status().isOk())
                    .andDo(print());

    }

    @Test
    public void getInfosOfPersonsLiveSameAddressTest() throws Exception {
        mockMvc.perform(get("/fire?address={address}","1509 Culver St"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllPhoneNumbersOfPersonsCoveredByOneFireStation() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation={firestation_number}","3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllPersonsInfoFromAGivenFireStationNumberTest() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber={station_number}","3"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsByAddressFromListOfStation_NumberTest() throws Exception {
        mockMvc.perform(get("/flood/stations?stations={?}","4"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
