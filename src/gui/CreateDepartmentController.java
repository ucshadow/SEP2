package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;
import common.helpers.Helpers;
import common.helpers.ResponseReader;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreateDepartmentController implements ResponseReader {

    private Controller controller;
    private User user;

    /**
     * Author: Radu Orleanu
     * <p>
     * Create user functionality for admin for creating departments.
     */

    private ArrayList<Department> arrayListDepartments = new ArrayList<>();
    private ArrayList<User> arrayListUsers = new ArrayList<>();
    private ArrayList<String> arrayListDepartmentStrings = new ArrayList<>();


    //    lists
    protected ListProperty<Department> departmentListProperty = new SimpleListProperty<>();
    protected ListProperty<String> userListProperty = new SimpleListProperty<>();

    @FXML
    private ListView departmentList;
    @FXML
    private ListView userList;

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
    }

    @FXML
    public void createDept(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dPostcode = departmentPostcodeCreate.getText();
        String dCity = departmentCityCreate.getText();
        String dManager = departmentManagerCreate.getText();
        controller.createDepartment(dNumber, dName, dPostcode, dCity, dManager);
    }

    @FXML
    public void editDept(ActionEvent event) {
        ArrayList<String> newDepartment = readAllTextFields();
        controller.editDepartment(newDepartment.get(0), newDepartment.get(1), newDepartment.get(2), newDepartment.get(3), newDepartment.get(4), newDepartment.get(0));

    }

    @FXML
    public void removeDept(ActionEvent event) {
        String departmentToRemove = departmentNumberCreate.getText();
        controller.deleteDepartment(departmentToRemove);
    }

    private ArrayList<String> readAllTextFields() {
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
            Helpers.getLastResponse(controller, this);
        }
    }

    @FXML
    public void getAllUsersInDepartment() {
        if (userListProperty.isEmpty()) {
            controller.getUserByDepartment(selectedDepartment.getdNumber());
            Helpers.getLastResponse(controller, this);
        }
    }

    public void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getAllDepartments")) {
                arrayListDepartments = (ArrayList) res.getRespnoseObject();
                for (Department dept : arrayListDepartments) {
                    arrayListDepartmentStrings.add(dept.getdNumber() + " " + dept.getdName() + " " + dept.getdManager() + " " + dept.getdLocation());
                }
                populateDepartments();
            }
            if (res.getResponse().equals("getuserbydepartment")) {
                arrayListUsers = (ArrayList) res.getRespnoseObject();
                ArrayList<String> arrayListUserStrings = new ArrayList<>();
                for (User a : arrayListUsers) {
                    arrayListUserStrings.add(a.getFirstName() + " " + a.getLastName() + " " + a.getCpr());
                }
                populateUsersInDepartment(arrayListUserStrings);
            }
        }
    }

    @FXML
    private void populateDepartments() {
        departmentList.itemsProperty().bind(departmentListProperty);
        departmentListProperty.set(FXCollections.observableArrayList(arrayListDepartments));
    }

    @FXML
    private void populateUsersInDepartment(ArrayList<String> users) {
        userList.itemsProperty().bind(userListProperty);
        userListProperty.set(FXCollections.observableArrayList(users));
    }

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
