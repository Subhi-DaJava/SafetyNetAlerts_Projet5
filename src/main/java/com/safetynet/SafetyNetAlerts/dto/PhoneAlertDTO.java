package com.safetynet.SafetyNetAlerts.dto;

import java.util.Objects;

public class PhoneAlertDTO {
    private String phone;

    public PhoneAlertDTO(String phone) {
        this.phone = phone;
    }

    public PhoneAlertDTO() {
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
        PhoneAlertDTO that = (PhoneAlertDTO) o;
        return phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

    @Override
    public String toString() {
        return "PhoneAlertDTO{" +
                "phone='" + phone + '\'' +
                '}';
    }
}
