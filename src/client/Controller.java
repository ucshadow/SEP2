package client;

import common.Department;
import common.Request;
import common.Response;
import common.User;

public class Controller {
    private Client client;

    public Controller() {
        client = new Client("localhost", 6789);
//        client = new Client("10.152.192.107", 6789);

    }

    public Response getLastResponse() {
        if (client.isStackEmpty()) {
            return null;
        }
        return client.getLastResponse();
    }

    //TODO Tested working
    public void createUser(User user) {
        Request<User> createUserRequest = new Request<>("createAccount", user);
        client.sendRequest(createUserRequest);
    }

    //TODO Tested working
    public void removeUser(User user) {
        Request<User> removeUserRequest = new Request<>("removeAccount", user);
        client.sendRequest(removeUserRequest);
    }

    //TODO Tested working
    public void submitEdit(User user) {
        Request<User> submitEditRequest = new Request<>("editAccount", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Cannot test
    public void changeUserInformation(User user) {
        Request<User> changeUserInformation = new Request<>("changeUserInfo", user);
        client.sendRequest(changeUserInformation);
    }

    //TODO Tested working
    public void createDepartment(Department department) {
        Request<Department> submitEditRequest = new Request<>("createDepartment", department);
        client.sendRequest(submitEditRequest);

    }

    //TODO working case sensitive!!!!
    public void editDepartment(Department department, Department departmentold) {
        Department[] d = {department, departmentold};
        Request<Department> submitEditRequest = new Request<>("editdepartment", d);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void viewDepartment(Department department) {
        Request<Department> submitEditRequest = new Request<>("getDepartment", department);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void deleteDepartment(Department department) {

        Request<Department> submitEditRequest = new Request<>("deleteDepartment", department);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void getAllDepartments() {

        Request submitEditRequest = new Request<>("getAllDepartments", null);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void getWorkingSchedule(User user) {
        Request submitEditRequest = new Request<>("getWorkingSchedule", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void getWagePerHour(User user) {
        Request submitEditRequest = new Request<>("getWagePerHours", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void changeWagePerHours(User user) {
        Request submitEditRequest = new Request<>("changeWagePerHour", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Maybe working cannot properly test at that time
    public void getWorkingColleagues(User user) {
        Request submitEditRequest = new Request<>("getWorkingColleagues", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void getMyWorkingDepartments(User user) {
        Request submitEditRequest = new Request<>("getMyWorkingDepartments", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working based on GUI TESTING CATALIN
    public void logIn(User user) {
        Request submitEditRequest = new Request<>("login", user);
        client.sendRequest(submitEditRequest);
    }

    //TODO Tested working
    public void getAllColleagues(User user) {
        Request submitEditRequest = new Request<>("getAllColleagues", user);
        client.sendRequest(submitEditRequest);
    }

}
