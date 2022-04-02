package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;
import java.util.Objects;

public class ChildAlertDTO {
    private List<ChildInfos> childInfos;
    //We could use Person instead of PersonListOfSameAddressDTO
    private List<PersonListOfSameAddressDTO> restOfFamily;

    public ChildAlertDTO(List<ChildInfos> childInfos, List<PersonListOfSameAddressDTO> restOfFamily) {
        this.childInfos = childInfos;
        this.restOfFamily = restOfFamily;
    }

    public List<ChildInfos> getChildInfos() {
        return childInfos;
    }

    public void setChildInfos(List<ChildInfos> childInfos) {
        this.childInfos = childInfos;
    }

    public List<PersonListOfSameAddressDTO> getRestOfFamily() {
        return restOfFamily;
    }

    public void setRestOfFamily(List<PersonListOfSameAddressDTO> restOfFamily) {
        this.restOfFamily = restOfFamily;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildAlertDTO that = (ChildAlertDTO) o;
        return Objects.equals(childInfos, that.childInfos) && Objects.equals(restOfFamily, that.restOfFamily);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childInfos, restOfFamily);
    }

    @Override
    public String toString() {
        return "ChildAlertDTO{" +
                "childInfos=" + childInfos +
                ", restOfFamily=" + restOfFamily +
                '}';
    }
}
