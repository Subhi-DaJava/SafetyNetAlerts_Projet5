package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationServiceTest {
    @Autowired
    private FireStationService fireStationService;
    @MockBean
    private FireStationRepository fireStationRepository;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private MedicalRecordRepository medicalRecordRepository;
    @MockBean
    private SolutionFormatter solutionFormatter;

    private List<FireStation> fireStationList;
    @BeforeEach
    public void setUp() {
        FireStation fireStation1 = new FireStation("1509 Culver St", "3");
        FireStation fireStation2 = new FireStation("29 15th St", "2");
        FireStation fireStation3 = new FireStation("834 Binoc Ave", "4");
        FireStation fireStation4 = new FireStation("644 Gershwin Cir", "1");
        fireStationList = new ArrayList<>(Arrays.asList(fireStation1, fireStation2, fireStation3, fireStation4));

    }

    @Test
    public void getAllFireStationsTest(){
        Mockito.when(fireStationRepository.getAll()).thenReturn(fireStationList);
        Iterable<FireStation> allFireStation = fireStationService.getAllFireStations();
        assertEquals(allFireStation,fireStationList);
        verify(fireStationRepository).getAll();
    }
    @Test
    public void getAnyFireStationsTest(){
        Mockito.when(fireStationRepository.getAll()).thenReturn(null);
        Iterable<FireStation> allFireStation = fireStationService.getAllFireStations();
        assertNull(allFireStation);
        verify(fireStationRepository).getAll();
    }
    @Test
    public void saveAStationTest(){
        FireStation newFireStation = new FireStation("200 Culver St", "4");

        Mockito.when(fireStationRepository.save(newFireStation)).thenReturn(true);
        boolean isSaved = fireStationService.saveStation(newFireStation);
        assertTrue(isSaved);
        verify(fireStationRepository).save(any(FireStation.class));
    }
    @Test
    public void saveAnyStationTest(){
        FireStation newFireStation = new FireStation("200 Culver St", "4");

        Mockito.when(fireStationRepository.save(newFireStation)).thenReturn(false);
        boolean isSaved = fireStationService.saveStation(newFireStation);
        assertFalse(isSaved);
        verify(fireStationRepository).save(any(FireStation.class));
    }
    @Test
    public void deleteAFireStationTest(){
        FireStation fireStationDeleted = new FireStation("1509 Culver St", "3");
        Mockito.when(fireStationRepository.getAll()).thenReturn(fireStationList);
        Mockito.when(fireStationRepository.delete(fireStationDeleted)).thenReturn(true);
        boolean isDeleted = fireStationService.deleteStation(fireStationDeleted.getAddress());
        assertTrue(isDeleted);
        verify(fireStationRepository).delete(any(FireStation.class));
    }
    @Test
    public void deleteAnyFireStationTest(){
        FireStation fireStationDeleted = new FireStation("1509 Culver St", "3");
        Mockito.when(fireStationRepository.getAll()).thenReturn(fireStationList);
        Mockito.when(fireStationRepository.delete(fireStationDeleted)).thenReturn(false);
        boolean isDeleted = fireStationService.deleteStation(fireStationDeleted.getAddress());
        assertFalse(isDeleted);
        verify(fireStationRepository).delete(any(FireStation.class));
    }
    @Test
    public void deleteAFireStationFromEmptyFireStationsTest(){
        FireStation fireStationDeleted = new FireStation("1509 Culver St", "3");
        Mockito.when(fireStationRepository.getAll()).thenReturn(null);
        Mockito.when(fireStationRepository.delete(fireStationDeleted)).thenReturn(false);
        assertThrows(NullPointerException.class,()-> fireStationService.deleteStation(fireStationDeleted.getAddress()));
        verify(fireStationRepository,times(0)).delete(fireStationDeleted);
    }
    @Test
    public void deleteAFireStationFailuresTest(){
        boolean fireStationDeleted = fireStationService.deleteStation(null);
        assertFalse(fireStationDeleted);
        verify(fireStationRepository,times(0)).delete(any());
    }

    @Test
    public void updateAStationTest(){
        FireStation fireStationUpdating = new FireStation("1509 Culver St", "1");
        Mockito.when(fireStationRepository.getAll()).thenReturn(fireStationList);
        Mockito.when(fireStationRepository.update(0,fireStationUpdating)).thenReturn(fireStationUpdating);
        FireStation fireStationUpdated = fireStationService.updateStation("1509 Culver St",fireStationUpdating);
        assertEquals(fireStationUpdated,fireStationUpdating);
        verify(fireStationRepository).update(0,fireStationUpdating);
    }
    @Test
    public void updateAnyStationTest(){
        FireStation fireStationUpdating = new FireStation("15 Rue Paris", "1");
        Mockito.when(fireStationRepository.getAll()).thenReturn(fireStationList);
        Mockito.when(fireStationRepository.update(0,fireStationUpdating)).thenReturn(null);
        FireStation fireStationUpdated = fireStationService.updateStation("15 rue Paris",fireStationUpdating);
        assertNull(fireStationUpdated);
        verify(fireStationRepository,never()).update(0,fireStationUpdating);
    }
    @Test
    public void getAllAddressCoveredByOneFireStationTest(){
        FireStation fireStation1 = new FireStation("1509 Culver St", "3");
        FireStation fireStation3 = new FireStation("834 Binoc Ave", "3");
        List<FireStation> fireStationsByStation = new ArrayList<>(Arrays.asList(fireStation1,fireStation3));

        when(fireStationRepository.getByType("3")).thenReturn(fireStationsByStation);

        Iterable<FireStation> fireStations = fireStationService.getAllAddressCoveredByOneFireStation("3");
        assertEquals(fireStations,fireStationsByStation);
        verify(fireStationRepository).getByType(anyString());
    }
    @Test
    public void getAnyAddressCoveredByOneFireStationTest(){
        FireStation fireStation1 = new FireStation("1509 Culver St", "3");
        FireStation fireStation3 = new FireStation("834 Binoc Ave", "3");
        when(fireStationRepository.getByType(anyString())).thenReturn(null);

        Iterable<FireStation> fireStationsCoveredByOneStation = fireStationService.getAllAddressCoveredByOneFireStation("3");
        assertNull(fireStationsCoveredByOneStation);
        verify(fireStationRepository).getByType(anyString());
    }
    @Test
    public void getInfosOfPersonsLiveAtSameAddressTest(){
        List<String> medicalRecords = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg","nillacilan"));

        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));
        PersonListOfSameAddressDTO personListOfSameAddress =
                new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecords);

        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOS = new ArrayList<>(List.of(personListOfSameAddress));
        FireDTO fireDTOTested = new FireDTO(personListOfSameAddressDTOS,"3");

        Person personSameAddress = new Person("John","Boyd", "1509 Culver St","Culver","97451", "841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress = new ArrayList<>(List.of(personSameAddress));

        when(fireStationRepository.getAll()).thenReturn(fireStationList);
        when(personRepository.getByType(anyString())).thenReturn(personListByAddress);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37);

        FireDTO expectedFireDTO = fireStationService.getInfosOfPersonsLiveAtSameAddress("1509 Culver St");

        assertThat(fireDTOTested).isEqualTo(expectedFireDTO);

    }
    @Test
    public void getAnyInfosOfPersonsLiveAtSameAddressTest(){

        MedicalRecord medicalRecord= new MedicalRecord();
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));

        List<Person> personListByAddress = new ArrayList<>();

        when(fireStationRepository.getAll()).thenReturn(fireStationList);
        when(personRepository.getByType(anyString())).thenReturn(personListByAddress);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);

        FireDTO expectedFireDTO = fireStationService.getInfosOfPersonsLiveAtSameAddress(null);

        assertThat(expectedFireDTO).isNull();

    }

    @Test
    public void getAllPhoneNumbersOfPersonsCoveredByOneFireStationTest(){
        PhoneAlertDTO phoneAlert = new PhoneAlertDTO("841-874-6512");
        List<PhoneAlertDTO> phoneAlertList = new ArrayList<>(List.of(phoneAlert));
        FireStation fireStationByStation = new FireStation("1509 Culver St","3");
        List<FireStation> fireStationsListByStation = new ArrayList<>(List.of(fireStationByStation));

        Person person = new Person("John","Boyd", "1509 Culver St","Culver","97451", "841-874-6512","jaboyd@email.com");
        List<Person> personList = new ArrayList<>(List.of(person));

        when(fireStationRepository.getAll()).thenReturn(fireStationsListByStation);
        when(personRepository.getAll()).thenReturn(personList);

        List<PhoneAlertDTO> expectedPhoneAlert = fireStationService.getAllPhoneNumbersOfPersonsCoveredByOneFireStation("3");

        assertThat(phoneAlertList).isEqualTo(expectedPhoneAlert);

    }
    @Test
    public void getAnyPhoneNumbersOfPersonsCoveredByOneFireStationTest(){

        List<FireStation> fireStationsListByStation = new ArrayList<>();
        List<Person> personList = new ArrayList<>();

        when(fireStationRepository.getAll()).thenReturn(fireStationsListByStation);
        when(personRepository.getAll()).thenReturn(personList);

        List<PhoneAlertDTO> expectedPhoneAlert = fireStationService.getAllPhoneNumbersOfPersonsCoveredByOneFireStation("3");

        assertNull(expectedPhoneAlert);
    }

    @Test
    public void getAllPersonsInfoFromAGivenFireStationNumberTest(){

        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation_1 =
                new HomeListCoveredByOneFireStationDTO("John","Boyd","1509 Culver St", "841-874-6512");
        HomeListCoveredByOneFireStationDTO homeListCoveredByOneFireStation_2 =
                new HomeListCoveredByOneFireStationDTO("Tenley","Boyd","1509 Culver St", "841-874-6512");


        List<HomeListCoveredByOneFireStationDTO> homeListCoveredByOneFireStationDTOList =
                new ArrayList<>(Arrays.asList(homeListCoveredByOneFireStation_1,homeListCoveredByOneFireStation_2));

        FireStationDTO fireStationDTO = new FireStationDTO(homeListCoveredByOneFireStationDTOList,1,1);
        List<FireStationDTO> fireStationDTOList = new ArrayList<>(List.of(fireStationDTO));

        FireStation fireStationByStationNbr = new FireStation("1509 Culver St", "3");
        List<FireStation> fireStationListByStation = new ArrayList<>(List.of(fireStationByStationNbr));

        Person person1 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        Person person2 = new Person("Tenley","Boyd","1509 Culver St","Culver","97451","841-874-6512","tenz@email.com");
        List<Person> personList = new ArrayList<>(Arrays.asList(person1, person2));

        List<String> medications1 = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies1 = new ArrayList<>(List.of("nillacilan")); //"peanut"
        List<String> medications2 = new ArrayList<>();
        List<String> allergies2 = new ArrayList<>(List.of("peanut"));

        MedicalRecord medicalRecord1 = new MedicalRecord("John","Boyd","03/06/1984", medications1, allergies1);
        MedicalRecord medicalRecord2 = new MedicalRecord("Tenley","Boyd","02/18/2012", medications2, allergies2);
        List<MedicalRecord> medicalRecordList = new ArrayList<>(Arrays.asList(medicalRecord1,medicalRecord2));

        when(fireStationRepository.getByType(anyString())).thenReturn(fireStationListByStation);
        when(personRepository.getByType(anyString())).thenReturn(personList);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37).thenReturn(10);

        List<FireStationDTO> expectedFireStation = fireStationService.getAllPersonsInfoFromAGivenFireStationNumber("3");

        assertEquals(fireStationDTOList,expectedFireStation);
    }
    @Test
    public void getAnyPersonsInfoFromAGivenFireStationNumberTest(){

        List<FireStation> fireStationListByStation = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        when(fireStationRepository.getByType(anyString())).thenReturn(fireStationListByStation);
        when(personRepository.getByType(anyString())).thenReturn(personList);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(0).thenReturn(0);

        List<FireStationDTO> expectedFireStation = fireStationService.getAllPersonsInfoFromAGivenFireStationNumber("3");

        assertNull(expectedFireStation);
    }

    @Test
    public void getPersonsByAddressFromListOfStation_NumberTest(){
        List<String> station_numbers = new ArrayList<>(Arrays.asList("3","1"));
        FireStation fireStation_1 = new FireStation("1509 Culver St","3");
        List<FireStation> fireStationsByStationNbr_1 = new ArrayList<>(List.of(fireStation_1));
        FireStation fireStation_2 = new FireStation("644 Gershwin Cir","1");
        List<FireStation> fireStationsByStationNbr_2 = new ArrayList<>(List.of(fireStation_2));

        Person person1 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress_1 = new ArrayList<>(List.of(person1));
        Person person2 = new Person("Peter","Duncan","644 Gershwin Cir","Culver","97451","841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress_2 = new ArrayList<>(List.of(person2));

        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        PersonListOfSameAddressDTO personListOfSameAddress1 = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList_1 = new ArrayList<>(List.of(personListOfSameAddress1));
        List<String> medicalRecordsList2 = new ArrayList<>(List.of("shellfish"));
        PersonListOfSameAddressDTO personListOfSameAddress2 = new PersonListOfSameAddressDTO("Peter","Duncan","841-874-6512",21,medicalRecordsList2);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList_2 = new ArrayList<>(List.of(personListOfSameAddress2));

        List<String> medications_1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies_1 = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord_1 = new MedicalRecord("John","Boyd","03/06/1984",medications_1,allergies_1);

        List<String> medications_2 = new ArrayList<>();
        List<String> allergies_2 = new ArrayList<>(List.of("shellfish"));
        MedicalRecord medicalRecord_2 = new MedicalRecord("Peter","Duncan","09/06/2000",medications_2,allergies_2);

        List<MedicalRecord> medicalRecordList = new ArrayList<>(Arrays.asList(medicalRecord_1,medicalRecord_2));

        FloodDTO floodDTO_1 = new FloodDTO(personListOfSameAddressDTOList_1,"1509 Culver St");
        FloodDTO floodDTO_2 = new FloodDTO(personListOfSameAddressDTOList_2,"644 Gershwin Cir");
        List<FloodDTO> floodDTOList_Expected = new ArrayList<>(Arrays.asList(floodDTO_1,floodDTO_2));

        when(fireStationRepository.getByType(fireStation_1.getStation())).thenReturn(fireStationsByStationNbr_1);
        when(fireStationRepository.getByType(fireStation_2.getStation())).thenReturn(fireStationsByStationNbr_2);
        when(personRepository.getByType(person1.getAddress())).thenReturn(personListByAddress_1);
        when(personRepository.getByType(person2.getAddress())).thenReturn(personListByAddress_2);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37).thenReturn(21);

        List<FloodDTO> floodDTOSList_tested = fireStationService.getPersonsByAddressFromListOfStation_Number(station_numbers);

        assertThat(floodDTOSList_tested).isEqualTo(floodDTOList_Expected);

    }
    @Test
    public void getAnyPersonsByAddressFromListOfStation_NumberTest(){
        List<String> station_numbers = new ArrayList<>(Arrays.asList("3","1"));
        FireStation fireStation_1 = new FireStation("1509 Culver St","3");
        List<FireStation> fireStationsByStationNbr_1 = new ArrayList<>();
        FireStation fireStation_2 = new FireStation("644 Gershwin Cir","1");
        List<FireStation> fireStationsByStationNbr_2 = new ArrayList<>();

        Person person1 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress_1 = new ArrayList<>();
        Person person2 = new Person("Peter","Duncan","644 Gershwin Cir","Culver","97451","841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress_2 = new ArrayList<>();

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        when(fireStationRepository.getByType(fireStation_1.getStation())).thenReturn(fireStationsByStationNbr_1);
        when(fireStationRepository.getByType(fireStation_2.getStation())).thenReturn(fireStationsByStationNbr_2);
        when(personRepository.getByType(person1.getAddress())).thenReturn(personListByAddress_1);
        when(personRepository.getByType(person2.getAddress())).thenReturn(personListByAddress_2);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37).thenReturn(21);

        List<FloodDTO> floodDTOSList_tested = fireStationService.getPersonsByAddressFromListOfStation_Number(station_numbers);

        assertThat(floodDTOSList_tested).isNull();

    }



}