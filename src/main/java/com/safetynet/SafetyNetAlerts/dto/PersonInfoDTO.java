package com.safetynet.SafetyNetAlerts.dto;

import java.util.Objects;
import java.util.Set;

public class PersonInfoDTO {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private Set<String> medicalRecords;

    public PersonInfoDTO(String firstName, String lastName, String address, int age, String email, Set<String> medicalRecords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medicalRecords = medicalRecords;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(Set<String> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonInfoDTO that = (PersonInfoDTO) o;
        return age == that.age && firstName.equals(that.firstName) && lastName.equals(that.lastName) && address.equals(that.address) && email.equals(that.email) && medicalRecords.equals(that.medicalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, age, email, medicalRecords);
    }

    @Override
    public String toString() {
        return "PersonInfoDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", medicalRecords=" + medicalRecords +
                '}';
    }
}
