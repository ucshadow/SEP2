package gui;

import client.Controller;
import common.Response;
import common.User;
import helpers.Helpers;
import helpers.ResponseReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class AllDepartmentWorkersController implements ResponseReader {

    private Controller controller;
    private User user;

    private int curX = 20;
    private int curY = 80;
    private int count = 0;
    private boolean showingAll = false;

    private ArrayList<User> departmentUsers = new ArrayList<>();
    private ArrayList<User> allUsers = new ArrayList<>();

    @FXML
    private Pane mainPane;
    @FXML
    private TextField warnings;


    public void getAllDepartmentWorkers() {
        if(departmentUsers.isEmpty()) {
            controller.getWorkingColleagues(user.getCpr());
        }
        if(allUsers.isEmpty()) {
            controller.getWorkingColleagues(null); // this will get all colleagues
        }
        Helpers.getLastResponse(controller, this);
    }

    @Override
    public void responseReader(Response res) {
        ArrayList<User> users = (ArrayList<User>) res.getRespnoseObject();
        if(departmentUsers.isEmpty()) {
            departmentUsers = users;
            warnings.setText("Loading, please wait...");
            departmentUsers.forEach(this::createUserProfilePane);
            warnings.setText("Department colleagues");
        }

        if(allUsers.isEmpty() && !departmentUsers.isEmpty()) {
            allUsers = users;
        }
    }

    @FXML
    public void toggle() {
        System.out.println(departmentUsers.size());
        System.out.println(allUsers.size());
        if(showingAll) {
            mainPane.getChildren().removeAll();
            warnings.setText("Loading, please wait...");
            departmentUsers.forEach(this::createUserProfilePane);
            warnings.setText("Department colleagues");
            showingAll = false;
        } else {
            mainPane.getChildren().removeAll();
            warnings.setText("Loading, please wait...");
            allUsers.forEach(this::createUserProfilePane);
            warnings.setText("All colleagues");
            showingAll = true;
        }
    }

    private void createUserProfilePane(User user) {
        Pane p = new Pane();
        p.setLayoutX(this.curX);
        p.setLayoutY(this.curY);
        p.setMinHeight(160.0);
        p.setMinWidth(400.0);
        p.setStyle("-fx-border-color: green;");

        ImageView image = new ImageView();
        image.setFitWidth(96);
        image.setFitHeight(96);
        image.setLayoutX(6);
        image.setLayoutY(6);

        Image i;
        TextField email = new TextField();
        TextField firstName = new TextField();
        TextField familyName = new TextField();
        TextField phoneNumber = new TextField();


        // prepare image
        try{
            i = new Image(user.getPicture(), true);
        } catch (Exception e) {
            i = new Image("https://i.imgur.com/gsTfiBr.png", true);
        }
        image.setImage(i);

        // prepare text fields
        email.setLayoutX(13.0);
        email.setLayoutY(127.0);
        email.setPrefHeight(25.0);
        email.setPrefWidth(375.0);
        email.setEditable(false);
        email.setText(user.getEmail());
        email.setStyle("-fx-background-color: none;");

        firstName.setLayoutX(176.0);
        firstName.setLayoutY(14.0);
        firstName.setPrefHeight(25.0);
        firstName.setPrefWidth(213);
        firstName.setEditable(false);
        firstName.setText(user.getFirstName());
        firstName.setStyle("-fx-background-color: none;");

        familyName.setLayoutX(176.0);
        familyName.setLayoutY(55.0);
        familyName.setPrefHeight(25.0);
        familyName.setPrefWidth(213);
        familyName.setEditable(false);
        familyName.setText(user.getLastName());
        familyName.setStyle("-fx-background-color: none;");

        phoneNumber.setLayoutX(176.0);
        phoneNumber.setLayoutY(92.0);
        phoneNumber.setPrefHeight(25.0);
        phoneNumber.setPrefWidth(213);
        phoneNumber.setEditable(false);
        phoneNumber.setText(user.getMobile());
        phoneNumber.setStyle("-fx-background-color: none;");

        // add nodes
        p.getChildren().add(image);
        p.getChildren().addAll(email, firstName, familyName, phoneNumber);


        mainPane.getChildren().add(p);
        count++;
        advance();
    }

    private void advance() {
        if (count % 3 == 0) {
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
