package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
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
class MedicalRecordRepositoryTest {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @MockBean
    private Repository repository;
    @MockBean
    private ReadFromJason_DAO readFromJason_dao;

    private List<MedicalRecord> medicalRecordsList;
    @BeforeEach
    public void setUp(){

        MedicalRecord medicalRecord_1 = new MedicalRecord("Adil", "Alim", "11/11/1933",null , null);

        MedicalRecord medicalRecord_2 = new MedicalRecord("Memet", "Emet", "25/10/2000", null,null);

        medicalRecordsList = new ArrayList<>(Arrays.asList(medicalRecord_1, medicalRecord_2));

    }
    @Test
    void getAllTest() {
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getMedicalrecords()).thenReturn(medicalRecordsList);

        List<MedicalRecord> medicalRecordList_result = medicalRecordRepository.getAll();

        assertThat(medicalRecordList_result).isEqualTo(medicalRecordsList);
    }

    @Disabled
    @Test
    void saveTest() {

        MedicalRecord medicalRecordSaved = new MedicalRecord("Memet", "Emet", "25/10/2000", null,null);
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);

        boolean isSaved_result = medicalRecordRepository.save(medicalRecordSaved);

        assertThat(isSaved_result).isTrue();
    }

    @Test
    void deleteTest() {
        MedicalRecord medicalRecordDeleted = new MedicalRecord("Adil", "Alim", "11/11/1933",null , null);

        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getMedicalrecords()).thenReturn(medicalRecordsList);
        boolean isDeleted_result = medicalRecordRepository.delete(medicalRecordDeleted);

        assertThat(isDeleted_result).isTrue();
    }
    @Disabled
    @Test
    void updateTest() {

        MedicalRecord medicalRecordUpdated = new MedicalRecord("Memet", "Emet", "01/12/1990", null,null);
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getMedicalrecords()).thenReturn(medicalRecordsList);

        MedicalRecord isUpdated = medicalRecordRepository.update(1,medicalRecordUpdated);

        assertThat(isUpdated).isEqualTo(medicalRecordUpdated);
    }

    @Test
    void getByType() {
        MedicalRecord medicalRecord_1 = new MedicalRecord("Adil", "Alim", "11/11/1933",null , null);
        List<MedicalRecord> medicalRecordListByLastName = new ArrayList<>(List.of(medicalRecord_1));
        when(readFromJason_dao.readFromJsonFile()).thenReturn(repository);
        when(repository.getMedicalrecords()).thenReturn(medicalRecordsList);
        List<MedicalRecord> listMedicalRecordsBySureName = medicalRecordRepository.getByType("Alim");

        assertThat(listMedicalRecordsBySureName).isEqualTo(medicalRecordListByLastName);



    }
}