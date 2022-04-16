package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationRepositoryTest {
    @Autowired
    private FireStationRepository fireStationRepository;
    @MockBean
    private Repository repository;
    @MockBean
    private DataJSONConverter readFromJason_dao;

    private List<FireStation> fireStationList ;

    @BeforeEach
    public void setUp(){
        FireStation fireStation_1 = new FireStation("5 rue Jean Jaures","1");
        FireStation fireStation_2 = new FireStation("10 Rue Victor Hugo","2");
        fireStationList = new ArrayList<>(Arrays.asList(fireStation_1,fireStation_2));
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getFirestations()).thenReturn(fireStationList);
    }
    @AfterEach
    public void tearDown(){
        fireStationRepository.setFireStations(fireStationList);
    }

    @Test
    void getAllTest() {
        List<FireStation> allFireStations = fireStationRepository.getAll();

        assertThat(allFireStations).isEqualTo(fireStationList);
    }

    @Test
    void deleteTest() {
        FireStation fireStationDeleted = new FireStation("5 rue Jean Jaures","1");
        boolean isDeleted = fireStationRepository.delete(fireStationDeleted);
        assertThat(isDeleted).isTrue();
    }

    @Test
    void getByTypeTest() {
        FireStation fireStation = new FireStation("5 rue Jean Jaures","1");
        List<FireStation> fireStationsByStationNbr = new ArrayList<>(List.of(fireStation));

        List<FireStation> fireStationListByStationsNbr = fireStationRepository.getByType(fireStation.getStation());

        assertThat(fireStationListByStationsNbr).isEqualTo(fireStationsByStationNbr);

    }
}