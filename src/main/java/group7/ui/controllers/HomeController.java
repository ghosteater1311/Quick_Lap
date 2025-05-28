package group7.ui.controllers;

import group7.data.storage.PostgreSqlDAO;
import group7.data.storage.LaptopPostgreSqlFactory;
import group7.model.Laptop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController {
    @FXML private TextField searchField;
    @FXML private Button chatButton;
    @FXML private GridPane gridPane;
    @FXML private ComboBox<String> brandComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> osComboBox;
    @FXML private ComboBox<String> cost;
    

    private PostgreSqlDAO<Laptop> laptopDAO;
    private Stage stage;
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

        loadProducts(laptops);
    }

    @FXML
    private void switchToChatbot(MouseEvent event) {
    
    }

    private void loadProducts(List<Laptop> laptops) {
        gridPane.getChildren().clear(); // Xóa các sản phẩm cũ
        int row = 1, col = 0;
        for (Laptop laptop : laptops) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group7/ui/view/ProductCard.fxml"));
                ProductCardController controller = new ProductCardController();
                loader.setController(controller);    
                VBox vBox = loader.load();
                controller.setData(laptop);
                gridPane.add(vBox, col++, row);
                GridPane.setMargin(vBox, new Insets(20, 10, 10, 10));

                if (col == 5) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void initialize() {
        final String ITEM_FILE_PATH = "/group7/ui/view/ProductCard.fxml";
        int column = 0;
        int row = 1;
        laptopDAO = new PostgreSqlDAO<>("laptop", new LaptopPostgreSqlFactory());
        List<Laptop> laptops = laptopDAO.getAllProduct();

        for(int i = 0; i < laptops.size(); i++){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(ITEM_FILE_PATH));
                ProductCardController productCardController = new ProductCardController();
                fxmlLoader.setController(productCardController);
                VBox vBox = new VBox();
                vBox = fxmlLoader.load();
                productCardController.setStage(stage);
                productCardController.setData(laptops.get(i));

                if (column == 5) {
                    column = 0;
                    row++;
                }

                gridPane.add(vBox, column++, row);
                GridPane.setMargin(vBox, new Insets(20, 10, 10, 10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        brandComboBox.setOnAction(event -> handleSearch());
        categoryComboBox.setOnAction(event -> handleSearch());
        osComboBox.setOnAction(event -> handleSearch());

        chatButton.setOnAction(event -> {
            try { 
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                NavigationManager.navigateTo("Chat.fxml", stage);
            } catch (IOException e) {
               e.printStackTrace();
            }
        });

        initializeComboBoxes();
    }

}