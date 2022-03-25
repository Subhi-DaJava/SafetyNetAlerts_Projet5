package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isOk());
                       // .andExpect(jsonPath("$[0].firstName",is("John")))

        verify(personService).getAllPersons();

    }

    @Test
    public void addPerson() {
    }

    @Test
    public void updatePerson() {
    }

    @Test
    public void deletePerson() {
    }

}