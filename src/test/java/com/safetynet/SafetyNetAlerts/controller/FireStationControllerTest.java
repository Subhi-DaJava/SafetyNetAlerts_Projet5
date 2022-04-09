package com.safetynet.SafetyNetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FireStationService fireStationService;
    @Autowired
    ObjectMapper objectMapper;
    private List<FireStation> fireStationList;

    @BeforeEach
    public void setUp(){
        FireStation fireStation1 = new FireStation( "1509 Culver St", "3" );
        FireStation fireStation2 = new FireStation("29 15th St", "2");
        FireStation fireStation3 = new FireStation("834 Binoc Ave", "3");
        FireStation fireStation4 = new FireStation("644 Gershwin Cir", "1");
        fireStationList = new ArrayList<>(Arrays.asList(fireStation1,fireStation2,fireStation3,fireStation4));
    }
    @Test
    public void getAllFireStationsTest() throws Exception {
        when(fireStationService.getAllFireStations()).thenReturn(fireStationList);
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address",is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station",is("3")));
        Mockito.verify(fireStationService).getAllFireStations();
    }
    @Test
    public void getAnyFireStationsTest() throws Exception {
        when(fireStationService.getAllFireStations()).thenReturn(null);
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isNotFound());
        Mockito.verify(fireStationService).getAllFireStations();
    }


    @Test
    public void addFireStationTest() throws Exception {
        FireStation newFireStation = new FireStation("230 Gershwin Cir", "4");
        when(fireStationService.saveStation(newFireStation)).thenReturn(true);
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFireStation)))
                .andExpect(status().isCreated());
        verify(fireStationService).saveStation(newFireStation);
    }
    @Test
    public void addAnyFireStationTest() throws Exception {
        FireStation newFireStation = new FireStation("230 Gershwin Cir", "4");
        when(fireStationService.saveStation(newFireStation)).thenReturn(false);
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFireStation)))
                .andExpect(status().isNotFound());
        verify(fireStationService).saveStation(newFireStation);
    }
    @Test
    public void deleteFireStationTest() throws Exception {
        String address = "anyAddress";
        when(fireStationService.deleteStation(address)).thenReturn(true);
        mockMvc.perform(delete("/firestation/anyAddress"))
                .andExpect(status().isOk());
        verify(fireStationService).deleteStation(address);
    }
    @Test
    public void deleteAnyFireStationTest() throws Exception {
        String address = "anyAddress";
        when(fireStationService.deleteStation(address)).thenReturn(false);
        mockMvc.perform(delete("/firestation/anyAddress"))
                .andExpect(status().isNotFound());
        verify(fireStationService).deleteStation(address);
    }

    @Test
    public void updateFireStationTest() throws Exception {
        String address = "1509 Culver St";
        String stationNumber = "5";
        FireStation fireStationUpdated = new FireStation(address,stationNumber);
        when(fireStationService.updateStation(address,fireStationUpdated)).thenReturn(fireStationUpdated);
        mockMvc.perform(put("/firestation/1509 Culver St")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireStationUpdated)))
                .andExpect(status().isOk());
        verify(fireStationService).updateStation(address,fireStationUpdated);
    }
    @Test
    public void updateAnyFireStationTest() throws Exception {
        String address = "1509 Culver St";
        String stationNumber = "5";
        FireStation fireStationUpdated = new FireStation(address,stationNumber);
        when(fireStationService.updateStation(address,fireStationUpdated)).thenReturn(null);
        mockMvc.perform(put("/firestation/1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fireStationUpdated)))
                .andExpect(status().isNotFound());
        verify(fireStationService).updateStation(address,fireStationUpdated);
    }
    @Test
    public void getAllAddressByStationNumber() throws Exception {
        FireStation fireStation1 = new FireStation( "1509 Culver St", "3" );
        FireStation fireStation3 = new FireStation("834 Binoc Ave", "3");
        List<FireStation> fireStationsByAddress = new ArrayList<>(Arrays.asList(fireStation1,fireStation3));
        when(fireStationService.getAllAddressCoveredByOneFireStation(anyString())).thenReturn(fireStationsByAddress);
        mockMvc.perform(get("/firestation/{?}","3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address",is("1509 Culver St")))
                .andExpect(jsonPath("$[1].address",is("834 Binoc Ave")));
        verify(fireStationService).getAllAddressCoveredByOneFireStation(anyString());
    }
    @Test
    public void getAnyAddressByStationNumber() throws Exception {

        when(fireStationService.getAllAddressCoveredByOneFireStation(anyString())).thenReturn(null);
        mockMvc.perform(get("/firestation/{?}","3"))
                .andExpect(status().isNotFound());
        verify(fireStationService).getAllAddressCoveredByOneFireStation(anyString());
    }
    @Test
    public void getInfosOfPersonsLiveSameAddressTest() throws Exception {
        String stationNumber = "3";
        String address = "1509 Culver St";
        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        List<String> medicalRecordsList2 = new ArrayList<>(Arrays.asList("aznol:250mg", "hydrapermazol:200mg", null));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Felicia","Boyd","841-874-6544",34,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(Arrays.asList(personListOfSameAddress1,personListOfSameAddress2));
        FireDTO newFire = new FireDTO(personListOfSameAddressDTOList,stationNumber);

        when(fireStationService.getInfosOfPersonsLiveAtSameAddress(anyString())).thenReturn(newFire);

        mockMvc.perform(get("/fire?=address={?}",address))
                .andExpect(status().isOk());
        verify(fireStationService).getInfosOfPersonsLiveAtSameAddress(anyString());

    }
    @Test
    public void getAnyInfosOfPersonsLiveSameAddressTest() throws Exception {
        String stationNumber = "3";
        String address = "1509 Culver St";
        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        List<String> medicalRecordsList2 = new ArrayList<>(Arrays.asList("aznol:250mg", "hydrapermazol:200mg", null));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Felicia","Boyd","841-874-6544",34,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(Arrays.asList(personListOfSameAddress1,personListOfSameAddress2));
        FireDTO newFire = new FireDTO(personListOfSameAddressDTOList,stationNumber);

        when(fireStationService.getInfosOfPersonsLiveAtSameAddress(anyString())).thenReturn(null);

        mockMvc.perform(get("/fire?=address={?}",address))
                .andExpect(status().isNotFound());
        verify(fireStationService).getInfosOfPersonsLiveAtSameAddress(anyString());

    }

    @Test
    public void getAllPhoneNumbersOfPersonsCoveredByOneFireStationTest() throws Exception {
        String stationNumber = "3";
        PhoneAlertDTO phoneAlert1 = new PhoneAlertDTO("841-874-6512");
        PhoneAlertDTO phoneAlert2 = new PhoneAlertDTO("841-874-6544");
        PhoneAlertDTO phoneAlert3 = new PhoneAlertDTO("841-874-5879");
        List<PhoneAlertDTO> phoneAlertList = new ArrayList<>(Arrays.asList(phoneAlert1,phoneAlert2,phoneAlert3));

        when(fireStationService.getAllPhoneNumbersOfPersonsCoveredByOneFireStation(stationNumber)).thenReturn(phoneAlertList);

        mockMvc.perform(get("/phoneAlert?firestation={?}",stationNumber))
                .andExpect(status().isOk());
        verify(fireStationService).getAllPhoneNumbersOfPersonsCoveredByOneFireStation(anyString());
    }
    @Test
    public void getAnyPhoneNumbersOfPersonsCoveredByOneFireStationTest() throws Exception {
        String stationNumber = "3";
        PhoneAlertDTO phoneAlert1 = new PhoneAlertDTO("841-874-6512");
        PhoneAlertDTO phoneAlert2 = new PhoneAlertDTO("841-874-6544");
        PhoneAlertDTO phoneAlert3 = new PhoneAlertDTO("841-874-5879");

        when(fireStationService.getAllPhoneNumbersOfPersonsCoveredByOneFireStation(stationNumber)).thenReturn(null);

        mockMvc.perform(get("/phoneAlert?firestation={?}",stationNumber))
                .andExpect(status().isNotFound());
        verify(fireStationService).getAllPhoneNumbersOfPersonsCoveredByOneFireStation(anyString());
    }

    @Test
    public void getAllPersonsInfoFromAGivenFireStationNumberTest() throws Exception {
        String stationNumber = "3";
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation1 = new HomeListCoveredByOneFireStationDTO("John","Boyd","1509 Culver St","841-874-6512");
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation2 = new HomeListCoveredByOneFireStationDTO("Tenley","Boyd","1509 Culver St","841-874-3456");
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation3 = new HomeListCoveredByOneFireStationDTO("Roger","Boyd","1509 Culver St","841-874-2237");
        List<HomeListCoveredByOneFireStationDTO> homeListCoveredByOneFireStationList =
                new ArrayList<>(Arrays.asList(homeListCoveredByOneFireStation1,homeListCoveredByOneFireStation2,homeListCoveredByOneFireStation3));
        int totalChild = 2;
        int totalAdult = 1;
        FireStationDTO fireStationDTO = new FireStationDTO(homeListCoveredByOneFireStationList,totalAdult,totalChild);
        List<FireStationDTO> fireStationList = new ArrayList<>(List.of(fireStationDTO));

        when(fireStationService.getAllPersonsInfoFromAGivenFireStationNumber(anyString())).thenReturn(fireStationList);

        mockMvc.perform(get("/firestation?stationNumber={?}",stationNumber))
                .andExpect(status().isOk());
        verify(fireStationService).getAllPersonsInfoFromAGivenFireStationNumber(anyString());
    }
    @Test
    public void getAnyPersonInfoFromAGivenFireStationNumberTest() throws Exception {
        String stationNumber = "3";
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation1 = new HomeListCoveredByOneFireStationDTO("John","Boyd","1509 Culver St","841-874-6512");
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation2 = new HomeListCoveredByOneFireStationDTO("Tenley","Boyd","1509 Culver St","841-874-3456");
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation3 = new HomeListCoveredByOneFireStationDTO("Roger","Boyd","1509 Culver St","841-874-2237");
        List<HomeListCoveredByOneFireStationDTO> homeListCoveredByOneFireStationList =
                new ArrayList<>(Arrays.asList(homeListCoveredByOneFireStation1,homeListCoveredByOneFireStation2,homeListCoveredByOneFireStation3));
        int totalChild = 2;
        int totalAdult = 1;
        FireStationDTO fireStationDTO = new FireStationDTO(homeListCoveredByOneFireStationList,totalAdult,totalChild);

        when(fireStationService.getAllPersonsInfoFromAGivenFireStationNumber(anyString())).thenReturn(null);

        mockMvc.perform(get("/firestation?stationNumber={?}",stationNumber))
                .andExpect(status().isNotFound());
        verify(fireStationService).getAllPersonsInfoFromAGivenFireStationNumber(anyString());
    }
    @Test
    public void getPersonsByAddressFromListOfStation_NumberTest() throws Exception {
        String address = "1509 Culver St";
        String station_nbr1 = "1";
        String station_nbr2 = "2";
        String station_nbr3 = "3";
        List<String> station_numbers = new ArrayList<>(Arrays.asList(station_nbr1,station_nbr2,station_nbr3));
        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        List<String> medicalRecordsList2 = new ArrayList<>(Arrays.asList("aznol:250mg", "hydrapermazol:200mg", null));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Felicia","Boyd","841-874-6544",34,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(Arrays.asList(personListOfSameAddress1,personListOfSameAddress2));
        FloodDTO flood = new FloodDTO(personListOfSameAddressDTOList,address);
        List<FloodDTO> floodList = new ArrayList<>(List.of(flood));

        when(fireStationService.getPersonsByAddressFromListOfStation_Number(anyList())).thenReturn(floodList);

        mockMvc.perform(get("/flood/stations?stations={?}",station_numbers))
                .andExpect(status().isOk());
        verify(fireStationService).getPersonsByAddressFromListOfStation_Number(anyList());

    }
    @Test
    public void getAnyPersonByAddressFromListOfStation_NumberTest() throws Exception {
        String address = "1509 Culver St";
        String station_nbr1 = "1";
        String station_nbr2 = "2";
        String station_nbr3 = "3";
        List<String> station_numbers = new ArrayList<>(Arrays.asList(station_nbr1,station_nbr2,station_nbr3));
        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        List<String> medicalRecordsList2 = new ArrayList<>(Arrays.asList("aznol:250mg", "hydrapermazol:200mg", null));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Felicia","Boyd","841-874-6544",34,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(Arrays.asList(personListOfSameAddress1,personListOfSameAddress2));
        FloodDTO flood = new FloodDTO(personListOfSameAddressDTOList,address);

        when(fireStationService.getPersonsByAddressFromListOfStation_Number(anyList())).thenReturn(null);

        mockMvc.perform(get("/flood/stations?stations={?}",station_numbers))
                .andExpect(status().isNotFound());
        verify(fireStationService).getPersonsByAddressFromListOfStation_Number(anyList());

    }



}