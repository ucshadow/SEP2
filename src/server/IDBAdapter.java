package server;

import common.Department;
import common.User;

public interface IDBAdapter {

    boolean checkUsername(String username);

    String getUserPassword(String username);

    void createDepartment(Department department);

    void editDepartment(Department department, Department oldDepartment);

    Department viewDepartment();

    void deleteDepartment(Department department);

    void createAccount(User user);

    void removeAccount(User user);

    void editAccount(User user);

}

