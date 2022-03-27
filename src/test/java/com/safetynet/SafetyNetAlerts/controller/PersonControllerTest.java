package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    /*
    La méthode perform prend en paramètre l’instruction get(“/person”). On exécute donc une requête GET sur l’URL /person.
    L’instruction .andExpect(status().isOk()) indique qu'une réponse HTTP 200.
    */
    @Test
    public void testGetAllPerson() throws Exception {
        mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(0)));

        verify(personService).getAllPersons();

    }
    @Test
    public void testGetAnyPerson() throws Exception {
        when(personService.getAllPersons()).thenReturn(null);
        mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(personService).getAllPersons();

    }

    @Test
    public void testAddPerson() throws Exception {
        String mappingJson = "{\"firstName\":\"Subhi\", \"lastName\":\"Yari\",\"address\":\"7 rue Narcissism\"," +
                "\"city\":\"Paris\",\"zip\":\"75007\",\"phone\":\"841-874-3002\",\"email\":\"paris@gmail.com\"}";
        when(personService.savePerson(any(Person.class))).thenReturn(true);
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mappingJson))
                .andExpect(status().isOk())
                .andDo(print());
               // .andExpect(jsonPath("$[0].firstName",is("Subhi")));

        verify(personService).savePerson(any(Person.class));

    }
    @Test
    public void testAddAnyPerson() throws Exception {
        String mappingJson = "{\"firstName\":\"\", \"lastName\":\"\",\"address\":\"7 rue Narcissism\"," +
                "\"city\":\"Paris\",\"zip\":\"75007\",\"phone\":\"841-874-3002\",\"email\":\"paris@gmail.com\"}";
        when(personService.savePerson(any(Person.class))).thenReturn(false);
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappingJson))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(personService).savePerson(any(Person.class));

    }

    @Test
    public void updatePerson() throws Exception {

        String mappingJson = "{\"firstName\":\"Subhi\", \"lastName\":\"Yari\",\"address\":\"7 rue Narcissism\"," +
                "\"city\":\"Paris\",\"zip\":\"75007\",\"phone\":\"841-874-3002\",\"email\":\"paris@gmail.com\"}";
        Person p = new Person("Subhi","Yari","6 Rue des ailes","Lyon","72000","520-620-8889","subhi@gmail.com");
        when(personService.updatePerson(any(Person.class))).thenReturn(p);
        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappingJson))
                        .andExpect(status().isOk())
                        .andDo(print());
        verify(personService, atLeast(1)).updatePerson(any(Person.class));
    }
    @Test
    public void updateAnyPerson() throws Exception {
        String map = "{}";
        when(personService.updatePerson(any(Person.class))).thenReturn(null);
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(map))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(personService).updatePerson(any(Person.class));
    }

    @Test
    public void deletePerson() throws Exception {

        when(personService.deletePerson(anyString(),anyString())).thenReturn(true);
        mockMvc.perform(delete("/person/firstName/lastName")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(personService).deletePerson(anyString(),anyString());
    }
    @Test
    public void deleteAnyPerson() throws Exception {

        when(personService.deletePerson(anyString(),anyString())).thenReturn(false);
        mockMvc.perform(delete("/person/firstName/lastName")
                        .contentType(MediaType.APPLICATION_JSON))
                         .andDo(print())
                         .andExpect(status().isNotFound());
        verify(personService).deletePerson(anyString(),anyString());
    }
    @Test
    public void getByAddress() throws Exception {
        when(personService.getByAddress(anyString())).thenReturn(anyIterable());

        mockMvc.perform(get("/person/address"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(0)));
        verify(personService).getByAddress(anyString());
    }

    @Test
    public void getByAddressFailure() throws Exception {
        when(personService.getByAddress(anyString())).thenReturn(null);

        mockMvc.perform(get("/person/address"))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(personService).getByAddress(anyString());
    }



}