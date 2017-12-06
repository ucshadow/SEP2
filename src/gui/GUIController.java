package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class GUIController {

    @FXML private CreateUserController createUserController;
    @FXML private MyProfileController myProfileController;
    @FXML private CreateDepartmentController createDepartmentController;
    @FXML private WeeklySchedule weeklyScheduleController;

    @FXML private TabPane clientTab;

    @FXML
    public void initialize() {
        clientTab.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                if(newValue.intValue() == 1) {
                    weeklyScheduleController.printSchedule();
                }
                System.out.println(newValue);
            }
        });
    }

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
        weeklyScheduleController.setUser(user);
    }

    public void setClientController(Controller c) {
        createUserController.setController(c);
        myProfileController.setController(c);
        createDepartmentController.setController(c);
        weeklyScheduleController.setController(c);
    }

}
