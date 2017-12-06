package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.concurrent.Task;

public class WeeklySchedule {

    private Controller controller;
    private User user;

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
            System.out.println(res.getRespnoseObject());
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
