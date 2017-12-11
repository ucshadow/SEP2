package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class GUIController {

    @FXML private CreateUserController createUserController;
    @FXML private MyProfileController myProfileController;
    @FXML private CreateDepartmentController createDepartmentController;
    @FXML private WeeklySchedule weeklyScheduleController;
    @FXML private ChangeUserScheduleController changeUserScheduleController;
    @FXML private AllDepartmentWorkersController allDepartmentWorkersController;

    @FXML private Tab adminTab;
    @FXML private TabPane clientTab;
    @FXML private TabPane adminTabPane;

    private User user;

    @FXML
    public void initialize() {
        clientTab.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue.intValue() == 1) {
                weeklyScheduleController.printSchedule();
            }
            if(newValue.intValue() == 2) {
                allDepartmentWorkersController.getMyDepartments();
            }
        });

        adminTabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue.intValue() == 2) {
                changeUserScheduleController.getAllWorkingSchedules();
                changeUserScheduleController.getUsersWithoutSchedule();
            }
            if(newValue.intValue() == 1) {
                createDepartmentController.onDepartmentTabFocus();
            }
        });

    }

    private void setElevation() {
        if(user.getUserRole().equals("User")) {
            adminTab.setDisable(true);
        }
    }

    public void setUser(Response x) {
        System.out.println(x.getRespnoseObject());
        User user = (User) x.getRespnoseObject();
        this.user = user;
        if (user.getPicture() == null || user.getPicture().equals("null") || user.getPicture().equals("")
                || user.getPicture().equals("picture")) {
            user.setPicture("https://supercharge.info/images/avatar-placeholder.png");
        }

        createUserController.setUser(user);
        myProfileController.setUser(user);
        myProfileController.displayUser(user);
        createDepartmentController.setUser(user);
        weeklyScheduleController.setUser(user);
        changeUserScheduleController.setUser(user);
        allDepartmentWorkersController.setUser(user);

        setElevation();
    }

    public void setClientController(Controller c) {
        createUserController.setController(c);
        myProfileController.setController(c);
        createDepartmentController.setController(c);
        weeklyScheduleController.setController(c);
        changeUserScheduleController.setController(c);
        allDepartmentWorkersController.setController(c);

//        if(user.getUserRole().equals("Manager")) {
//            changeUserScheduleController.getAllWorkingSchedules();
//            changeUserScheduleController.getUsersWithoutSchedule();
//        }
    }

}
