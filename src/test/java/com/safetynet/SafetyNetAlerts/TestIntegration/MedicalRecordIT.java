package com.safetynet.SafetyNetAlerts.TestIntegration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllMedicalRecordTest() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName",is("John")));
    }
    @Disabled
    @Test
    public void saveMedicalRecordTest() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MedicalRecord("John","Boyd","03/06/1984",null,null))))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}","John","Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MedicalRecord("John","Boyd","07/07/1980",null,null))))
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
                .andExpect(status().isOk());
    }


}
