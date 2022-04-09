package com.safetynet.SafetyNetAlerts.TestIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllFireStationTest() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
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
                .andExpect(status().isOk());
    }

    @Test
    public void getAllPersonsInfoFromAGivenFireStationNumberTest() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber={station_number}","3"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsByAddressFromListOfStation_NumberTest() throws Exception {
        mockMvc.perform(get("/flood/stations?stations={?}","1,3"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
