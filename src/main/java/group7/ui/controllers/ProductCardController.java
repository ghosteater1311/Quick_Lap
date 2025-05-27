package group7.ui.controllers;

import group7.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class ProductCardController {
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView imageView;
    @FXML private VBox cardContainer;

    private Laptop laptop;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setData(Laptop laptop) {
        this.laptop = laptop;
        nameLabel.setText(laptop.getName() != null ? laptop.getName() : "Không có tên");
        priceLabel.setText(laptop.getPrice() > 0 ? String.format("%,d VNĐ", laptop.getPrice()) : "Không có giá");
        try {
            String url = laptop.getUrl();
            imageView.setImage(url != null && !url.isEmpty() ? new Image(url) : new Image("/images/placeholder.png"));
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra lỗi
        }

            // Sự kiện nhấp vào card
            if (cardContainer != null) {
                cardContainer.setOnMouseClicked(event -> {
                    try {
                        NavigationManager.navigateToProductDetail(stage, laptop.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
    }

    @FXML
    public void initialize() {
        // Không thực hiện gì trong initialize, chờ setData
    }
}