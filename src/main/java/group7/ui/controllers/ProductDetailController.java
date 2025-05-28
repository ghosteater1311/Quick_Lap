package group7.ui.controllers;

import group7.data.storage.PostgreSqlDAO;
import group7.data.storage.LaptopPostgreSqlFactory;
import group7.model.Laptop;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.List;

public class ProductDetailController {
    @FXML private Label nameLabel;
    @FXML private Label brandLabel;
    @FXML private Label categoryLabel;
    @FXML private Label osLabel;
    @FXML private Label priceLabel;
    @FXML private Label ratingLabel;
    @FXML private Label cpuLabel;
    @FXML private Label gpuLabel;
    @FXML private Label ramLabel;
    @FXML private Label storageLabel;
    @FXML private Label diskTypeLabel;
    @FXML private Label resolutionLabel;
    @FXML private Label screenSizeLabel;
    @FXML private Label weightLabel;
    @FXML private Label batteryLabel;
    @FXML private ImageView imageView;
    @FXML private Button backButton;

    private PostgreSqlDAO<Laptop> laptopDAO;
    private String laptopId;
    private Stage stage;

    @FXML
    public void initialize() {
        laptopDAO = new PostgreSqlDAO<>("laptops", new LaptopPostgreSqlFactory());
        backButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                NavigationManager.navigateTo("Home.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLaptopId(String laptopId) {
        this.laptopId = laptopId;
        loadLaptopDetails();
    }

    private void loadLaptopDetails() {
        List<Laptop> laptops = laptopDAO.getAllProduct();
        Laptop laptop = laptops.stream()
                .filter(l -> l.getId().equals(laptopId))
                .findFirst()
                .orElse(null);

        if (laptop != null) {
            nameLabel.setText(laptop.getName());
            brandLabel.setText(laptop.getBrand());
            categoryLabel.setText(laptop.getCategory());
            osLabel.setText(laptop.getOs());
            priceLabel.setText(String.format("%,d VNĐ", laptop.getPrice()));
            ratingLabel.setText(String.format("%.1f", laptop.getRating()));
            cpuLabel.setText(laptop.getCpu());
            gpuLabel.setText(laptop.getGpu());
            ramLabel.setText(laptop.getRam() + " GB");
            storageLabel.setText(laptop.getStorage() >= 1000 ? "1 TB" : laptop.getStorage() + " GB");
            diskTypeLabel.setText(laptop.getDiskType());
            resolutionLabel.setText(laptop.getResolution());
            screenSizeLabel.setText(String.format("%.1f inch", laptop.getScreenSize()));
            weightLabel.setText(String.format("%.1f kg", laptop.getWeight()));
            batteryLabel.setText(String.format("%.1f hours", laptop.getBatteryLife()));
            if (laptop.getUrl() != null && !laptop.getUrl().isEmpty()) {
                imageView.setImage(new Image(laptop.getUrl()));
            }
        } else {
            nameLabel.setText("Không tìm thấy sản phẩm");
        }
    }
}