package com.safetynet.SafetyNetAlerts.dto;

import java.util.Objects;

public class CommunityEmailDTO {
    private String email;


    public CommunityEmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityEmailDTO emailDTO = (CommunityEmailDTO) o;
        return Objects.equals(email, emailDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "CommunityEmailDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
