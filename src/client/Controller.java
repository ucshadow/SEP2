package client;

import common.Department;
import common.Request;
import common.Response;
import common.User;

public class Controller {
    private Client client;

    public Controller() {
//        client = new Client("localhost", 6789);
        client = new Client("10.152.192.107", 6789);

    }

    public Response getLastResponse() {
        if(client.isStackEmpty()) {
            return null;
        }
        return client.getLastResponse();
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

    public void getWorkingSchedule(User user) {
        Request submitEditRequest = new Request<>("getWorkingSchedule", user);
        client.sendRequest(submitEditRequest);
    }

    public void getWagePerHour(User user) {
        Request submitEditRequest = new Request<>("getWagePerHours", user);
        client.sendRequest(submitEditRequest);
    }

    public void changeWagePerHours(User user) {
        Request submitEditRequest = new Request<>("changeWagePerHour", user);
        client.sendRequest(submitEditRequest);
    }

    public void getWorkingColleagues(User user) {
        Request submitEditRequest = new Request<>("getWorkingColleagues", user);
        client.sendRequest(submitEditRequest);
    }

    public void getMyWorkingDepartments(User user) {
        Request submitEditRequest = new Request<>("getMyWorkingDepartments", user);
        client.sendRequest(submitEditRequest);
    }


    public void logIn(User user) {
        Request submitEditRequest = new Request<>("login", user);
        client.sendRequest(submitEditRequest);
    }

    public void getAllColleagues(User user) {
        Request submitEditRequest = new Request<>("getAllColleagues", user);
        client.sendRequest(submitEditRequest);
    }

}
