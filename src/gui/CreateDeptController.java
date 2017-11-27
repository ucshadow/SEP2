package gui;

import client.Controller;
import common.Department;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateDeptController {
    @FXML private TextField departmentNumberCreate;
    @FXML private TextField departmentNameCreate;
    @FXML private TextField departmentLocationCreate;
    @FXML private TextField departmentManagerCreate;

    Controller c = new Controller();

    @FXML
    private void handleCreateUserAdminPanel(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dLocation = departmentLocationCreate.getText();
        String dManager = departmentManagerCreate.getText();
        Department d = new Department(dNumber, dName, dLocation, dManager);
        c.createDepartment(d);
    }

    @FXML
    public void createDept(){
    }
}