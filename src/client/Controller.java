package client;

import common.*;

import java.util.ArrayList;

public class Controller {
    private Client client;

    public Controller() {
        client = new Client("10.152.196.123", 6789);
//        client = new Client("localhost", 6789);


    }

    public Response getLastResponse() {
        if (client.isStackEmpty()) {
            return null;
        }
        return client.getLastResponse();
    }

    public void createUser(String username, String password, String cpr, String userRole, String wage) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCpr(cpr);
        user.setUserRole(userRole);
        user.setWage(wage);
        Request<User> createUserRequest = new Request<>("createAccount", user);
        client.sendRequest(createUserRequest);
    }

    public void removeUser(String username, String password, String cpr, String userRole) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCpr(cpr);
        user.setUserRole(userRole);
        Request<User> removeUserRequest = new Request<>("removeAccount", user);
        client.sendRequest(removeUserRequest);
    }

    public void submitEdit(String username, String password, String cpr, String userRole) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCpr(cpr);
        user.setUserRole(userRole);
        Request<User> submitEditRequest = new Request<>("editAccount", user);
        client.sendRequest(submitEditRequest);
    }

    public void changeUserInformation(ArrayList<String> arrayList) {
        User user = new User();
        int i = 0;
        user.setPicture(arrayList.get(i++));
        user.setUsername(arrayList.get(i++));
        user.setPassword(arrayList.get(i++));
        user.setFirstName(arrayList.get(i++));
        user.setSecondName(arrayList.get(i++));
        user.setLastName(arrayList.get(i++));
        user.setCpr(arrayList.get(i++));
        user.setDob(arrayList.get(i++));
        user.setAddress(arrayList.get(i++));
        user.setPostcode(arrayList.get(i++));
        user.setCity(arrayList.get(i++));
        user.setMobile(arrayList.get(i++));
        user.setLandline(arrayList.get(i++));
        user.setEmail(arrayList.get(i++));
        user.setKonto(arrayList.get(i++));
        user.setRecnumber(arrayList.get(i++));
        user.setLicencePlate(arrayList.get(i++));
        user.setPrefferedCommunication(arrayList.get(i++));
        user.setMoreInfo(arrayList.get(i++));
        user.setWage(arrayList.get(i++));
        user.setUserRole(arrayList.get(i++));
        Request<User> changeUserInformation = new Request<>("changeUserInfo", user);
        client.sendRequest(changeUserInformation);
    }

    public void createDepartment(String dNumber, String dName, String dLocation, String city, String dManager) {
        Department department = new Department(dNumber, dName, dLocation, dManager);
        department.setdCity(city);
        Request<Department> submitEditRequest = new Request<>("createDepartment", department);
        client.sendRequest(submitEditRequest);

    }

    public void editDepartment(String dNumber, String dName, String dLocation, String city, String dManager, String oldDNumber) {
        Department department = new Department(dNumber, dName, dLocation, dManager);
        department.setdCity(city);
        Department departmentold = new Department(oldDNumber, null, null, null);
        Department[] departments = {department, departmentold};
        Request<Department> submitEditRequest = new Request<>("editdepartment", departments);
        client.sendRequest(submitEditRequest);
    }

    public void deleteDepartment(String dNumber) {
        Department department = new Department(dNumber, null, null, null);

        Request<Department> submitEditRequest = new Request<>("deleteDepartment", department);
        client.sendRequest(submitEditRequest);
    }

    public void getAllDepartments() {
        Request submitEditRequest = new Request<>("getAllDepartments", null);
        client.sendRequest(submitEditRequest);
    }

    public void getWorkingSchedule(String cpr) {
        User user = new User();
        user.setCpr(cpr);
        Request submitEditRequest = new Request<>("getWorkingSchedule", user);
        client.sendRequest(submitEditRequest);
    }

    public void getAllWorkingSchedules() {
        Request submitEditRequest = new Request<>("getAllWorkingSchedule", null);
        client.sendRequest(submitEditRequest);
    }

    public void getUsersWithoutSchedule() {
        Request submitEditRequest = new Request<>("getUsersWithoutSchedule", null);
        client.sendRequest(submitEditRequest);
    }

    public void changeWagePerHours(String cpr,String wage) {
        User user = new User();
        user.setCpr(cpr);
        user.setWage(wage);
        Request submitEditRequest = new Request<>("changeWagePerHour", user);
        client.sendRequest(submitEditRequest);
    }

    public void getWorkingColleagues(String cpr) {
        if (cpr == null) {
            Request submitEditRequest = new Request<>("getWorkingColleagues", null);
            client.sendRequest(submitEditRequest);
        } else {
            User user = new User();
            user.setCpr(cpr);
            Request submitEditRequest = new Request<>("getWorkingColleagues", user);
            client.sendRequest(submitEditRequest);
        }
    }

    public void getMyWorkingDepartments(String cpr) {
        User user = new User();
        user.setCpr(cpr);
        Request submitEditRequest = new Request<>("getMyWorkingDepartments", user);
        client.sendRequest(submitEditRequest);
    }

    public void logIn(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Request submitEditRequest = new Request<>("login", user);
        client.sendRequest(submitEditRequest);
    }

    public void getAllColleagues() {
        Request submitEditRequest = new Request<>("getAllColleagues", null);
        client.sendRequest(submitEditRequest);
    }

    public void getAllUsers() {
        Request submitEditRequest = new Request<>("getallusers", null);
        client.sendRequest(submitEditRequest);
    }

    public void addWorkingSchedule(String dno, String dManager, String date, String starhours, String endHours) {
        WorkingSchedule workingSchedule = new WorkingSchedule(dno, dManager, date, starhours, endHours);
        Request submitEditRequest = new Request<>("addSchedule", workingSchedule);
        client.sendRequest(submitEditRequest);

    }

    public void getUserByDepartment(String dno) {
        Department d = new Department(dno, null, null, null);
        Request submitEditRequest = new Request<>("getuserbydepartment", d);
        client.sendRequest(submitEditRequest);
    }

    public void getUserForAdmin(String cpr) {
        User user = new User();
        user.setCpr(cpr);
        Request submitEditRequest = new Request<>("getuserinfoforadmin", user);
        client.sendRequest(submitEditRequest);
    }
    public void getWorkingHistory(String cpr) {
        User user = new User();
        user.setCpr(cpr);
        Request submitEditRequest = new Request<>("workinghistory", user);
        client.sendRequest(submitEditRequest);
    }
}
