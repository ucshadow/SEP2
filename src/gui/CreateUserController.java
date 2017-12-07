package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CreateUserController {

    private Controller controller;
    ArrayList<User> userList;
    private User user;
    private User selectedUser;

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
    private ListView clientList;
    @FXML
    private TextField adminEditUserUsername;
    @FXML
    private TextField adminEditUserCPR;
    @FXML
    private TextField adminEditUserPassword;
    @FXML
    private TextField adminEditUserRole;
    @FXML
    private TextField adminEditWage;


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
                userList = (ArrayList<User>) res.getRespnoseObject();
                populateUserTable((ArrayList<User>) res.getRespnoseObject());
            }
        }
    }

    private void populateUserTable(ArrayList<User> users) {
        ObservableList<String> items = FXCollections.observableArrayList();

        for (User user: users){
            items.add(user.getUsername() + " " + user.getCpr());
        }

        clientList.setItems(items);

        System.out.println(userList.toString());
    }

    @FXML
    private void getListText(){

        String str = clientList.getFocusModel().getFocusedItem().toString();
        String[] parts = str.split(" ");
        selectedUser = getUserByCPR(parts[1]);


        adminEditUserUsername.setText(selectedUser.getUsername());
        adminEditUserCPR.setText(selectedUser.getCpr());
        adminEditUserPassword.setText(selectedUser.getPassword());
        adminEditUserRole.setText(selectedUser.getUserRole());
        adminEditWage.setText(selectedUser.getWage());
    }

    private User getUserByCPR(String cpr){
        for (User user : userList){
            if ( user.getCpr().equals(cpr)){
                return user;
            }
        }
        return null;
    }



    @FXML
    private void createUserInfoWindow() {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userInfo.fxml"));
            root = fxmlLoader.load();
            userInfoController controller = fxmlLoader.getController();
            System.out.println("My user:" + selectedUser);
            controller.displayUser(selectedUser);
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("User Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeUser(){
        controller.removeUser(adminEditUserUsername.getText(),adminEditUserPassword.getText(),adminEditUserCPR.getText(),adminEditUserRole.getText());
    }

    @FXML
    private void submitEdit(){
        controller.submitEdit(adminEditUserUsername.getText(),adminEditUserPassword.getText(),adminEditUserCPR.getText(),adminEditUserRole.getText());
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
