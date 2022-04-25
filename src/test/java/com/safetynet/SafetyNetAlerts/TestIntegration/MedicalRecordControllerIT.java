package com.safetynet.SafetyNetAlerts.TestIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.repository.Repository;
import com.safetynet.SafetyNetAlerts.util.CustomPropertiesTest;
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

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Repository repository;
    @Autowired
    private CustomPropertiesTest customPropertiesTest;
    @BeforeEach
    public void setUp() throws IOException {
        repository = objectMapper.readValue(new File(customPropertiesTest.getFileJson()), Repository.class);
    }

    @AfterEach
    public void tearDown(){
        repository.getPersons().clear();
        repository.getFirestations().clear();
        repository.getMedicalrecords().clear();
    }
    @Test
    public void getAllMedicalRecordTest() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].firstName",is("John")));

    }

    @Test
    public void saveMedicalRecordTest() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MedicalRecord("Shawna","Stelzer","03/06/1984",null,null))))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}","Jamie","Peters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MedicalRecord("Jamie","Peters","07/07/1980",null,null))))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteMedicalRecordTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}","John","Boyd"))
                .andExpect(status().isOk());
    }
    @Test
    public void getMedicalRecordsBySameFamilyNameTest() throws Exception {
        mockMvc.perform(get("/medicalRecord/{lastName}","Boyd"))
                .andExpect(status().isOk());
    }
    @Test
    public void getChildListTest() throws Exception {
        mockMvc.perform(get("/childAlert?address={address}","1509 Culver St"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
