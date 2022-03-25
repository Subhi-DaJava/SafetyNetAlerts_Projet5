package com.safetynet.SafetyNetAlerts.TestIntegration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
//cette n'est pas détecté automatiquement
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
 * @SpringBootTest et @AutoConfigureMockMvc permettent de charger le contexte Spring et de réaliser des requêtes sur le controller.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    /**
     *  jsonPath("$[0].firstName", is("John")) :
     *  $ pointe sur la racine de la structure JSON.
     *  [0] indique qu’on veut vérifier le premier élément de la liste.
     *  firstName désigne l’attribut qu’on veut consulter.
     *  is(“Jhon”) est ce que l’on attend comme résultat.
     *  Etc
     */
    @Test
    public void testGetAllPerson() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName",is("John")))
                .andExpect(jsonPath("$[5].lastName",is("Marrack")))
                .andExpect(jsonPath("$[0].email",is("jaboyd@email.com")));

    }
}
