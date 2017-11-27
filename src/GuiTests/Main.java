package GuiTests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        primaryStage.setTitle("Login");
//        primaryStage.setScene(new Scene(root, 500, 400));
//        primaryStage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("MainGUIWindow.fxml"));
//        primaryStage.setTitle("MainWindow");
//        primaryStage.setScene(new Scene(root, 1280, 800));
//        primaryStage.show();

        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("MainGUIWindow.fxml"));
            loader.setController(new GUIController());
            Pane root = loader.load();
            Scene scene = new Scene(root,1280,800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
