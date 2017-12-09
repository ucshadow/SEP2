package gui;

import client.Controller;
import common.Response;
import common.User;
import common.WorkingSchedule;
import helpers.Helpers;
import helpers.ResponseReader;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ChangeUserScheduleController implements ResponseReader {

    protected ListProperty<WorkingSchedule> scheduleList = new SimpleListProperty<>();
    protected ListProperty<User> usersWithoutScheduleList = new SimpleListProperty<>();
    private Controller controller;
    private User user;
    private List<WorkingSchedule> allSchedules = new ArrayList<>();
    private List<User> usersWithoutSchedules = new ArrayList<>();

    @FXML
    private ListView allSchedulesList;
    @FXML
    private ListView selected;
    @FXML
    private ListView usersWithoutSchedule;

    @FXML private TextField startDate;
    @FXML private TextField endDate;
    @FXML private TextField startTime;
    @FXML private TextField endTime;
    @FXML private TextField depNo;
    @FXML private TextField usersWithoutScheduleFilter;

    @FXML private Label addStartDateLabel;
    @FXML private Label addEndDateLabel;
    @FXML private Label addStartTimeLabel;
    @FXML private Label addEndTimeLabel;
    @FXML private Label addDepNoLabel;

    @FXML
    public void initialize() {
        allSchedulesList.setOnMouseClicked(event -> addToSelected(allSchedulesList.getSelectionModel().getSelectedItem()));
        usersWithoutSchedule.setOnMouseClicked(event -> addToSelected(usersWithoutSchedule.getSelectionModel().getSelectedItem()));
    }

    private void addToSelected(Object sel) {
        selected.getItems().clear();

        WorkingSchedule schedule;

        if (!(sel instanceof WorkingSchedule)) {
            schedule = new WorkingSchedule("", ((User) sel).getCpr(), "", "", "");
            selected.getItems().add(schedule);
        } else {
            schedule = (WorkingSchedule) sel;
            allSchedules.forEach(e -> {
                if (e.getEmployeeCPR().equals(schedule.getEmployeeCPR())) {
                    selected.getItems().add(e);
                }
            });
        }
    }

    public void getAllWorkingSchedules() {
        if (allSchedules.isEmpty()) {
            controller.getAllWorkingSchedules();
            Helpers.getLastResponse(controller, this);
        }
    }

    public void getUsersWithoutSchedule() {
        if (usersWithoutScheduleList.isEmpty()) {
            controller.getUsersWithoutSchedule();
            Helpers.getLastResponse(controller, this);
        }
    }

    public void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getAllWorkingSchedule")) {
                allSchedules = (ArrayList) res.getRespnoseObject();
                fillUp();
            }
            if (res.getResponse().equals("getUsersWithoutSchedule")) {
                usersWithoutSchedules = (ArrayList) res.getRespnoseObject();
                fillUpUsersWithoutSchedule();
            }
        }
    }

    @FXML
    private void fillUp() {
        allSchedulesList.itemsProperty().bind(scheduleList);
        scheduleList.set(FXCollections.observableArrayList(allSchedules));
    }

    @FXML
    private void fillUpUsersWithoutSchedule() {
        usersWithoutSchedule.itemsProperty().bind(usersWithoutScheduleList);
        usersWithoutScheduleList.set(FXCollections.observableArrayList(usersWithoutSchedules));
    }

    @FXML
    private void addScheduleButt() {
        String startDate_ = startDate.getText();
        String endDate_ = endDate.getText();
        String startTime_ = startTime.getText();
        String endTime_ = endTime.getText();
        String depNo_ = depNo.getText();



        int check = 0;

        check += checkDate(startDate_);
        check += checkTime(startTime_, endTime_);
        check += checkDepNo(depNo_);

        if(check == 0) {
            updateSelected(startDate_, startTime_, endTime_, depNo_);
        }
    }

    private void updateSelected(String startDate, String startTime, String endTime, String depNo) {
        WorkingSchedule first = (WorkingSchedule) selected.getItems().get(0);
        String cpr = first.getEmployeeCPR();
        if(first.getDepartmentNumber().equals("")) {
            selected.getItems().clear();
        }
        WorkingSchedule w = new WorkingSchedule(depNo, cpr, startDate, startTime, endTime);
        selected.getItems().add(w);
    }

    private int checkDate(String start) {
        if(start.isEmpty()) {
            warning(addStartDateLabel, "date");
            return 1;
        }

        return 0;
    }

    private int checkTime(String start, String end) {
        if(start.isEmpty()) {
            warning(addStartTimeLabel, "time");
            return 1;
        }
        if(end.isEmpty()) {
            warning(addEndTimeLabel, "time");
            return 1;
        }
        return 0;
    }

    private int checkDepNo(String dep) {
        if(dep.isEmpty()) {
            warning(addDepNoLabel, "department");
            return 1;
        }
        return 0;
    }

    private void warning(Label label, String text) {
        label.setText("bad " + text);
    }

    @FXML
    private void filterUsersWithoutScheduleByCPR() {
        System.out.println(usersWithoutScheduleFilter.getText());
        ArrayList<User> filtered = new ArrayList<>();
        usersWithoutSchedules.forEach(e -> {
            if(e.getCpr().contains(usersWithoutScheduleFilter.getText())) {
                filtered.add(e);
            }
        });
        usersWithoutSchedule.getItems().clear();
        usersWithoutSchedule.getItems().addAll(filtered);
    }

    @FXML
    private void submitNewSchedule() {
        selected.getItems().forEach(e -> {
            WorkingSchedule s = (WorkingSchedule) e;
            controller.addWorkingSchedule(s.getDepartmentNumber(), s.getEmployeeCPR(), s.getWorkingDate(), s.getStartHours(),
            s.getEndHours());
        });
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
