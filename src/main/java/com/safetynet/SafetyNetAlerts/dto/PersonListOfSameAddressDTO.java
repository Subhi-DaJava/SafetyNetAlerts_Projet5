package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;
import java.util.Objects;

public class PersonListOfSameAddressDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medicalRecords;

    public PersonListOfSameAddressDTO(String firstName, String lastName, String phone, int age, List<String> medicalRecords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<String> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonListOfSameAddressDTO that = (PersonListOfSameAddressDTO) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(medicalRecords, that.medicalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phone, age, medicalRecords);
    }

    @Override
    public String toString() {
        return "PersonListOfSameAddressDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", medicalRecords=" + medicalRecords +
                '}';
    }
}