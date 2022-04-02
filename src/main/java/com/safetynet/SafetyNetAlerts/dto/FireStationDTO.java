package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;
import java.util.Objects;

public class FireStationDTO {
    private List<HomeListCoveredByOneFireStationDTO> homeListCoveredByOneFireStation;
    private int totalAdult;
    private int totalChild;

    public FireStationDTO(List<HomeListCoveredByOneFireStationDTO> homeDTOList, int totalAdult, int totalChild) {
        this.homeListCoveredByOneFireStation = homeDTOList;
        this.totalAdult = totalAdult;
        this.totalChild = totalChild;
    }

    public List<HomeListCoveredByOneFireStationDTO> getHomeListCoveredByOneFireStation() {
        return homeListCoveredByOneFireStation;
    }

    public void setHomeListCoveredByOneFireStation(List<HomeListCoveredByOneFireStationDTO> homeListCoveredByOneFireStation) {
        this.homeListCoveredByOneFireStation = homeListCoveredByOneFireStation;
    }

    public int getTotalAdult() {
        return totalAdult;
    }

    public void setTotalAdult(int totalAdult) {
        this.totalAdult = totalAdult;
    }

    public int getTotalChild() {
        return totalChild;
    }

    public void setTotalChild(int totalChild) {
        this.totalChild = totalChild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStationDTO that = (FireStationDTO) o;
        return totalAdult == that.totalAdult && totalChild == that.totalChild && Objects.equals(homeListCoveredByOneFireStation, that.homeListCoveredByOneFireStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeListCoveredByOneFireStation, totalAdult, totalChild);
    }

    @Override
    public String toString() {
        return "FireStationDTO{" +
                "homeDTOList=" + homeListCoveredByOneFireStation +
                ", totalAdult=" + totalAdult +
                ", totalChild=" + totalChild +
                '}';
    }
}
