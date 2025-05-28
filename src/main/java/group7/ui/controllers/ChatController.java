package group7.ui.controllers;

import group7.model.*;
import group7.data.storage.*;
import group7.llm.*;
import group7.retrieval.*;
import group7.config.Configuration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button; 
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import javafx.util.Duration;

public class ChatController {
    @FXML private TextField txtQuestion;
    @FXML private TextArea txtResponse;
    @FXML private Button btnSearch;
    @FXML private Button btnBackToHome;

    private List<Laptop> laptops;
    private List<Laptop> similarLaptops;
    private Configuration config;
    private Path filePath;
    private PostgreSqlDAO<Laptop> laptopDAO;
    private ProductSearchService productSearchService;
    private EmbeddingService embeddingService;
    private AIClient llm;
    private Stage stage;

    @FXML
    public void initialize() {
        filePath = Paths.get("./application_file", "application.properties");

        laptopDAO = new PostgreSqlDAO<>("laptop", new LaptopPostgreSqlFactory());
        laptops = laptopDAO.getAllProduct();
        productSearchService = new ProductSearchService();

        try {
            config = new Configuration(filePath);
            embeddingService = new EmbeddingService(config);
            llm = new MistralClient(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSearch.setOnAction(event -> handleAISearch());
        btnBackToHome.setOnAction(event -> {
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

    private void handleAISearch() {
        String query = txtQuestion.getText().trim();
        if (query.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thông tin quan trọng");
            alert.setContentText("Please enter a question.");
            alert.showAndWait();
            return;
        }

        try {
            try {
                double[][] embeddings = embeddingService.embedProducts(laptops);
                for (int i = 0; i < laptops.size(); i++) {
                    laptops.get(i).setVector(embeddings[i]);
                }
            } catch (IOException e) {
                System.err.println("IO Error: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Interrupted Error: " + e.getMessage());
            }

            double[] queryVector = null;
            try {
                queryVector = embeddingService.embedQuery(query);
            } catch (IOException e) {
                System.err.println("IO Error: " + e.getMessage());
            }

            try {
                similarLaptops = productSearchService.searchVector(queryVector, laptops, 10);
            } catch (Exception e) {
                System.err.println("Error when finding similar laptops: " + e.getMessage());
            }

            String answer = llm.getResponse(query, similarLaptops);
            displayTextGradually(answer);
        } catch (Exception e) {
            txtResponse.setText("Error when processing query: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayTextGradually(String text) {
        txtResponse.clear(); // Xóa nội dung cũ
        Timeline timeline = new Timeline();
        final int[] index = {0}; // Biến đếm ký tự

        KeyFrame keyFrame = new KeyFrame(
            Duration.millis(20), // Tốc độ hiển thị mỗi ký tự (50ms)
            event -> {
                if (index[0] < text.length()) {
                    txtResponse.appendText(String.valueOf(text.charAt(index[0])));
                    index[0]++;
                } else {
                    timeline.stop(); // Dừng khi hết văn bản
                }
            }
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Lặp vô hạn cho đến khi dừng
        timeline.play();
    }
}