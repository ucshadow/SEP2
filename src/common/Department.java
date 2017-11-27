package common;

import java.io.Serializable;

public class Department implements Serializable {
    private String dNumber, dName, dLocation, dManager, dEmployees;

    public Department(String dNumber, String dName, String dLocation, String dManager) {
        this.dNumber = dNumber;
        this.dName = dName;
        this.dLocation = dLocation;
        this.dManager = dManager;
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

    public String getdEmployees() {
        return dEmployees;
    }

    public void setdEmployees(String dEmployees) {
        this.dEmployees = dEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (dNumber != null ? !dNumber.equals(that.dNumber) : that.dNumber != null) return false;
        if (dName != null ? !dName.equals(that.dName) : that.dName != null) return false;
        if (dLocation != null ? !dLocation.equals(that.dLocation) : that.dLocation != null) return false;
        if (dManager != null ? !dManager.equals(that.dManager) : that.dManager != null) return false;
        return dEmployees != null ? dEmployees.equals(that.dEmployees) : that.dEmployees == null;
    }

    @Override
    public String toString() {
        return "Department{" +
                "dNumber='" + dNumber + '\'' +
                ", dName='" + dName + '\'' +
                ", dLocation='" + dLocation + '\'' +
                ", dManager='" + dManager + '\'' +
                ", dEmployees='" + dEmployees + '\'' +
                '}';
    }
}
