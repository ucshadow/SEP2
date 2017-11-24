package client;

import common.Department;
import common.Request;
import common.User;

public class Controller {
    private Client client;

    public Controller() {
        client = new Client("localhost", 6789);
    }

    public void createUser(User user) {
        Request<User> createUserRequest = new Request<>("createAccount", user);
        client.sendRequest(createUserRequest);
    }

    public void removeUser(User user) {
        Request<User> removeUserRequest = new Request<>("removeAccount", user);
        client.sendRequest(removeUserRequest);
    }

    public void submitEdit(User user) {
        Request<User> submitEditRequest = new Request<>("editAccount", user);
        client.sendRequest(submitEditRequest);
    }

    public void changeUserInformation(User user) {
        Request<User> changeUserInformation = new Request<>("changeUserInfo", user);
        client.sendRequest(changeUserInformation);
    }

    public void createDepartment(Department department) {
        Request<Department> submitEditRequest = new Request<>("createDepartment", department);
        client.sendRequest(submitEditRequest);

    }

    public void editDepartment(Department department, Department departmentold) {
        Department[] d = {department, departmentold};
        Request<Department> submitEditRequest = new Request<>("editDepartment", d);
        client.sendRequest(submitEditRequest);
    }

    public void viewDepartment(Department department) {
        Request<Department> submitEditRequest = new Request<>("getDepartment", department);
        client.sendRequest(submitEditRequest);
    }

    public void deleteDepartment(Department department) {

        Request<Department> submitEditRequest = new Request<>("deleteDepartment", department);
        client.sendRequest(submitEditRequest);
    }

    public void getAllDepartments() {

        Request submitEditRequest = new Request<>("getAllDepartments", null);
        client.sendRequest(submitEditRequest);
    }

}
