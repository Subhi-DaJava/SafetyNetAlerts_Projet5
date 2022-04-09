package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    @MockBean
    private Repository repository;
    @MockBean
    private ReadFromJason_DAO readFromJason_dao;

    private List<Person> personList;
    @BeforeEach
    public void setUp(){
        Person person_1 = new Person("Uyghur", "SherqiyTurkestan", "11 12 Noyabir", "Urumqi", "1933-44", "09990991", "weten@gmail.com");
        Person person_2 = new Person("Memet", "Emet", "20 Nurbagh", "Ghulja", "909009", "200-123-568", "memet@gmail.com");
        personList = new ArrayList<>(Arrays.asList(person_1,person_2));

    }

    @Test
    void getAllTest() {

        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);

        List<Person> personList_result = personRepository.getAll();

        assertThat(personList_result).isEqualTo(personList);
    }

    @Disabled
    @Test
    void saveTest() {

        Person personSaved = new Person("Uyghur", "SherqiyTurkestan", "1 140 Noyabir", "Ghulja", "909009", "09990991", "weten@gmail.com");
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);

        boolean isSaved_result = personRepository.save(personSaved);

        assertThat(isSaved_result).isTrue();
    }

    @Test
    void deleteTest() {
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);
        boolean isDeleted_result = personRepository.delete(personList.get(0));

        assertThat(isDeleted_result).isTrue();
    }

    @Test
    void updateTest() {
        Person personUpdated = new Person("Uyghur", "SherqiyTurkestan", "1 140 Noyabir", "Ghulja", "909009", "09990991", "sherqiy@gmail.com");

        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);

        Person isUpdated_result = personRepository.update(0,personUpdated);

        assertThat(isUpdated_result).isEqualTo(personUpdated);
    }
    @Disabled
    @Test
    void updateFailureTest() {
        Person personSaved = new Person("Uyghur", "SherqiyTurkestan", "1 140 Noyabir", "Ghulja", "909009", "09990991", "sherqiy@gmail.com");

        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);

        Person isUpdated_result = personRepository.update(1,personSaved);

        assertThat(isUpdated_result).isNotEqualTo(personSaved);
    }

    @Test
    void getByTypeTest() {
        Person person = new Person("Memet", "Emet", "20 Nurbagh", "Ghulja", "909009", "200-123-568", "memet@gmail.com");

        List<Person> listPersonByAddress = new ArrayList<>(List.of(person));
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);

        List<Person> listPersonByAddress_result = personRepository.getByType("20 Nurbagh");

        assertThat(listPersonByAddress_result).isEqualTo(listPersonByAddress);
    }
    @Test
    void getByTypeFailureTest() {
        Person person = new Person("Memet", "Emet", "20 Nurbagh", "Ghulja", "909009", "200-123-568", "memet@gmail.com");

        List<Person> listPersonByAddress = new ArrayList<>(List.of(person));
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getPersons()).thenReturn(personList);

        List<Person> listPersonByAddress_result = personRepository.getByType("20 rue de Paris");

        assertThat(listPersonByAddress_result).isNotEqualTo(listPersonByAddress);
    }
}