package group7.ui.controllers;

import group7.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

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
        nameLabel.setText(laptop.getName() != null ? laptop.getName() : "Không có tên");
        priceLabel.setText(laptop.getPrice() > 0 ? String.format("%,d VNĐ", laptop.getPrice()) : "Không có giá");

        try {
            imageView.setImage(loadImageWithUserAgent(laptop.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 👇 Gán sự kiện click tại đây
        button.setOnMouseClicked(event -> {
            try {
                NavigationManager.navigateToProductDetail(stage, laptop.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Image loadImageWithUserAgent(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Giả lập trình duyệt
            connection.connect();
            return new Image(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return new Image(getClass().getResource("/images/placeholder.png").toExternalForm()); // ảnh dự phòng nếu lỗi
        }
    }

    @FXML
    public void initialize() {
        // Không thực hiện gì trong initialize, chờ setData
    }
}