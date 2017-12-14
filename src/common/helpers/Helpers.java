package common.helpers;

import client.Controller;
import common.Response;
import javafx.concurrent.Task;

public class Helpers {

    private Helpers() {

    }

    public static void getLastResponse(Controller controller, ResponseReader r) {
        Task task = new Task<Response>() {
            @Override
            public Response call() {
                int tries = 0;
                while (tries < 10) {
                    Response r = controller.getLastResponse();
                    if (r != null) {
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
        task.setOnSucceeded(t -> r.responseReader((Response) task.getValue()));
    }

}
