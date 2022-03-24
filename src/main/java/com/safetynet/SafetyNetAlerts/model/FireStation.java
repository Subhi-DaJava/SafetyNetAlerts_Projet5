package com.safetynet.SafetyNetAlerts.model;

public class FireStation {
    private String address;
    private String station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    public void setStation(String station) {
        this.station = station;
    }
}
