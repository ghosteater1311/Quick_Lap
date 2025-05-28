package group7.ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationManager {
    public static void navigateTo(String fxmlFile, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource("/group7/ui/view/" + fxmlFile));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToProductDetail(Stage stage, String laptopId) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource("/group7/ui/view/ProductDetail.fxml"));
        ProductDetailController controller = new ProductDetailController();
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        controller.setLaptopId(laptopId); // Truyền ID để tải chi tiết laptop
        stage.setScene(scene);
        stage.show();
    }
}