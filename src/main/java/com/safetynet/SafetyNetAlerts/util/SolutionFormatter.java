package com.safetynet.SafetyNetAlerts.util;

public interface SolutionFormatter {
     /**
      *
      * @param date Elle est String en format date par ex. "08/18/2001"("MM/dd/yyyy")
      * @return Age (year/an) en int
      */
     int formatterStringToDate(String date);
}
