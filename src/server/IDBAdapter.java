package server;

import common.Department;
import common.User;
import common.WorkingSchedule;

import java.util.ArrayList;

public interface IDBAdapter {

    void createAccount(User user);

    void editAccount(User user);

    void removeAccount(User user);

    void createDepartment(Department department);

    void editDepartment(Department department, Department oldDepartment);

    void deleteDepartment(Department department);

    ArrayList<Department> getAllDepartments();

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

    ArrayList<User> getUsersByDepartment(Department department);

    ArrayList<User> getAllUsersWithoutWorkingSchedule();

    User getUserInfOForAdmin(User user);

    ArrayList<WorkingSchedule> getHistoryWorkingSchedule(User user);
}


