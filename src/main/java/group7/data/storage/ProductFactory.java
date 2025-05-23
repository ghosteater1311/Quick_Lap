package group7.data.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ProductFactory<T> {
	T createProduct(ResultSet rs) throws SQLException;
	List<T> afterQueryProduct(String query, String url, String user, String password);
}
