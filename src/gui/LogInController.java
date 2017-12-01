package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    private Controller c = new Controller();

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label errorText;

    @FXML
    private void handleLogIn(ActionEvent event) {

        c.logIn(username.getText(), password.getText());

        final Response[] res = {null};

        try {
            Task task = new Task<Void>() {
                @Override
                public Void call() {
                    int max = 10;
                    for (int i = 1; i <= max; i++) {
                        System.out.println(i);

                        Response r = c.getLastResponse();

                        if (r != null) {
                            res[0] = r;
                            return null;
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Max retries exceeded");
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            if (res[0] != null) {
                if (handleLoginFailure(res[0])) {
                    errorText.setText("Wrong username or password");
                    return;
                } else {
                    createMainWindow(res[0]);
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    return;
                }

            }
        }

    }

    private boolean handleLoginFailure(Response r) {
        User u = (User) r.getRespnoseObject();
        return u.getUsername() == null;
    }

    private void createMainWindow(Response r) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainGUIWindow.fxml"));
            root = fxmlLoader.load();
            GUIController controller = fxmlLoader.getController();
            controller.setUser(r);
            controller.setClientController(c);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Manager");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}