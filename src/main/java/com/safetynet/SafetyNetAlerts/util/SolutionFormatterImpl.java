package com.safetynet.SafetyNetAlerts.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class SolutionFormatterImpl implements SolutionFormatter{

    /**
     *
     * @param date Elle est String en format date par ex. "08/18/2001"
     * @param format Il désigne un format de date comme "MM/dd/yyyy", mais en String
     * @return Age (year/an) en int
     */
    @Override
    public int formatterStringToDate(String date, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateFormatted = LocalDate.parse(date, formatter);
        Period birthDate = Period.between(dateFormatted, LocalDate.now());

        return birthDate.getYears();
    }
}
