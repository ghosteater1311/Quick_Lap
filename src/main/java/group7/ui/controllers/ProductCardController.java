package group7.ui.controllers;

import group7.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProductCardController {
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView imageView;
    @FXML private VBox cardContainer;
    @FXML private Button button;

    private Laptop laptop;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setData(Laptop laptop) {
        this.laptop = laptop;
        nameLabel.setText(laptop.getName() != null ? laptop.getName() : "No name");
        priceLabel.setText(laptop.getPrice() > 0 ? String.format("%,d VNĐ", laptop.getPrice()) : "No price");

        String imagePath = "archive/LaptopImage.jpg"; 
        Image image = new Image(new java.io.File(imagePath).toURI().toString());
        imageView.setImage(image);

        /* 
        try {
            imageView.setImage(loadImageWithUserAgent(laptop.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        // 👇 Gán sự kiện click tại đây
        button.setOnAction(event -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                System.out.println(laptop.getId());
                NavigationManager.navigateToProductDetail(stage, laptop.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void initialize() {
        
    }
}