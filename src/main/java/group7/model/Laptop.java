package group7.model;

import java.util.HashMap;
import java.util.Map;

public class Laptop extends Product {
    private String os;              // He dieu hanh
    private String cpu;             // CPU
    private String gpu;             // GPU
    private int ram;                // Ram
    private String diskType;        // Loai o cung
    private int screenRate;         // Tan so quet man hinh
    private int storage;            // Dung luong o cung
    private String resolution;      // Do phan giai man hinh
    private float screenSize;       // Kich thuoc man hinh
    private float weight;           // Trong luong (Can nang)
    private float batteryLife;      // Thoi luong pin
    private String category;        // Loai san pham

    //To string
    @Override
    public String toString() {
        return getName() + " " + getBrand() + " " + getPrice() + " " + getRating() + " " + getOs() + " "
                + getCpu() + " " + getGpu() + " " + getRam() + " " + getDiskType() + " " + getStorage() + " "
                + getResolution() + " " + getScreenSize() + " " + getWeight() + " " + getBatteryLife() + " "
                + getCategory();
    }

    //Map to DataBase
    @Override
    public Map<String,Object> mapToDatabase(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", this.getId());
        attributes.put("laptop_name", this.getName());
        attributes.put("brand", this.getBrand());
        attributes.put("category", this.getCategory());
        attributes.put("os", this.getOs());
        attributes.put("price", this.getPrice());
        attributes.put("rating", this.getRating());
        attributes.put("cpu", this.getCpu());
        attributes.put("gpu", this.getGpu());
        attributes.put("refresh_rate", this.getScreenRate());
        attributes.put("ram", this.getRam());
        attributes.put("disk_size", this.getStorage());
        attributes.put("disk_type", this.getDiskType());
        attributes.put("resolution", this.getResolution());
        attributes.put("screen_size", this.getScreenSize());
        attributes.put("weight", this.getWeight());
        attributes.put("battery", this.getBatteryLife());
        attributes.put("url", this.getUrl());
        return attributes;
    }

    //Lay thong so ky thuat
    public Map<String, String> getSpecification() {
        Map<String, String> specification = new HashMap<String, String>();
        specification.put("os", getOs());
        specification.put("cpu", getCpu());
        specification.put("gpu", getGpu());
        specification.put("ram", String.valueOf(getRam()));
        specification.put("diskType", getDiskType());
        specification.put("screenRate", String.valueOf(getScreenRate()));
        specification.put("diskSize", String.valueOf(getStorage()));
        specification.put("resolution", getResolution());
        specification.put("screenSize", String.valueOf(getScreenSize()));
        specification.put("weight", String.valueOf(getWeight()));
        specification.put("batteryLife", String.valueOf(getBatteryLife()));
        specification.put("category", getCategory());
        return specification;
    }

    //Constructor
    public Laptop() {
        super();
    }

    public Laptop(String id, String name, String brand, String category, String os, int price, float rating, String cpu, String gpu,
                  int screenRate, int ram, int storage, String diskType, String resolution, float screenSize, float weight, float batteryLife, String url) {
        super(id, name, brand, price, rating, url);
        this.category = category;
        this.os = os;
        this.cpu = cpu;
        this.gpu = gpu;
        this.screenRate = screenRate;
        this.ram = ram;
        this.storage = storage;
        this.diskType = diskType;
        this.resolution = resolution;
        this.screenSize = screenSize;
        this.weight = weight;
        this.batteryLife = batteryLife;
    }

    //Getter and Setter
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
    
	public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public int getScreenRate() {
        return screenRate;
    }

    public void setScreenRate(int screenRate) {
        this.screenRate = screenRate;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(float batteryLife) {
        this.batteryLife = batteryLife;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}