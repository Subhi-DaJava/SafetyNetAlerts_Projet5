package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonInfoDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.util.SolutionFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private MedicalRecordRepository medicalRecordRepository;
    @MockBean
    private SolutionFormatter solutionFormatter;

    private List<Person> personList;
    @BeforeEach
    public void setUp(){
        Person person1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");
        Person person2 = new Person("Memet", "Emet", "25 dolet bagh", "Ghulja", "999999", "1111-33-44", "memet99@gmail.com");
        Person person3 = new Person("Jean", "Dupont", "2 rue des halles", "Paris", "75015", "06-85-96-23-57", "jdupont@gmail.com");
        Person person4 = new Person("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        personList = new ArrayList<>(Arrays.asList(person1, person2,person3,person4));
    }

    @Test
    void getAllPersonsTest() {

        when(personRepository.getAll()).thenReturn(personList);

        Iterable<Person> persons = personService.getAllPersons();

        assertEquals(persons, personList);

    }
    @Test
    void getAnyPersonTest(){
        when(personRepository.getAll()).thenReturn(null);

        Iterable<Person> persons = personService.getAllPersons();

        assertNull(persons);
    }

    @Test
    void saveAPersonTest() {
        Person person3 = new Person("Jean", "Dupont", "2 rue des halles", "Paris", "75015", "06-85-96-23-57", "jdupont@gmail.com");
        when(personRepository.save(person3)).thenReturn(true);
        boolean isSaved = personService.savePerson(person3);
        assertTrue(isSaved);
        verify(personRepository).save(any(Person.class));
    }
    @Test
    void saveAnyPersonTest() {
        Person person3 = new Person("Jean", "Dupont", "2 rue des halles", "Paris", "75015", "06-85-96-23-57", "jdupont@gmail.com");
        when(personRepository.save(person3)).thenReturn(false);
        boolean isSaved = personService.savePerson(person3);
        assertFalse(isSaved);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void deleteAPersonTest() {
        Person person3 = new Person("Memet", "Emet", "25 dolet bagh", "Ghulja", "999999", "1111-33-44", "memet99@gmail.com");
        when(personRepository.getAll()).thenReturn(personList);
        when(personRepository.delete(person3)).thenReturn(true);
        boolean isDeleted = personService.deletePerson(person3.getFirstName(),person3.getLastName());
        assertTrue(isDeleted);
    }
    @Test
    void deleteAnyPersonTest() {
        Person person3 = new Person("Emet", "Memet", "25 dolet bagh", "Ghulja", "999999", "1111-33-44", "memet99@gmail.com");
        when(personRepository.getAll()).thenReturn(personList);
        when(personRepository.delete(person3)).thenReturn(false);
        boolean isDeleted = personService.deletePerson(person3.getFirstName(),person3.getLastName());
        assertFalse(isDeleted);
    }

    @Test
    void updateAPersonTest() {
        Person personUpdated = new Person("Jean", "Dupont", "6 rue de Paris", "Paris", "75001", "07-55-38-41-80", "jdupont@hotmail.fr");

        when(personRepository.getAll()).thenReturn(personList);
        when(personRepository.update(2, personUpdated)).thenReturn(personUpdated);
        Person isPersonUpdated = personService.updatePerson("Jean","Dupont",personUpdated);
        assertEquals(isPersonUpdated,personUpdated);
    }
    @Test
    void updateAnyPersonTest() {
        Person personUpdate = new Person("Jean", "Dupont", "6 rue de Paris", "Paris", "75001", "07-55-38-41-80", "jdupont@hotmail.fr");

        when(personRepository.update(2, personUpdate)).thenReturn(null);
        Person isPersonUpdated = personService.updatePerson("Jean","Dupont",personUpdate);
        assertNull(isPersonUpdated);
    }

    @Test
    void getByAddressTest() {
        String address = "11 12 Noyabir";
        Person person = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");

        List<Person> listPersonByAddress = new ArrayList<>(List.of(person));

        when(personRepository.getByType(address)).thenReturn(listPersonByAddress);
        Iterable<Person> persons_result = personService.getByAddress(address);
        assertEquals(persons_result,listPersonByAddress);

    }
    @Test
    void getByAddressAnyPersonTest() {
        String address = "15 azat kocha";
        Person person = new Person("Uyghur", "SherqiyTurkestan", "15 azat kocha", "Urumqi", "1933-44", "09990991", "weten@gmail.com");
        List<Person> listPersonByAddress = new ArrayList<>(List.of(person));

        when(personRepository.getByType(address)).thenReturn(listPersonByAddress);
        Iterable<Person> persons_result = personService.getByAddress(address);
        assertEquals(persons_result,listPersonByAddress);
    }

    @Test
    public void getAllEmailsFromAGivenCityTest(){
        CommunityEmailDTO email_1 = new CommunityEmailDTO("weten@gmail.com");
        List<CommunityEmailDTO> emailList = new ArrayList<>(List.of(email_1));
        when(personRepository.getAll()).thenReturn(personList);
        List<CommunityEmailDTO> emailDTOList_result = personService.getAllEmailsFromAGivenCity("Urumqi");

        assertEquals(emailDTOList_result,emailList);
        verify(personRepository).getAll();
    }
    @Test
    public void getAnyEmailsFromAGivenCityTest(){

        when(personRepository.getAll()).thenReturn(personList);
        List<CommunityEmailDTO> emailDTOList_result = personService.getAllEmailsFromAGivenCity("Lyon");

        assertThat(emailDTOList_result).isNull();
        verify(personRepository).getAll();
    }

    @Test
    public void getInformationOfSameFamilyTest(){
        Set<String> medicalRecords = new HashSet<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg","nillacilan"));
        PersonInfoDTO personInfo =
                new PersonInfoDTO("John","Boyd","1509 Culver St",37,"jaboyd@email.com",medicalRecords);
        List<PersonInfoDTO> personInfoList = new ArrayList<>(List.of(personInfo));

        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));

        when(personRepository.getAll()).thenReturn(personList);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37);

        List<PersonInfoDTO> expectedPersonInfoList = personService.getInformationOfSameFamily("John","Boyd");
        assertThat(expectedPersonInfoList).isEqualTo(personInfoList);

        verify(personRepository).getAll();
        verify(medicalRecordRepository).getAll();
        verify(solutionFormatter).formatterStringToDate(anyString());
    }

    @Test
    public void getAnyInformationOfSameFamilyTest(){
        Set<String> medicalRecords = new HashSet<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg","nillacilan"));
        PersonInfoDTO personInfo =
                new PersonInfoDTO("John","Boyd","1509 Culver St",37,"jaboyd@email.com",medicalRecords);
        List<PersonInfoDTO> personInfoList = new ArrayList<>(List.of(personInfo));

        List<String> medications = new ArrayList<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        MedicalRecord medicalRecord= new MedicalRecord("John","Boyd","03/06/1984",medications,allergies);
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));

        when(personRepository.getAll()).thenReturn(personList);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecordList);
        when(solutionFormatter.formatterStringToDate(anyString())).thenReturn(37);

        List<PersonInfoDTO> expectedPersonInfoList = personService.getInformationOfSameFamily("Jean","Dupont");
        assertThat(expectedPersonInfoList).isNotEqualTo(personInfoList);

        verify(personRepository).getAll();
        verify(medicalRecordRepository).getAll();
        verify(solutionFormatter, never()).formatterStringToDate(anyString());
    }


}