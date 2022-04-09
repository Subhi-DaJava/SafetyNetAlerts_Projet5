package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.ChildAlertDTO;
import com.safetynet.SafetyNetAlerts.dto.ChildInfos;
import com.safetynet.SafetyNetAlerts.dto.PersonListOfSameAddressDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordServiceTest {
    @Autowired
    private MedicalRecordService medicalRecordService;
    @MockBean
    private MedicalRecordRepository medicalRecordRepository;
    @MockBean
    private PersonRepository personRepository;

    @MockBean
    SolutionFormatter solutionFormatter;
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
    void getAllMedicalRecordsTest() {
        Iterable<MedicalRecord> allMedicalRecords_result;
        Mockito.when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        allMedicalRecords_result = medicalRecordService.getAllMedicalRecords();
        assertThat(allMedicalRecords_result).isEqualTo(medicalRecordList);
        Mockito.verify(medicalRecordRepository).getAll();
    }
    @Test
    void getAnyMedicalRecordsTest() {
        Iterable<MedicalRecord> allMedicalRecords_result;
        Mockito.when(medicalRecordRepository.getAll()).thenReturn(null);
        allMedicalRecords_result = medicalRecordService.getAllMedicalRecords();
        assertThat(allMedicalRecords_result).isNull();
        Mockito.verify(medicalRecordRepository).getAll();
    }

    @Test
    void saveAMedicalRecordTest() {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(true);
        boolean isSaved_result = medicalRecordService.saveMedicalRecord(medicalRecord);
        assertThat(isSaved_result).isTrue();
        verify(medicalRecordRepository).save(any(MedicalRecord.class));
    }
    @Test
    void saveAnyMedicalRecordTest() {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(false);
        boolean isSaved_result = medicalRecordService.saveMedicalRecord(medicalRecord);
        assertThat(isSaved_result).isFalse();
        verify(medicalRecordRepository).save(any(MedicalRecord.class));
    }
    @Test
    void deleteAMedicalRecordTest() {
        String firstName = "François";
        String lastName = "Dumas";
        List<String> medications3 = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies3 = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord3= new MedicalRecord("François","Dumas","26/02/1996",medications3,allergies3);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(medicalRecordRepository.delete(medicalRecord3)).thenReturn(true);

        boolean isDeleted_result = medicalRecordService.deleteMedicalRecord(firstName,lastName);

        assertThat(isDeleted_result).isTrue();
        verify(medicalRecordRepository).delete(any(MedicalRecord.class));
    }
    @Test
    void deleteAnyMedicalRecordTest() {
        String firstName = "Roger";
        String lastName = "Filons";
        List<String> medications3 = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies3 = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord3= new MedicalRecord("Roger","Filons","26/02/1996",medications3,allergies3);

        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(medicalRecordRepository.delete(medicalRecord3)).thenReturn(false);

        boolean isDeleted_result = medicalRecordService.deleteMedicalRecord(firstName,lastName);

        assertThat(isDeleted_result).isFalse();
        verify(medicalRecordRepository, never()).delete(medicalRecord3);
    }

    @Test
    void updateAMedicalRecordTest() {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:250mg","hydrapermazol:200mg"));
        List<String> allergies = new ArrayList<>(Arrays.asList("nillacilan","allergie"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(medicalRecordRepository.update(0,medicalRecord)).thenReturn(medicalRecord);

        MedicalRecord updatedMedicalRecord_result = medicalRecordService.updateMedicalRecord("John","Boyd",medicalRecord);

        assertThat(updatedMedicalRecord_result).isEqualTo(medicalRecord);

        verify(medicalRecordRepository).update(0,medicalRecord);
    }
    @Test
    void updateAMedicalRecordFailureTest() {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:250mg","hydrapermazol:200mg"));
        List<String> allergies = new ArrayList<>(Arrays.asList("nillacilan","allergie"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(medicalRecordRepository.update(0,medicalRecord)).thenReturn(null);

        MedicalRecord updatedMedicalRecord_result = medicalRecordService.updateMedicalRecord("François","Dupont",medicalRecord);

        assertThat(updatedMedicalRecord_result).isEqualTo(null);

        verify(medicalRecordRepository,never()).update(0,medicalRecord);
    }


    @Test
    void updateAnyMedicalRecordTest() {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:250mg","hydrapermazol:200mg"));
        List<String> allergies = new ArrayList<>(Arrays.asList("nillacilan","allergie"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(medicalRecordRepository.update(0,medicalRecord)).thenReturn(null);

        MedicalRecord updatedMedicalRecord_result = medicalRecordService.updateMedicalRecord("John","Boyd",medicalRecord);

        assertThat(updatedMedicalRecord_result).isNull();

        verify(medicalRecordRepository).update(0,medicalRecord);
    }


    @Test
    void getMedicalRecordsBySameFamilyNameTest() {
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:250mg","hydrapermazol:200mg"));
        List<String> allergies = new ArrayList<>(Arrays.asList("nillacilan","allergie"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        List<MedicalRecord> medicalRecordsByLastName = new ArrayList<>(List.of(medicalRecord));

        when(medicalRecordRepository.getByType("Boyd")).thenReturn(medicalRecordsByLastName);

        List<MedicalRecord> medicalRecordsBySameFamilyName_result = medicalRecordService.getMedicalRecordsBySameFamilyName("Boyd");
        assertThat(medicalRecordsBySameFamilyName_result).isEqualTo(medicalRecordsByLastName);

        verify(medicalRecordRepository).getByType("Boyd");
    }
    @Test
    void getAnyMedicalRecordBySameFamilyNameTest() {

        when(medicalRecordRepository.getByType(anyString())).thenReturn(null);

        List<MedicalRecord> medicalRecordsBySameFamilyName = medicalRecordService.getMedicalRecordsBySameFamilyName(anyString());
        assertThat(medicalRecordsBySameFamilyName).isNull();

        verify(medicalRecordRepository).getByType(anyString());
    }

    @Test
    void getChildAndHisFamilyMemberByAGivenAddressTest() {
        ChildInfos childInfos_1 = new ChildInfos("Tenley","Boyd",10);
        ChildInfos childInfos_2 = new ChildInfos("Roger","Boyd",4);
        List<ChildInfos> childInfosList = new ArrayList<>(Arrays.asList(childInfos_1,childInfos_2));
        Person person1 = new Person("Tenley","Boyd","1509 Culver St","Culver","97451","841-874-6512","tenz@email.com");
        Person person2 = new Person("Roger","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        Person person3 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress = new ArrayList<>(Arrays.asList(person1,person2,person3));

        List<String> medications_1 = new ArrayList<>();
        List<String> allergies_1 = new ArrayList<>(List.of("peanut"));
        List<String> medications_2 = new ArrayList<>();
        List<String> allergies_2 = new ArrayList<>();
        List<String> medications_3 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies_3 = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord_1 = new MedicalRecord("Tenley","Boyd","02/18/2012",medications_1,allergies_1);
        MedicalRecord medicalRecord_2 = new MedicalRecord("Roger","Boyd","09/06/2017",medications_2,allergies_2);
        MedicalRecord medicalRecord_3 = new MedicalRecord("John","Boyd","03/06/1984",medications_3,allergies_3);
        List<MedicalRecord> medicalRecordListByAddress = new ArrayList<>(Arrays.asList(medicalRecord_1,medicalRecord_2,medicalRecord_3));


        List<String> medicalRecordsList1 = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg", "nillacilan"));
        PersonListOfSameAddressDTO personListOfSameAddress = new PersonListOfSameAddressDTO("John","Boyd","841-874-6512",37,medicalRecordsList1);
        List<PersonListOfSameAddressDTO> personListOfSameAddressDTOList = new ArrayList<>(List.of(personListOfSameAddress));

        ChildAlertDTO childAlertExpected = new ChildAlertDTO(childInfosList,personListOfSameAddressDTOList);

        when(personRepository.getByType("1509 Culver St")).thenReturn(personListByAddress);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordListByAddress);
        when(solutionFormatter.formatterStringToDate("02/18/2012")).thenReturn(10);
        when(solutionFormatter.formatterStringToDate("09/06/2017")).thenReturn(4);
        when(solutionFormatter.formatterStringToDate("03/06/1984")).thenReturn(37);

        ChildAlertDTO childAlertDTO_result = medicalRecordService.getChildAndHisFamilyMemberByAGivenAddress("1509 Culver St");

        assertThat(childAlertDTO_result).isEqualTo(childAlertExpected);

    }
    @Test
    void getAnyChildAndHisFamilyMemberByAGivenAddressTest() {
        List<Person> personListByAddress = new ArrayList<>();
        List<MedicalRecord> medicalRecordListByAddress = new ArrayList<>();

        when(personRepository.getByType(anyString())).thenReturn(personListByAddress);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordListByAddress);

        ChildAlertDTO childAlertDTO_result = medicalRecordService.getChildAndHisFamilyMemberByAGivenAddress(anyString());

        assertThat(childAlertDTO_result).isNull();

    }
    @Test
    void getChildAndHisFamilyMemberByAGivenAddressReturnEmptyTest() {
        Person person = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        List<Person> personListByAddress = new ArrayList<>(List.of(person));
        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord = new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        List<MedicalRecord> medicalRecordListByAddress = new ArrayList<>(List.of(medicalRecord));
        List<ChildInfos> childInfosList = new ArrayList<>();

        when(personRepository.getByType(anyString())).thenReturn(personListByAddress);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordListByAddress);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37);

        ChildAlertDTO childAlertDTO_result = medicalRecordService.getChildAndHisFamilyMemberByAGivenAddress(anyString());

        assertThat(childAlertDTO_result.getChildInfos()).isEqualTo(childInfosList);

    }
}