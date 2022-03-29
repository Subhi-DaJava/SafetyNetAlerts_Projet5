package com.safetynet.SafetyNetAlerts.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class SolutionFormatterImpl implements SolutionFormatter{


    @Override
    public int formatterStringToDate(String date, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateFormatted = LocalDate.parse(date, formatter);
        Period birthDate = Period.between(dateFormatted, LocalDate.now());

        return birthDate.getYears();
    }
}
