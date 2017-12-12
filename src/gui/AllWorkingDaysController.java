package gui;

import client.Controller;
import common.Response;
import common.User;
import common.WorkingSchedule;
import helpers.Helpers;
import helpers.ResponseReader;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class AllWorkingDaysController implements ResponseReader {

    private Controller controller;
    private User user;

    private int curX = 10;
    private int curY = 10;

    private String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    @FXML private Pane mainPane;

    public void init(User user) {
        System.out.println("Selected user");
        System.out.println(user);

        controller.getWorkingSchedule(user.getCpr());
        Helpers.getLastResponse(controller, this);

        drawMonths();
    }


    private void drawMonths() {
        for(int i = 0; i < months.length; i++) {
            Pane monthPane = new Pane();
            monthPane.setPrefWidth(300);
            monthPane.setPrefHeight(250);
            if(i != 0 && i % 4 == 0) {
                curX = 10;
                curY += 255;
            }

            // add month name text bar
            TextField monthName = new TextField();
            monthName.setEditable(false);
            monthName.setPrefWidth(300);
            monthName.setPrefHeight(11);
            monthName.setLayoutX(0);
            monthName.setLayoutY(0);
            monthName.setStyle(" -fx-background-color: none; -fx-text-fill: red;");
            monthName.setText(months[i]);
            monthName.setAlignment(Pos.CENTER);
            monthPane.getChildren().add(monthName);

            monthPane.setLayoutX(curX);
            monthPane.setLayoutY(curY);
            monthPane.setStyle("-fx-border-color: green; -fx-font-size: 10px;");

            // add day rows
//            addDayRows(i, monthPane);

            mainPane.getChildren().add(monthPane);

            curX += 305;
        }
    }

//    private void addDayRows(int month, Pane monthPane) {
//        LocalDate date = LocalDate.of(2017, month + 1, 1);
//        IntStream.range(0, date.lengthOfMonth()).forEach(i -> {
//            TextField day = new TextField();
//            day.setEditable(false);
//            day.setAlignment(Pos.BASELINE_LEFT);
//            day.setStyle("-fx-background-color: none;");
//            day.setStyle("-fx-font-size: 10px;");
//            day.setLayoutY(11 * i + 1);
//            day.setPrefWidth(300);
//            day.setPrefHeight(11);
//            day.setText(String.valueOf(i + 1));
//            monthPane.getChildren().add(day);
//        });
//    }

    @Override
    public void responseReader(Response res) {
        System.out.println("Schedule:");
        ArrayList<WorkingSchedule> arr = (ArrayList) res.getRespnoseObject();
        for(int i = 0; i < arr.size(); i++) {
            LocalDate l = LocalDate.parse(arr.get(i).getWorkingDate());
            System.out.println(l.getMonthValue() - 1);
            Pane selectedMonth = (Pane) mainPane.getChildren().get(l.getMonthValue() - 1);
            addDays(selectedMonth, String.valueOf(l.getDayOfMonth()),
                    arr.get(i).getStartHours(), arr.get(i).getEndHours(), i);
        }
    }

    public void addDays(Pane pane, String day, String start, String end, int position) {
       TextField dayField = new TextField();
       dayField.setEditable(false);
       dayField.setPrefHeight(11);
       dayField.setPrefWidth(300);
       dayField.setLayoutY(11 * (position + 1));
       dayField.setText(day + ": " + start  + " to " + end);
       dayField.setStyle("-fx-background-color: none; -fx-font-size: 10px;");
       pane.getChildren().add(dayField);
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
