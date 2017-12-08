package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreateDepartmentController {

    private Controller controller;
    private User user;

    /**
     * Author: Radu Orleanu
     * <p>
     * Create user functionality for admin for creating departments.
     */

    private ArrayList<Department> arrayListDepartments = new ArrayList<>();
    private ArrayList<User> arrayListUsers = new ArrayList<>();

    //    lists
    protected ListProperty<Department> departmentListProperty = new SimpleListProperty<>();
    protected ListProperty<User> userListProperty = new SimpleListProperty<>();

    @FXML
    private ListView departmentList;
    @FXML
    private ListView userList;
//    @FXML
//    private ListView selected;

    //    text fields
    @FXML
    private TextField departmentNumberCreate;
    @FXML
    private TextField departmentNameCreate;
    @FXML
    private TextField departmentPostcodeCreate;
    @FXML
    private TextField departmentCityCreate;
    @FXML
    private TextField departmentManagerCreate;

    //    buttons
    @FXML
    private Button departmentCreateButton;
    @FXML
    private Button departmentSelectButton;
    @FXML
    private Button departmentEditButton;
    @FXML
    private Button departmentRemoveButton;

    private Department selectedDepartment;

    @FXML
    public void initialize() {
        departmentList.setOnMouseClicked(event -> addToSelected(departmentList.getSelectionModel().getSelectedItem()));
    }

    public void onDepartmentTabFocus() {
        getAllDepartmentsEvent();
    }

    private void addToSelected(Object sel) {
        getAllDepartmentsEvent();
        userList.getItems().clear();
        selectedDepartment = (Department) sel;
        getAllUsersInDepartment();
        populateUsersInDepartment();
        arrayListUsers.forEach(userList.getItems()::add);
    }

    @FXML
    public void createDept(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dPostcode = departmentPostcodeCreate.getText();
        String dCity = departmentCityCreate.getText();
        String dManager = departmentManagerCreate.getText();
//        controller.createDepartment(dNumber, dName, dPostcode, dCity, dManager);
    }

    @FXML
    public void editDept(ActionEvent event) {
        ArrayList<String> newDepartment = readAllTextFields();
        controller.editDepartment(newDepartment.get(0), newDepartment.get(1), newDepartment.get(2), newDepartment.get(3), newDepartment.get(0));
        for (String s : newDepartment) {
            System.out.println(s);
        }
    }

    @FXML
    public void removeDept(ActionEvent event) {
        String departmentToRemove = departmentNumberCreate.getText();
        controller.deleteDepartment(departmentToRemove);
    }

    private ArrayList<String> readAllTextFields() {
//        dNumber, dName, dPostcode, dCity, dManager
        ArrayList<String> departmentDetails = new ArrayList<>();
        departmentDetails.add(departmentNumberCreate.getText());
        departmentDetails.add(departmentNameCreate.getText());
        departmentDetails.add(departmentPostcodeCreate.getText());
        departmentDetails.add(departmentCityCreate.getText());
        departmentDetails.add(departmentManagerCreate.getText());
        return departmentDetails;
    }

    @FXML
    public void getAllDepartmentsEvent() {
        if (departmentListProperty.isEmpty()) {
            controller.getAllDepartments();
            Task task = new Task<Response>() {
                @Override
                public Response call() {
                    int tries = 0;
                    while (tries < 10) {
                        Response r = controller.getLastResponse();
                        if (r != null) {
                            return r;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tries++;
                    }
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(t -> responseReader((Response) task.getValue()));
        }
    }

    @FXML
    public void getAllUsersInDepartment() {
        if (userListProperty.isEmpty()) {
            controller.getUserByDepartment(selectedDepartment.getdNumber());
            Task task = new Task<Response>() {
                @Override
                public Response call() {
                    int tries = 0;
                    while (tries < 10) {
                        Response r = controller.getLastResponse();
                        if (r != null) {
                            return r;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tries++;

                    }
                    return null;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(t -> responseReader((Response) task.getValue()));
        }
    }

    private void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getAllDepartments")) {
                arrayListDepartments = (ArrayList) res.getRespnoseObject();
                System.out.println(res.toString());
                selectedDepartment = arrayListDepartments.get(0);
                populateDepartments();
            }
            if (res.getResponse().equals("getuserbydepartment")) {
                arrayListUsers = (ArrayList) res.getRespnoseObject();
                System.out.println(res.toString());
                populateUsersInDepartment();

            }
        }
    }

    @FXML
    private void populateDepartments() {
        departmentList.itemsProperty().bind(departmentListProperty);
        departmentListProperty.set(FXCollections.observableArrayList(arrayListDepartments));
    }

    @FXML
    private void populateUsersInDepartment() {
        userList.itemsProperty().bind(userListProperty);
        userListProperty.set(FXCollections.observableArrayList(arrayListUsers));
    }

//    ToDo: finish remove worker from department
//    @FXML
//    public void removeWorkerFromDepartment() {
//        selectedUser = (User) userList.getSelectionModel().getSelectedItem();
//        controller.removeUser(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getCpr(), selectedUser.getUserRole());
//    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
