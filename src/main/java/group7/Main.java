package group7;

import group7.model.*;
import group7.search.rag.EmbeddingService;
import group7.search.rag.GPTClient;
import group7.search.rag.ProductSearchService;
import group7.data.storage.*;

import java.io.IOException;
import java.util.List;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        /*
        Laptop l1 = new Laptop("L001", "Aspire 5", "Acer", "Ultrabook", "Windows 10", 1500, 4.5f, "Intel i5-1135G7", "Intel Iris Xe", 60, 8, 512, "SSD", "1920x1080", 15.6f, 1.8f, 8.0f, "http://example.com/laptop1");
        Laptop l2 = new Laptop("L002", "ThinkPad X1", "Lenovo", "Business", "Windows 11", 2200, 4.7f, "Intel i7-1165G7", "Intel Iris Xe", 90, 16, 1024, "SSD", "2560x1440", 14.0f, 1.3f, 10.0f, "http://example.com/laptop2");
        Laptop l3 = new Laptop("L003", "MacBook Air", "Apple", "Ultrabook", "macOS", 1800, 4.8f, "Apple M1", "Integrated", 60, 8, 512, "SSD", "2560x1600", 13.3f, 1.29f, 15.0f, "http://example.com/laptop3");
        Laptop l4 = new Laptop("L004", "ROG Zephyrus", "ASUS", "Gaming", "Windows 11", 2500, 4.6f, "AMD Ryzen 9 5900HS", "NVIDIA RTX 3060", 144, 16, 1024, "SSD", "1920x1080", 15.6f, 1.9f, 7.5f, "http://example.com/laptop4");
        Laptop l5 = new Laptop("L005", "XPS 13", "Dell", "Ultrabook", "Windows 11", 2000, 4.7f, "Intel i7-1185G7", "Intel Iris Xe", 60, 16, 512, "SSD", "1920x1200", 13.4f, 1.2f, 12.0f, "http://example.com/laptop5");
        Laptop l6 = new Laptop("L006", "Surface Laptop 4", "Microsoft", "Ultrabook", "Windows 10", 1800, 4.4f, "Intel i5-1135G7", "Intel Iris Xe", 60, 8, 256, "SSD", "2256x1504", 13.5f, 1.3f, 11.5f, "http://example.com/laptop6");
        Laptop l7 = new Laptop("L007", "Pavilion Gaming", "HP", "Gaming", "Windows 10", 1300, 4.2f, "Intel i5-10300H", "NVIDIA GTX 1650", 60, 8, 512, "SSD", "1920x1080", 15.6f, 2.3f, 6.5f, "http://example.com/laptop7");
        Laptop l8 = new Laptop("L008", "Legion 5", "Lenovo", "Gaming", "Windows 11", 1600, 4.5f, "AMD Ryzen 7 5800H", "NVIDIA RTX 3050", 165, 16, 1024, "SSD", "1920x1080", 15.6f, 2.25f, 8.0f, "http://example.com/laptop8");
        Laptop l9 = new Laptop("L009", "MacBook Pro 14", "Apple", "Professional", "macOS", 3000, 4.9f, "Apple M1 Pro", "Integrated", 120, 16, 1024, "SSD", "3024x1964", 14.2f, 1.6f, 17.0f, "http://example.com/laptop9");
        Laptop l10 = new Laptop("L010", "Vivobook 15", "ASUS", "Ultrabook", "Windows 10", 700, 4.0f, "Intel i3-1115G4", "Intel UHD", 60, 8, 256, "SSD", "1920x1080", 15.6f, 1.7f, 6.0f, "http://example.com/laptop10");
        Laptop l11 = new Laptop("L011", "IdeaPad 3", "Lenovo", "Office", "Windows 10", 600, 3.9f, "AMD Ryzen 5 3500U", "Integrated", 60, 8, 512, "SSD", "1920x1080", 15.6f, 1.85f, 7.0f, "http://example.com/laptop11");
        Laptop l12 = new Laptop("L012", "Swift 3", "Acer", "Ultrabook", "Windows 10", 900, 4.3f, "Intel i7-1165G7", "Intel Iris Xe", 60, 8, 512, "SSD", "2560x1440", 14.0f, 1.2f, 11.0f, "http://example.com/laptop12");
        Laptop l13 = new Laptop("L013", "ROG Strix", "ASUS", "Gaming", "Windows 11", 2200, 4.6f, "Intel i7-10875H", "NVIDIA RTX 2070", 144, 16, 1024, "SSD", "1920x1080", 15.6f, 2.5f, 6.5f, "http://example.com/laptop13");
        Laptop l14 = new Laptop("L014", "MacBook Air M2", "Apple", "Ultrabook", "macOS", 2200, 4.9f, "Apple M2", "Integrated", 60, 8, 512, "SSD", "2560x1664", 13.6f, 1.24f, 18.0f, "http://example.com/laptop14");
        Laptop l15 = new Laptop("L015", "Nitro 5", "Acer", "Gaming", "Windows 11", 1300, 4.3f, "Intel i5-11400H", "NVIDIA RTX 3050", 144, 8, 512, "SSD", "1920x1080", 15.6f, 2.3f, 8.5f, "http://example.com/laptop15");
        Laptop l16 = new Laptop("L016", "Latitude 7420", "Dell", "Business", "Windows 10", 2100, 4.5f, "Intel i7-1185G7", "Intel Iris Xe", 60, 16, 512, "SSD", "1920x1080", 14.0f, 1.3f, 13.0f, "http://example.com/laptop16");
        Laptop l17 = new Laptop("L017", "Envy 13", "HP", "Ultrabook", "Windows 11", 1400, 4.2f, "Intel i5-1135G7", "Intel Iris Xe", 60, 8, 256, "SSD", "1920x1080", 13.3f, 1.2f, 10.5f, "http://example.com/laptop17");
        Laptop l18 = new Laptop("L018", "Predator Helios", "Acer", "Gaming", "Windows 11", 1800, 4.4f, "Intel i7-10750H", "NVIDIA RTX 2060", 144, 16, 1024, "SSD", "1920x1080", 15.6f, 2.5f, 7.0f, "http://example.com/laptop18");
        Laptop l19 = new Laptop("L019", "ThinkBook 14", "Lenovo", "Business", "Windows 10", 1000, 4.0f, "AMD Ryzen 5 4500U", "Integrated", 60, 8, 512, "SSD", "1920x1080", 14.0f, 1.5f, 9.0f, "http://example.com/laptop19");
        Laptop l20 = new Laptop("L020", "Inspiron 15", "Dell", "Office", "Windows 10", 800, 3.8f, "Intel i3-1005G1", "Intel UHD", 60, 8, 256, "SSD", "1920x1080", 15.6f, 1.8f, 6.5f, "http://example.com/laptop20");
        */
        // List<Laptop> laptops = new ArrayList<>();
        /* 
        laptops.add(l1);
        laptops.add(l2);
        laptops.add(l3);
        laptops.add(l4);
        laptops.add(l5);
        laptops.add(l6);
        laptops.add(l7);
        laptops.add(l8);
        laptops.add(l9);
        laptops.add(l10);
        laptops.add(l11);
        laptops.add(l12);
        laptops.add(l13);
        laptops.add(l14);
        laptops.add(l15);
        laptops.add(l16);
        laptops.add(l17);
        laptops.add(l18);
        laptops.add(l19);
        laptops.add(l20);
        */ 
    	ProductDAO<Laptop> admin = new PostgreSqlDAO<Laptop>("laptop",new LaptopPostgreSqlFactory());
    	List<Laptop> laptops = admin.getAllProduct();

        EmbeddingService embeddingService = new EmbeddingService();
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

        GPTClient llm = new GPTClient();
        ProductSearchService productSearchService = new ProductSearchService();

        List<Laptop> similarLaptop = null;
        try {
            similarLaptop = productSearchService.searchVector(queryVector, laptops, 10);
        } catch (Exception e) {
        System.err.println("Loi khi tim kiem Laptop: " + e.getMessage());
        }
        
        String answer = llm.askGPT(query, similarLaptop);
        System.out.println(answer);
        scanner.close();
    }
}