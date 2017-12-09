package gui;

import client.Controller;
import common.Response;
import common.User;
import helpers.Helpers;
import helpers.ResponseReader;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class AllDepartmentWorkersController implements ResponseReader {

    private Controller controller;
    private User user;

    private int curX = 20;
    private int curY = 20;
    private int count = 0;

    private ArrayList<User> departmentUsers = new ArrayList<>();

    @FXML private Pane mainPane;
    @FXML private ScrollPane scrollableContainer;


    public void getAllDepartmentWorkers(String depNo) {
        controller.getUserByDepartment(depNo);
        Helpers.getLastResponse(controller, this);
    }

    public void getMyDepartments() {
        controller.getMyWorkingDepartments(user.getCpr());
        Helpers.getLastResponse(controller, this);
    }

    @Override
    public void responseReader(Response res) {
        if (res != null) {
            if(res.getResponse().equals("getuserbydepartment")) {
                ArrayList<User> arr = (ArrayList) res.getRespnoseObject();
                arr.forEach(this::createUserProfilePane);
            } else {
                ArrayList<String> arr = (ArrayList) res.getRespnoseObject();
                arr.forEach(this::getAllDepartmentWorkers);
            }
        }
    }

    private Pane createUserProfilePane(User user) {
        System.out.println(user.getCpr());
        Pane p = new Pane();
        p.setLayoutX(this.curX);
        p.setLayoutY(this.curY);
        p.setMinHeight(160.0);
        p.setMinWidth(400.0);
        p.setStyle("-fx-border-color: green;");


        mainPane.getChildren().add(p);
        count++;
        advance();
        return null;
    }

    private void advance() {
        if(count % 3 == 0) {
            this.curY += 170;
            this.curX = 20;
        } else {
            this.curX += 410;
        }
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
