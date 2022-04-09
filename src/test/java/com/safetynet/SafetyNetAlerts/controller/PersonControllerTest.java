package com.safetynet.SafetyNetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonInfoDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * L'objectif est de tester les controllers ; Ces derniers seront appelés à travers des URL(Endpoints) par les programmes qui communiqueront avec.
 */
//@WebMvcTest(controllers = PersonController.class) déclenche le mécanisme permettant de tester le(les) controller(s).

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {
    //L’attribut mockMvc permet d’appeler la méthode “perform” qui déclenche la requête.
    @Autowired
    private MockMvc mockMvc;
    //La méthode du controller exécutée par l’appel de “/person” utilise PersonService.
    @MockBean
    private PersonService personService;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){

    }
    /*
    La méthode perform prend en paramètre l’instruction get(“/person”). On exécute donc une requête GET sur l’URL /person.
    L’instruction .andExpect(status().isOk()) indique qu'une réponse HTTP 200.
    */
    @Test
    public void getAllPersonTest() throws Exception {
        Person person1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");
        Person person2 = new Person("Memet", "Emet", "25 dolet bagh", "Ghulja", "999999", "1111-33-44", "memet99@gmail.com");
        List<Person> personList = new ArrayList<>(Arrays.asList(person1, person2));
        Mockito.when(personService.getAllPersons()).thenReturn(personList);
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(2)))
                .andExpect(jsonPath("$[0].firstName",is("Uyghur")));

        verify(personService).getAllPersons();

    }
    @Test
    public void getAnyPersonTest() throws Exception {

        when(personService.getAllPersons()).thenReturn(null);

        mockMvc.perform(get("/person"))
                .andExpect(status().isNotFound());

        verify(personService).getAllPersons();

    }

    @Test
    public void addAPersonTest() throws Exception {

        Person person2 = new Person("Memet", "Emet", "25 dolet bagh", "Ghulja", "999999", "1111-33-44", "memet99@gmail.com");

        when(personService.savePerson(person2)).thenReturn(true);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person2)))
                .andExpect(status().isCreated());

        verify(personService).savePerson(person2);

    }
    @Test
    public void addAnyPersonTest() throws Exception {
        Person person1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");

        when(personService.savePerson(person1)).thenReturn(false);
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person1)))
                .andExpect(status().isNotFound());

        verify(personService).savePerson(any(Person.class));
    }

    @Test
    public void updateAPersonTest() throws Exception {
        Person personUpdated = new Person("Memet","Emet","6 Rue des ailes","Lyon","72000","520-620-8889","subhi@gmail.com");

        when(personService.updatePerson(anyString(),anyString(),any(Person.class))).thenReturn(personUpdated);

        mockMvc.perform(put("/person/Memet/Emet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personUpdated)))
                        .andExpect(status().isOk());
        verify(personService, atLeast(1)).updatePerson("Memet","Emet",personUpdated);
    }
    @Test
    public void updateAnyPersonTest() throws Exception {
        Person personUpdated = new Person("Memet","Emet","6 Rue des ailes","Lyon","72000","520-620-8889","subhi@gmail.com");
        when(personService.updatePerson("Memet", "Emet",personUpdated)).thenReturn(null);
        mockMvc.perform(put("/person/Memet/Emet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personUpdated)))
                .andExpect(status().isNotFound());
        verify(personService).updatePerson("Memet","Emet",personUpdated);
    }

    @Test
    public void deleteAPersonTest() throws Exception {
        Person personDeleted = new Person("Memet", "Emet", "25 dolet bagh", "Ghulja", "999999", "1111-33-44", "memet99@gmail.com");
        when(personService.deletePerson(anyString(),anyString())).thenReturn(true);
        mockMvc.perform(delete("/person/Memet/Emet"))
                .andExpect(status().isOk());
        verify(personService).deletePerson(anyString(),anyString());
    }
    @Test
    public void deleteAnyPersonTest() throws Exception {

        when(personService.deletePerson("Memet","Emet")).thenReturn(false);
        mockMvc.perform(delete("/person/Memet/Emet"))
                         .andExpect(status().isNotFound());
        verify(personService).deletePerson("Memet","Emet");
    }
    @Test
    public void getByAddressTest() throws Exception {
        Person person1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");
        List<Person> personList = new ArrayList<>(List.of(person1));

        when(personService.getByAddress(person1.getAddress())).thenReturn(personList);

        mockMvc.perform(get("/person/11 12 Noyabir"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName",is("Uyghur")));
        verify(personService).getByAddress(person1.getAddress());
    }

    @Test
    public void getByAddressFailureTest() throws Exception {
        when(personService.getByAddress(anyString())).thenReturn(null);

        mockMvc.perform(get("/person/address"))
                .andExpect(status().isNotFound());
        verify(personService).getByAddress(anyString());
    }
    @Test
    public void getAllEmailsOfGivenCityTest() throws Exception {
        Person person1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");
        CommunityEmailDTO emailDTO = new CommunityEmailDTO(person1.getEmail());
        List<CommunityEmailDTO> emailList = new ArrayList<>(List.of(emailDTO));

        when(personService.getAllEmailsFromAGivenCity(anyString())).thenReturn(emailList);
        mockMvc.perform(get("/communityEmail?city={?}","Urumqi"))
                .andExpect(status().isOk());
        verify(personService).getAllEmailsFromAGivenCity(anyString());

    }
    @Test
    public void getAnyEmailOfGivenCityTest() throws Exception {
        Person person1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");

        CommunityEmailDTO emailDTO = new CommunityEmailDTO(person1.getEmail());

        when(personService.getAllEmailsFromAGivenCity(anyString())).thenReturn(null);
        mockMvc.perform(get("/communityEmail?city={?}","Urumqi"))
                .andExpect(status().isNotFound());
        verify(personService).getAllEmailsFromAGivenCity(anyString());

    }
    @Test
    public void getInformationOfSameNameTest() throws Exception {

        Set<String> medicalRecord = new HashSet<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg","nillacilan"));

        PersonInfoDTO personInfo = new PersonInfoDTO("Memet","Emet","25 dolet bagh",15,"memet99@gmail.com",medicalRecord);
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>(List.of(personInfo));

        when(personService.getInformationOfSameFamily(anyString(),anyString())).thenReturn(personInfoDTOList);

        mockMvc.perform(get("/personInfo?firstName={?}&lastName={?}","Memet","Emet"))
                .andExpect(status().isOk());
        verify(personService).getInformationOfSameFamily(anyString(),anyString());
    }
    @Test
    public void getAnyInformationOfSameNameTest() throws Exception {

        Set<String> medicalRecord = new HashSet<>(Arrays.asList("aznol:350mg","hydrapermazol:100mg","nillacilan"));

        PersonInfoDTO personInfo = new PersonInfoDTO("Memet","Emet","25 dolet bagh",15,"memet99@gmail.com",medicalRecord);

        when(personService.getInformationOfSameFamily(anyString(),anyString())).thenReturn(null);

        mockMvc.perform(get("/personInfo?firstName={?}&lastName={?}","Memet","Emet"))
                .andExpect(status().isNotFound());
        verify(personService).getInformationOfSameFamily(anyString(), anyString());
    }



}