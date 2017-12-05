package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreateUserController {

    private Controller controller;
    private User user;

    @FXML
    private TextField userNameFieldCreate;
    @FXML
    private TextField CPRFieldCreate;
    @FXML
    private TextField passwordFieldCreate;
    @FXML
    private TextField userRoleCreate;
    @FXML
    private TextField userWageCreate;


    @FXML
    private void handleCreateUserAdminPanel() {
        String name = userNameFieldCreate.getText();
        String cpr = CPRFieldCreate.getText();
        String pass = passwordFieldCreate.getText();
        String role = userRoleCreate.getText();
        String wage = userWageCreate.getText();
        controller.createUser(name, pass, cpr, role, wage);
    }

    @FXML
    private void getAllUsersEvent() {
        controller.getAllUsers(user);
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

    private void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getallusers")) {
                System.out.println(res.toString());
                populateUserTable((ArrayList<User>) res.getRespnoseObject());
            }
        }
    }

    private void populateUserTable(ArrayList<User> users) {
        users.forEach(System.out::println);
    }

    @FXML
    private void closePanel() {
        System.exit(0);
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
