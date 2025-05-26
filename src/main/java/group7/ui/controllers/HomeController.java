package group7.ui.controllers;

import group7.data.storage.PostgreSqlDAO;
import group7.data.storage.LaptopPostgreSqlFactory;
import group7.model.Laptop;
import group7.ui.controllers.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class HomeController {
    @FXML private GridPane productGrid;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> brandComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> osComboBox;
    @FXML private Button sortPriceAscButton;
    @FXML private Button sortPriceDescButton;
    @FXML private Button chatButton;

    private PostgreSqlDAO<Laptop> laptopDAO;
    private Stage stage;

    @FXML
    public void initialize() {
        laptopDAO = new PostgreSqlDAO<>("laptops", new LaptopPostgreSqlFactory());
        initializeComboBoxes();
        loadProducts(laptopDAO.getAllProduct());

        // Xử lý sự kiện tìm kiếm
        searchField.textProperty().addListener((obs, oldValue, newValue) -> handleSearch());
        brandComboBox.setOnAction(event -> handleSearch());
        categoryComboBox.setOnAction(event -> handleSearch());
        osComboBox.setOnAction(event -> handleSearch());

        // Xử lý sắp xếp
        sortPriceAscButton.setOnAction(event -> loadProducts(laptopDAO.sortByCostDownToUp()));
        sortPriceDescButton.setOnAction(event -> loadProducts(laptopDAO.sortByCostUpToDown()));

        // Chuyển sang màn hình Chat
        chatButton.setOnAction(event -> {
            try {
                NavigationManager.navigateTo("Chat.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initializeComboBoxes() {
        brandComboBox.getItems().addAll("All", "HP", "ASUS", "DELL", "ACER", "LENOVO", "MSI", "MACBOOK");
        categoryComboBox.getItems().addAll("All", "Gaming", "AI", "Văn phòng");
        osComboBox.getItems().addAll("All", "Windows 11", "macOS");
        brandComboBox.setValue("All");
        categoryComboBox.setValue("All");
        osComboBox.setValue("All");
    }

    private void handleSearch() {
        List<Laptop> laptops = laptopDAO.getAllProduct();
        String brand = brandComboBox.getValue();
        String category = categoryComboBox.getValue();
        String os = osComboBox.getValue();
        String keyword = searchField.getText().trim().toLowerCase();

        // Lọc theo thương hiệu
        if (!brand.equals("All")) {
            laptops = laptopDAO.searchByBrand(brand);
        }

        // Lọc theo danh mục
        if (!category.equals("All")) {
            laptops = laptopDAO.searchBycategory(category);
        }

        // Lọc theo hệ điều hành
        if (!os.equals("All")) {
            laptops = laptopDAO.searchByOs(os);
        }

        // Lọc theo từ khóa
        if (!keyword.isEmpty()) {
            laptops = laptops.stream()
                    .filter(l -> l.getName().toLowerCase().contains(keyword) ||
                            l.getBrand().toLowerCase().contains(keyword) ||
                            l.getCategory().toLowerCase().contains(keyword))
                    .toList();
        }

        loadProducts(laptops);
    }

    private void loadProducts(List<Laptop> laptops) {
        productGrid.getChildren().clear();
        int row = 0, col = 0;
        for (Laptop laptop : laptops) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group7/ui/views/components/ProductCard.fxml"));
                loader.setController(new ProductCardController(laptop, stage));
                productGrid.add(loader.load(), col, row);
                col++;
                if (col > 3) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}