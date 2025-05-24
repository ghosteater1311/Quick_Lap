package group7.model;

import java.util.Map;

public abstract class Product {
    private String id;          // Ma san pham
    private String name;        // Ten san pham
    private String brand;       // Hang san pham
    private int price;          // Gia tien
    private float rating;       // Thang diem danh gia
    private String url;         // Lien ket hinh anh
    private double[] vector;    // Vector embedding

    //Map to DataBase
    public abstract Map<String,Object> mapToDatabase();

    // Hien thi thong tin co ban cua san pham
    @Override
    public abstract String toString();

    // Constructor
    public Product() {

    }

    public Product(String id, String name, String brand, int price, float rating, String url) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.rating = rating;
        this.url = url;
    }

    //Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }
}