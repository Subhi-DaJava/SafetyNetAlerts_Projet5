package com.safetynet.SafetyNetAlerts.dto;

import java.util.Objects;

public class HomeListCoveredByOneFireStationDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public HomeListCoveredByOneFireStationDTO(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeListCoveredByOneFireStationDTO homeDTO = (HomeListCoveredByOneFireStationDTO) o;
        return Objects.equals(firstName, homeDTO.firstName) && Objects.equals(lastName, homeDTO.lastName) && Objects.equals(address, homeDTO.address) && Objects.equals(phone, homeDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, phone);
    }

    @Override
    public String toString() {
        return "HomeDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
