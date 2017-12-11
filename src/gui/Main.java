package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("Login.fxml"));
            Pane root = loader.load();
            Scene scene = new Scene(root,600,400);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(e -> System.exit(0));
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
