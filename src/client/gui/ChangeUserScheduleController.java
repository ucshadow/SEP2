package client.gui;

import client.Controller;
import common.Response;
import common.User;
import common.WorkingSchedule;
import common.helpers.Helpers;
import common.helpers.ResponseReader;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ChangeUserScheduleController implements ResponseReader {

    private ListProperty<WorkingSchedule> scheduleList = new SimpleListProperty<>();
    private ListProperty<String> usersWithoutScheduleList__ = new SimpleListProperty<>();
    private Controller controller;
    private User user;
    private List<WorkingSchedule> allSchedules = new ArrayList<>();
    private List<String> usersWithoutSchedules__ = new ArrayList<>();
    private ArrayList<String> depNumbers = new ArrayList<>();
    private boolean canBeSubmitted = false;

    @FXML
    private ListView allSchedulesList;
    @FXML
    private ListView selected;
    @FXML
    private ListView usersWithoutScheduleActualListView;

    @FXML
    private TextField startDate;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private TextField depNo;
    @FXML
    private TextField usersWithoutScheduleFilter;
    @FXML
    private TextField allSchedulesFilter;

    @FXML
    private Label addStartDateLabel;
    @FXML
    private Label addStartTimeLabel;
    @FXML
    private Label addEndTimeLabel;
    @FXML
    private Label addDepNoLabel;

    @FXML
    public void initialize() {
        allSchedulesList.setOnMouseClicked(event -> addToSelected(allSchedulesList.getSelectionModel().getSelectedItem()));
        usersWithoutScheduleActualListView.setOnMouseClicked(event -> addToSelected(usersWithoutScheduleActualListView.getSelectionModel().getSelectedItem()));
    }

    private void addToSelected(Object sel) {
        if (sel != null) {
            selected.getItems().clear();

            WorkingSchedule schedule;

            if (!(sel instanceof WorkingSchedule)) {
                schedule = new WorkingSchedule("", ((String) sel).split(", ")[0], "", "", "");
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
    }

    public void getAllWorkingSchedules() {
        if (allSchedules.isEmpty()) {
            controller.getAllWorkingSchedules();
            Helpers.getLastResponse(controller, this);
        }
    }

    public void getUsersWithoutSchedule() {
        if (usersWithoutScheduleList__.isEmpty()) {
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
                ArrayList<String> temp = new ArrayList();
                ArrayList<User> response = (ArrayList<User>) res.getRespnoseObject();
                response.forEach(e -> temp.add(e.getCpr() + ", with first name: " + e.getFirstName()));
                usersWithoutSchedules__ = temp;
                fillUpUsersWithoutSchedule();
            }
        }
        allSchedules.forEach(e -> {
            if (!depNumbers.contains(e.getDepartmentNumber())) {
                depNumbers.add(e.getDepartmentNumber());
            }
        });
    }

    @FXML
    private void fillUp() {
        allSchedulesList.itemsProperty().bind(scheduleList);
        scheduleList.set(FXCollections.observableArrayList(allSchedules));
    }

    @FXML
    private void fillUpUsersWithoutSchedule() {
        usersWithoutScheduleActualListView.itemsProperty().bind(usersWithoutScheduleList__);
        usersWithoutScheduleList__.set(FXCollections.observableArrayList(usersWithoutSchedules__));
    }

    @FXML
    private void addScheduleButt() {
        String startDate_ = startDate.getText();
        String startTime_ = startTime.getText();
        String endTime_ = endTime.getText();
        String depNo_ = depNo.getText();

        canBeSubmitted = false;

        int check = 0;

        check += checkDate(startDate_);
        check += checkTime(startTime_, endTime_);
        check += checkDepNo(depNo_);

        if (check == 0) {
            updateSelected(startDate_, startTime_, endTime_, depNo_);
            canBeSubmitted = true;
        }
    }

    private void updateSelected(String startDate, String startTime, String endTime, String depNo) {
        WorkingSchedule first = (WorkingSchedule) selected.getItems().get(0);
        String cpr = first.getEmployeeCPR();
        if (first.getDepartmentNumber().equals("")) {
            selected.getItems().clear();
        }
        WorkingSchedule w = new WorkingSchedule(depNo, cpr, startDate, startTime, endTime);
        selected.getItems().add(w);
    }

    private int checkDate(String start) {
        if (start.isEmpty()) {
            warning(addStartDateLabel, "date");
            return 1;
        }

        return 0;
    }

    private int checkTime(String start, String end) {
        if (start.isEmpty()) {
            warning(addStartTimeLabel, "time");
            return 1;
        }
        if (end.isEmpty()) {
            warning(addEndTimeLabel, "time");
            return 1;
        }
        if (!isValidTimeFormat(start) || !isValidTimeFormat(end)) {
            warning(addEndTimeLabel, "time format");
            return 1;
        }
        if (!isLesserOrEquals(start, end)) {
            warning(addEndTimeLabel, "time interval");
            return 1;
        }
        return 0;
    }

    private boolean isLesserOrEquals(String start, String end) {
        String[] arr = start.split(":");
        String[] arr2 = end.split(":");
        return Integer.parseInt(arr[0]) <= Integer.parseInt(arr2[0]);
    }

    private boolean isValidTimeFormat(String time) {
        String[] arr = time.split(":");
        int i;
        int j;
        if (arr.length != 2) {
            return false;
        }
        try {
            i = Integer.parseInt(arr[0]);
            j = Integer.parseInt(arr[1]);
        } catch (Exception e) {
            return false;
        }
        return i >= 0 && i < 24 && j >= 0 && j < 60;
    }

    private int checkDepNo(String dep) {
        if (dep.isEmpty()) {
            warning(addDepNoLabel, "department");
            return 1;
        }
        if (!depNumbers.contains(dep)) {
            warning(addDepNoLabel, "dep number");
            return 1;
        }
        return 0;
    }

    private void warning(Label label, String text) {
        label.setText("bad " + text);
    }

    @FXML
    private void filterUsersWithoutScheduleByCPR() {
        ArrayList<String> filtered = new ArrayList<>();
        usersWithoutSchedules__.forEach(e -> {
            if (e.split(", ")[0].contains(usersWithoutScheduleFilter.getText())) {
                filtered.add(e);
            }
        });
        usersWithoutScheduleActualListView.getItems().clear();
        usersWithoutScheduleActualListView.getItems().addAll(filtered);
    }

    @FXML
    private void filterByCPR() {
        ArrayList<WorkingSchedule> filtered = new ArrayList<>();
        allSchedules.forEach(e -> {
            if (e.getEmployeeCPR().contains(allSchedulesFilter.getText())) {
                filtered.add(e);
            }
        });
        allSchedulesList.getItems().clear();
        allSchedulesList.getItems().addAll(filtered);
    }

    @FXML
    private void submitNewSchedule() {
        System.out.println(canBeSubmitted);
        if (canBeSubmitted) {
            selected.getItems().forEach(e -> {
                WorkingSchedule s = (WorkingSchedule) e;
                controller.addWorkingSchedule(s.getDepartmentNumber(), s.getEmployeeCPR(), s.getWorkingDate(), s.getStartHours(),
                        s.getEndHours());
            });
        } else {
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
