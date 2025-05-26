package group7.data.collector;

import org.openqa.selenium.By; // Xác định các phần tử trên trang web (ví dụ: bằng CSS selector, ID, hoặc class).
import org.openqa.selenium.JavascriptExecutor; // Thực thi mã JavaScript trên trình duyệt (dùng để cuộn trang hoặc thực hiện các hành động phức tạp).
import org.openqa.selenium.WebDriver; // Giao diện chính để điều khiển trình duyệt.
import org.openqa.selenium.WebElement; // Đại diện cho một phần tử HTML trên trang web.
import org.openqa.selenium.chrome.ChromeDriver; // Lớp cụ thể để điều khiển trình duyệt Chrome.
import org.openqa.selenium.chrome.ChromeOptions; // Cấu hình các tùy chọn cho trình duyệt Chrome (ví dụ: chạy ở chế độ không giao diện - headless).
import io.github.bonigarcia.wdm.WebDriverManager; // Tự động quản lý và tải ChromeDriver phù hợp với phiên bản trình duyệt Chrome
import group7.model.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.Duration;

public class DataCollector {
    // Khai báo một Map tĩnh (không thay đổi) để lưu trữ các cặp key-value, trong đó:
    // Key là tên thương hiệu laptop (ví dụ: "GAMING", "MACBOOK").
    // Value là URL của trang web chứa danh sách laptop của thương hiệu đó.
    // Sử dụng LinkedHashMap để duy trì thứ tự chèn của các mục.
    private static final Map<String, String> BRAND_URLS = new LinkedHashMap<>();
    static {
    	BRAND_URLS.put("GAMING", "https://www.thegioididong.com/laptop?g=laptop-gaming#c=44&p=37699&o=13&pi=4");
        BRAND_URLS.put("AI", "https://www.thegioididong.com/laptop-ai#c=44&p=294769&o=13&pi=5");
        BRAND_URLS.put("MACBOOK", "https://www.thegioididong.com/laptop-apple-macbook#c=44&m=203&o=13&pi=1");
        BRAND_URLS.put("HP", "https://www.thegioididong.com/laptop-hp-compaq#c=44&m=122&o=13&pi=2");
        BRAND_URLS.put("ASUS", "https://www.thegioididong.com/laptop-asus#c=44&m=128&o=13&pi=3");
        BRAND_URLS.put("DELL", "https://www.thegioididong.com/laptop-dell#c=44&m=118&o=13&pi=2");
        BRAND_URLS.put("ACER", "https://www.thegioididong.com/laptop-acer#c=44&m=119&o=13&pi=2");
        BRAND_URLS.put("LENOVO", "https://www.thegioididong.com/laptop-lenovo#c=44&m=120&o=13&pi=3");
        BRAND_URLS.put("MSI", "https://www.thegioididong.com/laptop-msi#c=44&m=133&o=13&pi=1");
    }

