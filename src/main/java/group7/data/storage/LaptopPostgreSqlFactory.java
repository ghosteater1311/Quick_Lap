package group7.data.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import group7.model.Laptop;

public class LaptopPostgreSqlFactory implements ProductFactory<Laptop> {

	@Override
	public Laptop createProduct(ResultSet rs) throws SQLException {
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
		return laptop;
	}

	@Override
	public List<Laptop> afterQueryProduct(String query, String url, String user, String password) {
		List<Laptop> products = new ArrayList<Laptop>();

        try (Connection conn = DriverManager.getConnection(url, user, password); //tạo ra 1 kết nối đến database cho phép thực hiện các query
             Statement stmt = conn.createStatement();                            //cho phép thực hiện các câu query đơn giản không có tham số
             ResultSet rs = stmt.executeQuery(query)) {   // trả về kết quả của câu truy vấn có dạng như là 1 bảng ảo

            while (rs.next()) {
                Laptop product = this.createProduct(rs);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
	}

}
