package client.gui;

import client.Controller;
import common.Response;
import common.User;
import common.WorkingSchedule;
import common.helpers.Helpers;
import common.helpers.ResponseReader;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeeklySchedule implements ResponseReader {

    private Controller controller;
    private User user;

    @FXML
    private GridPane parent;

    public void printSchedule() {
        getWorkingSchedule();
    }

    public void getWorkingSchedule() {
        controller.getWorkingSchedule(user.getCpr());
        Helpers.getLastResponse(controller, this);
    }

    public void responseReader(Response res) {
        if (res != null) {
            ArrayList<WorkingSchedule> arr = (ArrayList) res.getRespnoseObject();
            arr.forEach(this::populateView);
        }
    }

    private void populateView(WorkingSchedule w) {
        String[] params = w.getWorkingDate().split("-");
        int year = Integer.valueOf(params[0]);
        int month = Integer.valueOf(params[1]);
        int day = Integer.valueOf(params[2]);

        int start = Integer.parseInt(w.getStartHours().split(":")[0]);
        int end = Integer.parseInt(w.getEndHours().split(":")[0]);

        int weekDay = LocalDate.of(year, month, day).getDayOfWeek().getValue();

        for(int i = start; i < end; i++) {
            Region r = new Region();
            r.setStyle("-fx-background-color: green; -fx-border-style: solid;");
            parent.add(r, weekDay, i + 1);
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
