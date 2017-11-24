package server;

import common.Department;
import common.User;

import java.util.ArrayList;

public interface IDBAdapter {

    boolean checkUsername(String username);

    String getUserPassword(String username);

    void createDepartment(Department department);

    void editDepartment(Department department, Department oldDepartment);

    Department viewDepartment(Department department);

    void deleteDepartment(Department department);

    void createAccount(User user);

    void removeAccount(User user);

    void editAccount(User user);

    void changeUserInformation(User user);

    ArrayList<Department> getAllDepartments();

}

