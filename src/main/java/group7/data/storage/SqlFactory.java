package group7.data.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class SqlFactory<T> implements ProductFactory<T> {
	public abstract T createProduct(ResultSet rs) throws SQLException;

    @Override
    public List<T> afterQueryProduct(String query, String url, String user, String password) {
        List<T> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                T product = createProduct(rs);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}