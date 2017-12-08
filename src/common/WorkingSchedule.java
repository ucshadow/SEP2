package common;

import java.io.Serializable;

public class WorkingSchedule implements Serializable {
    private String departmentNumber;
    private String employeeCPR;
    private String workingDate;
    private String startHours;
    private String endHours;

    public WorkingSchedule(String departmentNumber, String employeeCPR, String workingDate, String startHours, String endHours) {
        this.departmentNumber = departmentNumber;
        this.employeeCPR = employeeCPR;
        this.workingDate = workingDate;
        this.startHours = startHours;
        this.endHours = endHours;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getEmployeeCPR() {
        return employeeCPR;
    }

    public void setEmployeeCPR(String employeeCPR) {
        this.employeeCPR = employeeCPR;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    public String getStartHours() {
        return startHours;
    }

    public void setStartHours(String startHours) {
        this.startHours = startHours;
    }

    public String getEndHours() {
        return endHours;
    }

    public void setEndHours(String endHours) {
        this.endHours = endHours;
    }

    @Override
    public String toString() {
        return "CPR: " + getEmployeeCPR() + ", dep: " + getDepartmentNumber() + ", on: " + getWorkingDate() +
                ", from: " + getStartHours().substring(0, 5) + ", to: " + getEndHours().substring(0, 5);
    }
}
