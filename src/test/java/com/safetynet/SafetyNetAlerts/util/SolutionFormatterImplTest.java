package com.safetynet.SafetyNetAlerts.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
@AutoConfigureMockMvc
class SolutionFormatterImplTest {
    @Autowired
    private SolutionFormatter solutionFormatter;

    @Test
    void formatterStringToDateTest() {
        String format = "MM/dd/yyyy";
        String date = "01/08/1986";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate dateFormatted = LocalDate.parse(date, formatter);

        Period birthDate = Period.between(dateFormatted, LocalDate.now());

        int testBrithDateYear = solutionFormatter.formatterStringToDate(date);

        assertThat(testBrithDateYear).isEqualTo(birthDate.getYears());

    }
}