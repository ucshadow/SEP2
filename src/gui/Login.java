package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.databasehandlers.LogInMethod;
import main.userlogin.LogIn;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage mainStage) {
        mainStage.setTitle("CYRN");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text SceneTitle = new Text("Welcome Back!");
        SceneTitle.setFont(Font.font("Roboto", FontWeight.BOLD, 20));
        grid.add(SceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Uername: ");
        userName.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
        grid.add(userName, 0, 1);


        TextField userTF = new TextField();
        grid.add(userTF, 1, 1);

        //add Label for Pass
        Label pass = new Label("Password:");
        pass.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
        grid.add(pass, 0, 2);

        //Set text field for username label
        TextField passTF = new TextField();
        grid.add(passTF, 1, 2);

        Button signIn = new Button("Sign in");
        signIn.setFont(Font.font("Roboto", FontWeight.BOLD, 12));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(signIn);
        grid.add(hbBtn, 1, 4);

        Button quit = new Button("Quit");
        quit.setFont(Font.font("Roboto", FontWeight.BOLD, 12));
        HBox hbQuit = new HBox(20);
        hbQuit.setAlignment(Pos.BOTTOM_RIGHT);
        hbQuit.getChildren().add(quit);
        grid.add(hbQuit, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        quit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (LogInMethod.authentication(userTF.getText(), passTF.getText())) {
                    try {
                        new EmployeeGUI().start();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
//                mainStage.close();
            }
        });


        //Add to stage and run
        Scene scene = new Scene(grid, 300, 275);
        mainStage.setScene(scene);
        mainStage.show();
    }


}
