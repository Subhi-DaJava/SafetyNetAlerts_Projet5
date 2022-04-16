package com.safetynet.SafetyNetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.dto.ChildAlertDTO;
import com.safetynet.SafetyNetAlerts.dto.ChildInfos;
import com.safetynet.SafetyNetAlerts.dto.PersonListOfSameAddressDTO;
import com.safetynet.SafetyNetAlerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    ObjectMapper objectMapper;

    private List<MedicalRecord> medicalRecordList;
    @BeforeEach
    public void setUp(){
        List<String> medications1 = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies1 = new ArrayList<>(List.of("nillacilan"));
        List<String> medications2 = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies2 = new ArrayList<>(List.of("nillacilan"));
        List<String> medications3 = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies3 = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord1= new MedicalRecord("John","Boyd","03/06/1984",medications1,allergies1);
        MedicalRecord medicalRecord2= new MedicalRecord("Jean","Dupont","10/08/2010",medications2,allergies2);
        MedicalRecord medicalRecord3= new MedicalRecord("François","Dumas","26/02/1996",medications3,allergies3);
        medicalRecordList = new ArrayList<>(Arrays.asList(medicalRecord1,medicalRecord2,medicalRecord3));
    }
    @Test
    public void getAllMedicalRecordTest() throws Exception {
        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[2].lastName", is("Dumas")));
        Mockito.verify(medicalRecordService).getAllMedicalRecords();
    }
    @Test
    public void getAnyMedicalRecordTest() throws Exception {
        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenThrow(new MedicalRecordNotFoundException("Any MedicalRecord not found"));
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isNotFound());

        Mockito.verify(medicalRecordService).getAllMedicalRecords();
    }
    @Test
    public void saveAMedicalRecordTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecordAdded= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        Mockito.when(medicalRecordService.saveMedicalRecord(medicalRecordAdded)).thenReturn(true);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicalRecordAdded)))
                .andExpect(status().isCreated());
        Mockito.verify(medicalRecordService).saveMedicalRecord(medicalRecordAdded);

    }
    @Test
    public void saveAnyMedicalRecordTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecordAdded= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        Mockito.when(medicalRecordService.saveMedicalRecord(medicalRecordAdded)).thenReturn(false);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicalRecordAdded)))
                .andExpect(status().isNotFound());
        Mockito.verify(medicalRecordService).saveMedicalRecord(medicalRecordAdded);
    }

    @Test
    public void updateAMedicalRecordTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecordUpdated= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        Mockito.when(medicalRecordService.updateMedicalRecord("John","Boyd",medicalRecordUpdated)).thenReturn(medicalRecordUpdated);
        mockMvc.perform(put("/medicalRecord/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicalRecordUpdated)))
                .andExpect(status().isOk());
        Mockito.verify(medicalRecordService).updateMedicalRecord("John","Boyd",medicalRecordUpdated);

    }
    @Test
    public void updateAnyMedicalRecordTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecordUpdated= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        Mockito.when(medicalRecordService.updateMedicalRecord("John","Boyd",medicalRecordUpdated)).thenReturn(null);
        mockMvc.perform(put("/medicalRecord/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicalRecordUpdated)))
                        .andExpect(status().isNotFound());
        Mockito.verify(medicalRecordService).updateMedicalRecord("John","Boyd",medicalRecordUpdated);

    }
    @Test
    public void deleteAMedicalRecordTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecordDeleted= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);
        Mockito.when(medicalRecordService.deleteMedicalRecord(medicalRecordDeleted.getFirstName(),medicalRecordDeleted.getLastName())).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord/John/Boyd"))
                .andExpect(status().isOk());
        Mockito.verify(medicalRecordService).deleteMedicalRecord(anyString(),anyString());
    }
    @Test
    public void deleteAnyMedicalRecordTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecordDeleted= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);
        Mockito.when(medicalRecordService.deleteMedicalRecord(medicalRecordDeleted.getFirstName(),medicalRecordDeleted.getLastName())).thenReturn(false);

        mockMvc.perform(delete("/medicalRecord/John/Boyd"))
                .andExpect(status().isNotFound());
        Mockito.verify(medicalRecordService).deleteMedicalRecord(anyString(),anyString());
    }
    @Test
    public void getMedicalRecordsBySameFamilyNameTest() throws Exception {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        List<MedicalRecord> medicalRecordByLastName = new ArrayList<>(List.of(medicalRecord));
        Mockito.when(medicalRecordService.getMedicalRecordsBySameFamilyName("Boyd")).thenReturn(medicalRecordByLastName);

        mockMvc.perform(get("/medicalRecord/Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName",is("Boyd")));

        Mockito.verify(medicalRecordService).getMedicalRecordsBySameFamilyName(anyString());
    }
    @Test
    public void getAnyMedicalRecordBySameFamilyNameTest() throws Exception {
        Mockito.when(medicalRecordService.getMedicalRecordsBySameFamilyName("Boyd")).thenThrow(new MedicalRecordNotFoundException("Any MedicalRecord not found !"));

        mockMvc.perform(get("/medicalRecord/Boyd"))
                .andExpect(status().isNotFound());

        Mockito.verify(medicalRecordService).getMedicalRecordsBySameFamilyName(anyString());
    }
    @Test
    public void getChildListTest() throws Exception {
        ChildInfos childInfos1 = new ChildInfos("Tenley","Boyd",18);
        ChildInfos childInfos2 = new ChildInfos("Jacob","Boyd",17);
        List<ChildInfos> childInfosList = new ArrayList<>(Arrays.asList(childInfos1,childInfos2));
        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        List<String> medicalRecordsList2 = new ArrayList<>(Arrays.asList("aznol:250mg", "hydrapermazol:200mg", null));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Felicia","Boyd","841-874-6544",34,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(Arrays.asList(personListOfSameAddress1,personListOfSameAddress2));

        ChildAlertDTO newChildAlert = new ChildAlertDTO(childInfosList,personListOfSameAddressDTOList);
        Mockito.when(medicalRecordService.getChildAndHisFamilyMemberByAGivenAddress(anyString())).thenReturn(newChildAlert);

        mockMvc.perform(get("/childAlert?address={?}","1509 Culver St"))
                .andExpect(status().isOk());
        Mockito.verify(medicalRecordService).getChildAndHisFamilyMemberByAGivenAddress(anyString());
    }
    @Test
    public void getAnyChildListTest() throws Exception {
        ChildInfos childInfos1 = new ChildInfos("Tenley","Boyd",18);
        ChildInfos childInfos2 = new ChildInfos("Jacob","Boyd",17);
        List<ChildInfos> childInfosList = new ArrayList<>(Arrays.asList(childInfos1,childInfos2));
        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        List<String> medicalRecordsList2 = new ArrayList<>(Arrays.asList("aznol:250mg", "hydrapermazol:200mg", null));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Felicia","Boyd","841-874-6544",34,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(Arrays.asList(personListOfSameAddress1,personListOfSameAddress2));

        ChildAlertDTO newChildAlert = new ChildAlertDTO(childInfosList,personListOfSameAddressDTOList);
        Mockito.when(medicalRecordService.getChildAndHisFamilyMemberByAGivenAddress(anyString())).thenReturn(null);

        mockMvc.perform(get("/childAlert?address={?}","1509 Culver St"))
                .andExpect(status().isNotFound());
        Mockito.verify(medicalRecordService).getChildAndHisFamilyMemberByAGivenAddress(anyString());
    }


}