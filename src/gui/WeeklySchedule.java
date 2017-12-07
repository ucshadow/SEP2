package gui;

import client.Controller;
import common.Response;
import common.User;
import common.WorkingSchedule;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeeklySchedule {

    private Controller controller;
    private User user;

    @FXML
    private GridPane parent;

    public void printSchedule() {
        getWorkingSchedule();
    }

    public void getWorkingSchedule() {
        controller.getWorkingSchedule(user.getCpr());
        Task task = new Task<Response>() {
            @Override
            public Response call() {
                int tries = 0;
                while (tries < 10) {
                    Response r = controller.getLastResponse();
                    if (r != null) {
                        System.out.println("that null");

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
            System.out.println("Schedule.");
            ArrayList<WorkingSchedule> arr = (ArrayList) res.getRespnoseObject();
            arr.forEach(this::populateView);
        }
    }

    private void populateView(WorkingSchedule w) {
        System.out.println("Working schedule is: ");
        System.out.println(w);
        String[] params = w.getWorkingDate().split("-");
        int year = Integer.valueOf(params[0]);
        int month = Integer.valueOf(params[1]);
        int day = Integer.valueOf(params[2]);

        int start = Integer.parseInt(w.getStartHours().split(":")[0]);
        int end = Integer.parseInt(w.getEndHours().split(":")[0]);

        int weekDay = LocalDate.of(year, month, day).getDayOfWeek().getValue();

//        System.out.println(LocalDate.of(year, month, day).getDayOfWeek().getValue());

        for(int i = start; i < end; i++) {
            Region r = new Region();
            r.setStyle("-fx-background-color: green; -fx-border-style: solid;");
            parent.add(r, weekDay, i + 1);
        }



        //Gets the list of children of this Parent.
//        ObservableList<Node> ch = parent.getChildren();

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
