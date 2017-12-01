package server;

import common.Department;
import common.User;
import common.WorkingSchedule;

import java.util.ArrayList;
import java.util.Map;

public interface IDBAdapter {

//    boolean checkUsername(String username);
//
//    String getUserPassword(String username);

    void createDepartment(Department department);

    void editDepartment(Department department, Department oldDepartment);

    Department viewDepartment(Department department);

    void deleteDepartment(Department department);

    void createAccount(User user);

    void removeAccount(User user);

    void editAccount(User user);

    void changeUserInformation(User user);

    ArrayList<Department> getAllDepartments();

    ArrayList<WorkingSchedule> workingSchedulePerWeek(User user);

    String getWagePerHour(User user);

    void changeWagePerHours(User user);

    ArrayList<User> getWorkingColleagues(User user);

    ArrayList<String> getWorkingDepartments(User user);

    ArrayList<User> getAllColleagues(User user);

    User logIn(User user);

    void addToWorkingSchedule(WorkingSchedule workingSchedule);
}

