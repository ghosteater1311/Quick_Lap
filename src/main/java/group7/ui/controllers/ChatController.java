package group7.ui.controllers;

import group7.data.storage.PostgreSqlDAO;
import group7.data.storage.LaptopPostgreSqlFactory;
import group7.llm.MistralClient;
import group7.retrieval.ProductSearchService;
import group7.retrieval.EmbeddingService;
import group7.model.Laptop;
import group7.config.Configuration;
import group7.ui.controllers.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button; // ✔ Thêm dòng này
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class ChatController {
    @FXML private TextField queryField;
    @FXML private TextArea responseArea;
    @FXML private VBox productContainer;
    @FXML private Button searchButton;
    @FXML private Button backButton;

    private PostgreSqlDAO<Laptop> laptopDAO;
    private ProductSearchService searchService;
    private EmbeddingService embeddingService;
    private MistralClient mistralClient;
    private Stage stage;

    @FXML
    public void initialize() {
        laptopDAO = new PostgreSqlDAO<>("laptops", new LaptopPostgreSqlFactory());
        Configuration config = new Configuration(); // Giả định Configuration đã được cấu hình
        searchService = new ProductSearchService();
        embeddingService = new EmbeddingService(config);
        mistralClient = new MistralClient(config);

        searchButton.setOnAction(event -> handleAISearch());
        backButton.setOnAction(event -> {
            try {
                NavigationManager.navigateTo("Home.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void handleAISearch() {
        String query = queryField.getText().trim();
        if (query.isEmpty()) {
            responseArea.setText("Vui lòng nhập câu hỏi.");
            return;
        }

        try {
            // Lấy danh sách laptop từ cơ sở dữ liệu
            List<Laptop> laptops = laptopDAO.getAllProduct();

            // Chuyển đổi câu hỏi thành vector
            double[] queryVector = embeddingService.embedQuery(query);

            // Tìm kiếm các sản phẩm phù hợp bằng cosine similarity
            List<Laptop> topLaptops = searchService.searchVector(queryVector, laptops, 5);

            // Gửi câu hỏi và danh sách sản phẩm đến MistralClient
            String response = mistralClient.getResponse(query, topLaptops);
            responseArea.setText(response);

            // Hiển thị danh sách sản phẩm gợi ý
            productContainer.getChildren().clear();
            for (Laptop laptop : topLaptops) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group7/ui/views/components/ProductCard.fxml"));
                loader.setController(new ProductCardController(laptop, stage));
                productContainer.getChildren().add(loader.load());
            }
        } catch (Exception e) {
            responseArea.setText("Lỗi khi xử lý câu hỏi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}