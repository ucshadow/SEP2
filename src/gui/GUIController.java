package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.fxml.FXML;

public class GUIController {

    @FXML private CreateUserController createUserController;
    @FXML private MyProfileController myProfileController;
    @FXML private CreateDepartmentController createDepartmentController;

    public void setUser(Response x) {
        System.out.println(x.getRespnoseObject());
        User user = (User) x.getRespnoseObject();
        if (user.getPicture() == null || user.getPicture().equals("null") || user.getPicture().equals("")) {
            user.setPicture("https://supercharge.info/images/avatar-placeholder.png");
        }

        createUserController.setUser(user);
        myProfileController.setUser(user);
        myProfileController.displayUser(user);
        createDepartmentController.setUser(user);
    }

    public void setClientController(Controller c) {
        createUserController.setController(c);
        myProfileController.setController(c);
        createDepartmentController.setController(c);
    }

}
