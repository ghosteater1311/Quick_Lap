package group7;

import group7.config.*;
import group7.model.*;
import group7.retrieval.*;
import group7.data.storage.*;
import group7.llm.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Path filePath = Paths.get("application_file", "application.properties");

    	ProductDAO<Laptop> admin = new PostgreSqlDAO<Laptop>("laptop",new LaptopPostgreSqlFactory());
    	List<Laptop> laptops = admin.getAllProduct();

        Configuration config = null;
        EmbeddingService embeddingService = null;
        AIClient llm = null;
        ProductSearchService productSearchService = new ProductSearchService();
        List<Laptop> similarLaptop = null;

        try {
            config = new Configuration(filePath);
            embeddingService = new EmbeddingService(config);
            llm = new MistralClient(config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            double[][] embeddings = embeddingService.embedProducts(laptops);
            for (int i = 0; i < laptops.size(); i++) {
                laptops.get(i).setVector(embeddings[i]);
            }
        } catch (IOException e) {
            System.err.println("Loi IO: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Loi Interrupted: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap chuoi: ");
        String query = scanner.nextLine(); 
        double[] queryVector = null;
        try {
            queryVector = embeddingService.embedQuery(query);
        } catch (IOException e) {
            System.err.println("Loi IO: " + e.getMessage());
        }

        try {
            similarLaptop = productSearchService.searchVector(queryVector, laptops, 10);
        } catch (Exception e) {
            System.err.println("Loi khi tim kiem Laptop: " + e.getMessage());
        }
        
        String answer = llm.getResponse(query, similarLaptop);
        System.out.println(answer);
        scanner.close();
    }
}