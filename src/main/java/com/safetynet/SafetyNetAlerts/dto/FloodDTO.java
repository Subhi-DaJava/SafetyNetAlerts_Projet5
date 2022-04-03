package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;
import java.util.Objects;

public class FloodDTO {
    private List<PersonListOfSameAddressDTO> personListOfSameAddress;
    private String address;

    public List<PersonListOfSameAddressDTO> getPersonListOfSameAddress() {
        return personListOfSameAddress;
    }


    public void setPersonListOfSameAddress(List<PersonListOfSameAddressDTO> personListOfSameAddress) {
        this.personListOfSameAddress = personListOfSameAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FloodDTO(List<PersonListOfSameAddressDTO> personListOfSameAddress, String address) {
        this.personListOfSameAddress = personListOfSameAddress;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloodDTO floodDTO = (FloodDTO) o;
        return Objects.equals(personListOfSameAddress, floodDTO.personListOfSameAddress) && Objects.equals(address, floodDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personListOfSameAddress, address);
    }

    @Override
    public String toString() {
        return "FloodDTO{" +
                "personListOfSameAddress=" + personListOfSameAddress +
                ", address='" + address + '\'' +
                '}';
    }
}
