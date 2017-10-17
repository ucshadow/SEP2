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

public class Login extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        // Set title
        primaryStage.setTitle("Fx Redo ");

        //setLayout to grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        //Set text to secene

        Text SceneTitle = new Text("Welcome");
        SceneTitle.setFont(Font.font("Roboto", FontWeight.BOLD,20));
        grid.add(SceneTitle,0,0,2,1);

        //add Label for username
        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        //Set text field for username label
        TextField userTF = new TextField();
        grid.add(userTF,1,1);

        //add Label for Pass
        Label pass = new Label("Password:");
        grid.add(pass, 0, 2);

        //Set text field for username label
        TextField passTF = new TextField();
        grid.add(passTF,1,2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Button Quit = new Button("Quit");
        HBox hbQuit = new HBox(20);
        hbQuit.setAlignment(Pos.BOTTOM_RIGHT);
        hbQuit.getChildren().add(Quit);
        grid.add(hbQuit, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        Quit.setOnAction(new EventHandler<ActionEvent>() {


            public void handle(ActionEvent e) {
               primaryStage.close();
            }
        });

        //Add to stage and run
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
