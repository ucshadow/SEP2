package common;

import java.io.Serializable;

public class Department implements Serializable {
    private String dNumber, dName, dLocation, dManager, dCity;

    public Department(String dNumber, String dName, String dLocation, String dManager) {
        this.dNumber = dNumber;
        this.dName = dName;
        this.dLocation = dLocation;
        this.dManager = dManager;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdNumber() {
        return dNumber;
    }

    public void setdNumber(String dNumber) {
        this.dNumber = dNumber;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation;
    }

    public String getdManager() {
        return dManager;
    }

    public void setdManager(String dManager) {
        this.dManager = dManager;
    }


    @Override
    public String toString() {
        String shortDescription = "";
        shortDescription += dNumber + " " + dName + " " + dLocation + " " + dManager;
        return shortDescription;
    }

    public String asString() {
        return "Department{" +
                "dNumber='" + dNumber + '\'' +
                ", dName='" + dName + '\'' +
                ", dLocation='" + dLocation + '\'' +
                ", dManager='" + dManager + '\'' +
                '}';
    }
}
