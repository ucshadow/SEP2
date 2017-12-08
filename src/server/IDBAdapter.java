package server;

import common.Department;
import common.Request;
import common.User;
import common.WorkingSchedule;

import java.util.ArrayList;
import java.util.Map;

public interface IDBAdapter {

    void createAccount(User user);

    void editAccount(User user);

    void removeAccount(User user);

    void createDepartment(Department department);

    void editDepartment(Department department, Department oldDepartment);

    Department viewDepartment(Department department);

    void deleteDepartment(Department department);

    ArrayList<Department> getAllDepartments();

    String getWagePerHour(User user);

    void changeWagePerHours(User user);

    void changeUserInformation(User user);

    ArrayList<User> getAllColleagues();

    ArrayList<User> getAllUsers();

    User logIn(User user);

    void addToWorkingSchedule(WorkingSchedule workingSchedule);


    ArrayList<WorkingSchedule> workingSchedulePerWeek(User user);

    ArrayList<String> getWorkingDepartments(User user);

    ArrayList<WorkingSchedule> allWorkingSchedulesPerWeek();

    ArrayList<WorkingSchedule> getAllWorkingSchedules();

    ArrayList<User> getWorkingColleagues(User user);


    ArrayList<User> getAllUsers(User user);

    void wordCheck(String string);

    ArrayList<User> getUsersByDepartment(Department department);

    ArrayList<User> getAllUsersWithoutWorkingSchedule();
}