    // Khai báo phương thức chính collectStructuredData, trả về một danh sách (List) các đối tượng Laptop chứa dữ liệu thu thập được.
    public List<Laptop> collectStructuredData() {
        List<Laptop> laptops = new ArrayList<>(); // Tạo một danh sách rỗng (ArrayList) để lưu trữ các đối tượng Laptop.
        Set<String> laptopIds = new HashSet<>(); // Tạo một HashSet để lưu trữ các ID của laptop, dùng để kiểm tra và loại bỏ các laptop trùng lặp.
        int totalLaptops = 0; // Biến đếm số lượng laptop đã thu thập.
        final int MAX_LAPTOPS = 360;

       // Cấu hình ChromeDriver bằng WebDriverManager
        WebDriverManager.chromedriver().setup(); // Tự động tải và cấu hình ChromeDriver phù hợp với phiên bản trình duyệt Chrome trên máy
        ChromeOptions options = new ChromeOptions(); // Khởi tạo ChromeOptions để tùy chỉnh các thiết lập cho trình duyệt Chrome
        options.addArguments("--headless"); // Chạy Chrome ở chế độ không giao diện (không hiển thị cửa sổ trình duyệt).
        options.addArguments("--disable-gpu"); // Tắt tăng tốc GPU (thường dùng khi chạy headless để tránh lỗi).
        options.addArguments("--window-size=1920,1080"); // Đặt kích thước cửa sổ trình duyệt là 1920x1080 pixel.
        WebDriver driver = new ChromeDriver(options); // Khởi tạo đối tượng ChromeDriver với các tùy chọn đã cấu hình, dùng để điều khiển trình duyệt.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1)); // Đặt thời gian chờ ngầm (implicit wait) là 1 giây. Selenium sẽ chờ tối đa 1 giây để tìm thấy một phần tử trước khi báo lỗi.

        // Duyệt qua từng cặp key-value trong BRAND_URLS (thương hiệu và URL tương ứng).
        for (Map.Entry<String, String> entry : BRAND_URLS.entrySet()) {
            String brand = entry.getKey(); // Lấy tên thương hiệu (brand) và URL (url) từ mỗi mục trong BRAND_URLS.
            String url = entry.getValue(); // Lấy tên thương hiệu (brand) và URL (url) từ mỗi mục trong BRAND_URLS.
            try {
                driver.get(url); // Mở URL của thương hiệu trong trình duyệt Chrome.
                System.out.println("Đã kết nối tới danh sách " + brand + ": " + url);  // In thông báo xác nhận đã kết nối tới danh sách laptop của thương hiệu.              
                loadAllProducts(driver); // Gọi phương thức loadAllProducts để tải toàn bộ sản phẩm trên trang. 
                List<WebElement> productItems = driver.findElements(By.cssSelector("li.item")); // Tìm tất cả các phần tử HTML trên trang có thẻ <li> và class item (mỗi phần tử đại diện cho một sản phẩm laptop).
                System.out.println("Số sản phẩm tìm thấy: " + productItems.size()); // In số lượng sản phẩm tìm thấy trên trang.

                // Nếu không tìm thấy sản phẩm nào (danh sách rỗng), in thông báo lỗi và chuyển sang thương hiệu tiếp theo (continue).
                if (productItems.isEmpty()) {
                    System.out.println("Không tìm thấy sản phẩm. Kiểm tra selector hoặc HTML động.");
                    continue;
                }

                // Duyệt qua từng phần tử sản phẩm (item) trong danh sách productItems.
                // Nếu số lượng laptop đã thu thập đạt đến giới hạn MAX_LAPTOPS (360), thoát khỏi vòng lặp.
                for (WebElement item : productItems) {
                    if (totalLaptops >= MAX_LAPTOPS) break;

                    try {
                        long startTime = System.currentTimeMillis(); // Đo thời gian xử lý một sản phẩm bằng cách lưu thời điểm hiện tại (tính bằng
                        System.out.println("HTML" + item.getAttribute("outerHTML")); // In toàn bộ mã HTML của phần tử sản phẩm để kiểm tra hoặc gỡ lỗi.
                        Laptop laptop = scrapeLaptopFromMainPage(item, brand); // Gọi phương thức scrapeLaptopFromMainPage để trích xuất thông tin laptop từ phần tử HTML và thương hiệu. 
                        if (laptop != null && isValidLaptop(laptop) && !isDuplicateLaptop(laptop, laptopIds)) {
                            laptops.add(laptop);
                            laptopIds.add(laptop.getId()); // Thêm id vào Set để kiểm tra trùng lặp
                            totalLaptops++;
                            System.out.println("Đã thêm laptop: " + laptop.getName() + " (Tổng: " + totalLaptops + ")");
                        } else {
                            System.out.println("Bỏ qua laptop do lỗi hoặc trùng lặp: " + (laptop != null ? laptop.getId() : "null"));
                        }
                        long endTime = System.currentTimeMillis(); // Ghi lại thời điểm kết thúc xử lý sản phẩm. In thời gian xử lý sản phẩm (tính bằng mili-giây) để theo dõi hiệu suất.
                        System.out.println("Thời gian xử lý sản phẩm: " + (endTime - startTime) + "ms");
                    } catch (Exception e) {
                        System.err.println("Lỗi khi xử lý sản phẩm " + brand + ": " + e.getMessage()); // Bắt và xử lý ngoại lệ nếu có lỗi khi xử lý một sản phẩm (ví dụ: lỗi truy cập phần tử HTML). In thông báo lỗi và tiếp tục với sản phẩm tiếp theo.
                    }
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi kết nối tới " + url + ": " + e.getMessage()); // Bắt và xử lý ngoại lệ nếu có lỗi khi kết nối tới URL của thương hiệu (ví dụ: lỗi mạng, trang web không phản hồi). In thông báo lỗi và tiếp tục với thương hiệu tiếp theo.
            }
        }
        driver.quit();
        saveLaptopsToCsv(laptops);
        System.out.println("Tổng số laptop thu thập được: " + totalLaptops);
        return laptops;
    }

    // Phương thức tải tất cả sản phẩm trên trang bằng cách nhấp nút "Xem thêm" và cuộn trang.
    private void loadAllProducts(WebDriver driver) {
        // Kiểm tra và nhấn nút "Xem thêm" nếu có
        while (true) {
            try {
                WebElement seeMoreButton = driver.findElement(By.cssSelector("a[href*='javascript:void(0)'][data-action='load-more']")); // Tìm nút "Xem thêm" bằng CSS selector.
                if (seeMoreButton.isDisplayed()) {
                    seeMoreButton.click(); // Nhấp và chờ 2 giây để tải thêm.
                    try {
                        Thread.sleep(2000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                // Không tìm thấy nút "Xem thêm" hoặc đã tải hết
                break;
            }
        }

        // Cuộn trang để đảm bảo tất cả sản phẩm được tải (trong trường hợp cuộn tự động)
        long lastHeight = -1;
        while (true) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long newHeight = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break; // Kiểm tra chiều cao trang mới; nếu không thay đổi, thoát vòng lặp.
            }
            lastHeight = newHeight;
        }
    }

    private boolean isValidLaptop(Laptop laptop) {
        if (laptop == null || laptop.getName().equals("Unknown")) {
            return false;
        }
        return true;
    }

    private boolean isDuplicateLaptop(Laptop laptop, Set<String> laptopIds) {
        return laptopIds.contains(laptop.getId()); // Kiểm tra trùng lặp theo id
    }

    // Trích xuất thông tin laptop từ phần tử HTML.
    private Laptop scrapeLaptopFromMainPage(WebElement item, String brand) {
        // Lấy tất cả các phần tử cần thiết 
        String name = "Unknown";
        String priceText = "0";
        String ratingText = "0.0";
        String imageUrl = "";
        List<WebElement> compareElements = item.findElements(By.cssSelector(".item-compare.gray-bg span"));
        List<WebElement> detailElements = item.findElements(By.cssSelector(".utility p"));

        // Lấy tên laptop từ thẻ <h3> (thuộc tính title)
        WebElement h3Element = item.findElements(By.cssSelector("h3")).isEmpty() ? null : item.findElement(By.cssSelector("h3"));
        if (h3Element != null) {
            name = cleanText(h3Element.getAttribute("title"));  // Làm sạch bằng cleanText.
        }
        System.out.println("Tên laptop: " + name);

        // Lấy id từ phần trong ngoặc () của name
        String id = extractIdFromName(name, brand);       
        
        // Lấy category
        String category = "Văn phòng";
        if (brand.equals("GAMING")) {
            category = "Gaming";
        } else if (brand.equals("AI")) {
            category = "AI";
        }        

        String os = brand.equals("MACBOOK") ? "macOS" : "Windows 11";

        // Lấy price
        WebElement priceElement = item.findElements(By.cssSelector(".price")).isEmpty() ? null : item.findElement(By.cssSelector(".price"));
        if (priceElement != null) {
            priceText = priceElement.getText();
        }
        int price = parsePrice(priceText);    

        // Lấy rating
        WebElement ratingElement = item.findElements(By.cssSelector("i.iconnewglobal-vote + b")).isEmpty() ? null : item.findElement(By.cssSelector("i.iconnewglobal-vote + b"));
        if (ratingElement != null) {
            ratingText = ratingElement.getText();
        }
        float rating = parseFloat(ratingText);
        System.out.println("Rating: " + rating);

        // Lấy URL image
        WebElement imgElement = item.findElements(By.cssSelector("img")).isEmpty() ? null : item.findElement(By.cssSelector("img"));
        if (imgElement != null) {
            imageUrl = imgElement.getAttribute("src");
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = imgElement.getAttribute("data-src"); 
            }
        }
        System.out.println("URL hình ảnh: " + imageUrl);

        // Mặc định cho các trường
        String cpu = "Unknown";
        String gpu = brand.equals("MACBOOK") ? "Apple GPU" : "Integrated";
        String resolution = "FHD";
        float screenSize = 15.6f;
        int screenRate = 60;
        int ram = 0;
        int storage = 0;
        String diskType = "SSD";
        float weight = 1.5f;
        float batteryLife = 3.0f;

        // Lấy RAM và Storage từ .item-compare.gray-bg span
        System.out.println("Số phần tử trong .item-compare.gray-bg: " + compareElements.size());
        for (WebElement compare : compareElements) {
            String compareText = cleanText(compare.getText()).trim();
            System.out.println("Dòng compare: " + compareText);
            if (compareText.startsWith("RAM")) {
                ram = parseInt(compareText.replace("RAM", "").replace("GB", "").trim());
                System.out.println("Đã tìm thấy RAM: " + ram + " GB");
            } else if (compareText.contains("SSD") || compareText.contains("HDD")) {
                if (compareText.contains("SSD")) {
                    diskType = "SSD";
                    storage = parseInt(compareText.replace("SSD", "").replace("GB", "").replace("TB", "").trim());
                    if (compareText.contains("TB")) {
                        storage *= 1000; // Chuyển TB thành GB
                    }
                } else if (compareText.contains("HDD")) {
                    diskType = "HDD";
                    storage = parseInt(compareText.replace("HDD", "").replace("GB", "").replace("TB", "").trim());
                    if (compareText.contains("TB")) {
                        storage *= 1000; // Chuyển TB thành GB
                    }
                }
                System.out.println("Đã tìm thấy Storage: " + storage + " GB, Type: " + diskType);
            }
        }

        // Lấy thông tin chi tiết từ các thẻ <p> trong .utility
        System.out.println("Số thẻ <p> trong .utility: " + detailElements.size());
        if (!detailElements.isEmpty()) {
            System.out.println("HTML thô của các thẻ <p> trong .utility: " + detailElements.get(0).getAttribute("outerHTML"));

            for (WebElement detail : detailElements) {
                String detailText = cleanText(detail.getText()).trim();
                System.out.println("Dòng chi tiết: " + detailText);

                if (detailText.startsWith("Màn hình:")) {
                    String screenInfo = detailText.replace("Màn hình:", "").trim();
                    String[] parts = screenInfo.split(",");
                    if (parts.length > 0) {
                        String sizePart = parts[0].trim().replace("\"", "");
                        screenSize = parseFloat(sizePart);
                        System.out.println("Đã tìm thấy Screen Size: " + screenSize + " inch");
                    }
                    if (parts.length > 1) {
                        String resPart = parts[1].trim();
                        resolution = resPart.contains("(") ? resPart.substring(0, resPart.indexOf("(")).trim() : resPart;
                        System.out.println("Đã tìm thấy Resolution: " + resolution);
                    }
                    for (String part : parts) {
                        if (part.contains("Hz")) {
                            screenRate = parseInt(part.replace("Hz", "").trim());
                            System.out.println("Đã tìm thấy Screen Rate: " + screenRate + " Hz");
                            break;
                        }
                    }
                } else if (detailText.startsWith("CPU:")) {
                    String cpuFull = detailText.replace("CPU:", "").trim();
                    cpu = cpuFull.replace(",", "").replaceAll("\\s+", " ");
                    System.out.println("Đã tìm thấy CPU: " + cpu);
                } else if (detailText.startsWith("Card:")) {
                    gpu = detailText.replace("Card:", "").trim();
                    System.out.println("Đã tìm thấy GPU: " + gpu);
                } else if (detailText.startsWith("Khối lượng:")) {
                    weight = parseFloat(detailText.replace("Khối lượng:", "").replace("kg", "").trim());
                    System.out.println("Đã tìm thấy Weight: " + weight + " kg");
                }
            }
        } else {
            System.out.println("Không tìm thấy thẻ <p> chứa thông tin chi tiết trong .utility cho laptop: " + name);
        }

        // Lấy brand trong trường hợp Gaming hoặc AI
        String actualBrand = (brand.equals("GAMING") || brand.equals("AI")) ? extractBrandFromName(name) : brand;
        System.out.println("Actual Brand: " + actualBrand);

        return new Laptop(id, name, actualBrand, category, os, price, rating, cpu, gpu, screenRate, ram, storage,
                         diskType, resolution, screenSize, weight, batteryLife, imageUrl);
    }
    private String extractIdFromName(String name, String brand) {
        // Lấy id từ phần trong ngoặc () của name
        int start = name.lastIndexOf('(');
        int end = name.lastIndexOf(')');
        if (start != -1 && end != -1 && start < end) {
            return name.substring(start + 1, end).trim();
        }
        // Nếu không có phần trong ngoặc, dùng logic mặc định
        return brand + "-" + System.currentTimeMillis();
    }

    private String extractBrandFromName(String name) {
        // Tìm brand từ tên sản phẩm 
        String[] knownBrands = {"HP", "ASUS", "DELL", "ACER", "LENOVO", "MSI", "MACBOOK"};
        for (String brand : knownBrands) {
            if (name.toUpperCase().contains(brand)) {
                return brand;
            }
        }
        return "Unknown";
    }

    private void saveLaptopsToCsv(List<Laptop> laptops) {
        Path filePath = Paths.get("archive", "laptops.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write('\uFEFF'); // Thêm BOM để hỗ trợ UTF-8
            writer.write("id,name,brand,category,os,price,rating,cpu,gpu,screenRate,ram,storage,diskType,resolution,screenSize,weight,batteryLife,image");
            writer.newLine();

            for (Laptop laptop : laptops) {
                String storageDisplay = (laptop.getStorage() == 1) ? "1TB" : (laptop.getStorage() + "GB");
                //System.out.println("GPU trước khi ghi: " + laptop.getGpu()); // Log để kiểm tra giá trị GPU
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        escapeCsv(laptop.getId()),
                        escapeCsv(laptop.getName()),
                        escapeCsv(laptop.getBrand()),
                        escapeCsv(laptop.getCategory()),
                        escapeCsv(laptop.getOs()),
                        escapeCsv(String.valueOf(laptop.getPrice())),
                        escapeCsv(String.format("%.1f", laptop.getRating())),
                        escapeCsv(laptop.getCpu()),
                        escapeCsv(laptop.getGpu()),
                        escapeCsv(String.valueOf(laptop.getScreenRate())),
                        escapeCsv(String.valueOf(laptop.getRam())),
                        escapeCsv(storageDisplay),
                        escapeCsv(laptop.getDiskType()),
                        escapeCsv(laptop.getResolution()),
                        escapeCsv(String.format("%.1f", laptop.getScreenSize())),
                        escapeCsv(String.format("%.1f", laptop.getWeight())),
                        escapeCsv(String.format("%.1f", laptop.getBatteryLife())),
                        escapeCsv(laptop.getUrl() != null ? laptop.getUrl() : ""));
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Đã lưu dữ liệu laptop vào laptops.csv");
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu laptops.csv: " + e.getMessage());
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "\"\"";
        value = value.replace("\n", " ").replace("\r", " ");
        value = value.replace("\"", "\"\"");
        return "\"" + value + "\""; 
    }

    private String cleanText(String text) {
        if (text == null) return "";
        return text.replaceAll("[\\n\\r\\t]+", " ")
                   .replaceAll(" ", " ")
                   .replaceAll("&[a-zA-Z0-9#]+;", " ")
                   .replaceAll("\\s+", " ")
                   .trim();
    }

    private int parsePrice(String priceText) {
        try {
            return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseInt(String text) {
        try {
            String number = text.replaceAll("[^0-9]", "");
            return number.isEmpty() ? 0 : Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private float parseFloat(String text) {
        try {
            String number = text.replaceAll("[^0-9.]", "");
            return number.isEmpty() ? 0.0f : Float.parseFloat(number);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }
}