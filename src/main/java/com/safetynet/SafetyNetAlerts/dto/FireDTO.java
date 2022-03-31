package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;
import java.util.Objects;

public class FireDTO {
    private List<PersonListOfSameAddressDTO> listOfPersonAtSameAddress;
    private String station;

    public FireDTO(List<PersonListOfSameAddressDTO> listOfPersonAtSameAddress, String station) {
        this.listOfPersonAtSameAddress = listOfPersonAtSameAddress;
        this.station = station;
    }

    public List<PersonListOfSameAddressDTO> getListOfPersonAtSameAddress() {
        return listOfPersonAtSameAddress;
    }

    public void setListOfPersonAtSameAddress(List<PersonListOfSameAddressDTO> listOfPersonAtSameAddress) {
        this.listOfPersonAtSameAddress = listOfPersonAtSameAddress;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireDTO fireDTO = (FireDTO) o;
        return Objects.equals(listOfPersonAtSameAddress, fireDTO.listOfPersonAtSameAddress) && Objects.equals(station, fireDTO.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfPersonAtSameAddress, station);
    }

    @Override
    public String toString() {
        return "FireDTO{" +
                "listOfPersonAtSameAddress=" + listOfPersonAtSameAddress +
                ", station='" + station + '\'' +
                '}';
    }
}
