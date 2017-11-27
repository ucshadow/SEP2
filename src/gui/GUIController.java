package gui;

import client.Controller;
import common.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GUIController {

    @FXML private TextField userNameFieldCreate;
    @FXML private TextField CPRFieldCreate;
    @FXML private TextField passwordFieldCreate;
    @FXML private TextField userRoleCreate;

    Controller c = new Controller();

    @FXML
    private void handleCreateUserAdminPanel(ActionEvent event) {
        String name = userNameFieldCreate.getText();
        String cpr = CPRFieldCreate.getText();
        String pass = passwordFieldCreate.getText();
        String role = userRoleCreate.getText();
        User u = new User(name, pass, cpr, role);
        c.createUser(u);
    }

}
