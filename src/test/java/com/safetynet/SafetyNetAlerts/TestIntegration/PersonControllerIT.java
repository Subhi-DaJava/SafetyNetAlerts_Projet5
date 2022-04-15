package com.safetynet.SafetyNetAlerts.TestIntegration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.DataJSONConverter;
import com.safetynet.SafetyNetAlerts.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/*
 * @SpringBootTest et @AutoConfigureMockMvc permettent de charger le contexte Spring et de réaliser des requêtes sur le controller.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    Repository repository;

    @BeforeEach
    public void setUp() throws IOException {
        String fileJson = "C:/Users/asus/openClassRoomsIntelliJ/P5_SprintBoot/Projet_P5/SafetyNetProjet/SafetyNet-Alerts/src/test/java/ressource/data.json";
        repository = objectMapper.readValue(new File(fileJson), Repository.class);
    }
    @AfterEach
    public void tearDown(){
        repository.getPersons().clear();
        repository.getFirestations().clear();
        repository.getMedicalrecords().clear();
    }

    /**
     *  jsonPath("$[0].firstName", is("John")) :
     *  $ pointe sur la racine de la structure JSON.
     *  [0] indique qu’on veut vérifier le premier élément de la liste.
     *  firstName désigne l’attribut qu’on veut consulter.
     *  is(“Jhon”) est ce que l’on attend comme résultat.
     *  Etc
     */
    @Test
    public void getAllPersonTest() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName",is("John")))
                .andExpect(jsonPath("$[5].lastName",is("Marrack")));

    }
    @Test
    public void addPersonTest() throws Exception {
    mockMvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                    new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com"))))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));

    }

    @Test
    public void deleteAPersonTest() throws Exception {
        mockMvc.perform(delete("/person/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new Person("John","Boyd","6 Rue des ailes","Lyon","72000","520-620-8889","subhi@gmail.com"))))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteAnyPersonTest() throws Exception {
        mockMvc.perform(delete("/person/Xavier/Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new Person("John","Boyd","6 Rue des ailes","Lyon","72000","520-620-8889","subhi@gmail.com"))))
                .andExpect(status().isNotFound());
    }
    @Test
    public void updateAPersonTest() throws Exception {
        mockMvc.perform(put("/person/{firstName}/{lastName}","John","Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new Person("John","Boyd","2 Rue des ailes","Lyon","75000","520-620-8889","subhi@gmail.com"))))
                .andExpect(status().isOk());
    }
    @Test
    public void updateAnyPersonTest() throws Exception {
        mockMvc.perform(put("/person/{firstName}/{lastName}","Wilyam","Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new Person("John","Boyd","2 Rue des ailes","Lyon","75000","520-620-8889","subhi@gmail.com"))))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getByAddressTest() throws Exception {
        mockMvc.perform(get("/person/{address}","1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName",is("John")))
                .andExpect(jsonPath("$[4].firstName",is("Felicia")));
    }

    @Test
    public void getAllEmailsOfGivenCityTest() throws Exception {
        mockMvc.perform(get("/communityEmail?city={city}","Culver"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void getInformationOfSameFamilyTest() throws Exception {
        mockMvc.perform(get("/personInfo?firstName={firstName}&lastName={lastName}","Lily","Cooper"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
