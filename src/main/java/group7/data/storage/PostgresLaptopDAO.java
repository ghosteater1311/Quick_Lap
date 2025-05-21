package group7.data.storage;

import group7.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresLaptopDAO implements LaptopDAO {
	private final String url = "jdbc:postgresql://192.168.1.226:5433/laptop";
    private final String user = "postgres";
    private final String password = "123";
	@Override
	public List<Laptop> getAllLaptop() {
		List<Laptop> laptops = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password); //tạo ra 1 kết nối đến database cho phép thực hiện các query
             Statement stmt = conn.createStatement();                            //cho phép thực hiện các câu query đơn giản không có tham số
             ResultSet rs = stmt.executeQuery("SELECT * FROM information")) {    // trả về kết quả của câu truy vấn có dạng như là 1 bảng ảo

            while (rs.next()) {
                Laptop laptop = new Laptop(
                		rs.getString("id"),
                        rs.getString("laptop_name"),
                        rs.getString("brand"),
                        rs.getString("category"),
                        rs.getString("os"),
                        rs.getInt("price"),
                        rs.getFloat("rating"),
                        rs.getString("cpu"),
                        rs.getString("gpu"),
                        rs.getInt("refresh_rate"),
                        rs.getInt("ram"),
                        rs.getInt("disk_size"),
                        rs.getString("disk_type"),
                        rs.getString("resolution"),
                        rs.getFloat("screen_size"),
                        rs.getFloat("weight"),
                        rs.getFloat("battery"),
                        rs.getString("url")
                );
                laptops.add(laptop);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return laptops;
	}

	@Override
	public void insertLaptop(Laptop laptop) {
		String sql = "INSERT INTO information (id,laptop_name,brand,price,rating,os,cpu,gpu,ram,disk_type,disk_size,resolution,screen_size,weight,battery,category ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, laptop.getId());
            pstmt.setString(2, laptop.getName());
            pstmt.setString(3, laptop.getBrand());
            pstmt.setInt(4,laptop.getPrice());
            pstmt.setFloat(5, (float)(laptop.getRating()));
            pstmt.setString(6, laptop.getOs());
            pstmt.setString(7, laptop.getCpu());
            pstmt.setString(8, laptop.getGpu());
            pstmt.setInt(9, laptop.getRam());
            pstmt.setString(10, laptop.getDiskType());
            pstmt.setInt(11, laptop.getStorage());
            pstmt.setString(12, laptop.getResolution());
            pstmt.setFloat(13, laptop.getScreenSize());
            pstmt.setFloat(14, laptop.getWeight());
            pstmt.setFloat(15, laptop.getBatteryLife());
            pstmt.setString(16, laptop.getCategory());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void removeLaptop(Laptop laptop) {
		String sql = "DELETE FROM information where laptop_name=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, laptop.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

}
