package com.safetynet.SafetyNetAlerts.dto;

import java.util.Objects;
import java.util.Set;

public class FireDTO {
    private String lastName;
    private String phone;
    private String station;
    private int age;
    private Set<String> medications;
    private Set<String> allergies;

    public FireDTO(String lastName, String phone, String station, int age, Set<String> medications, Set<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.station = station;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public FireDTO() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<String> getMedications() {
        return medications;
    }

    public void setMedications(Set<String> medications) {
        this.medications = medications;
    }

    public Set<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireDTO fireDTO = (FireDTO) o;
        return age == fireDTO.age && Objects.equals(lastName, fireDTO.lastName) && Objects.equals(phone, fireDTO.phone) && Objects.equals(station, fireDTO.station) && Objects.equals(medications, fireDTO.medications) && Objects.equals(allergies, fireDTO.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, phone, station, age, medications, allergies);
    }
}
