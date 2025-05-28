package group7;

import group7.ui.controllers.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        NavigationManager.navigateTo("Home.fxml", primaryStage);
        primaryStage.setY(200);
        primaryStage.setTitle("QuickLap");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
// chi nhap dduoc tieng anh thoi anh oi