package group7.data.storage;

import group7.model.*;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class PostgreSqlDAO<T extends Product> implements ProductDAO<T>{
	private final String url = "jdbc:postgresql://192.168.1.226:5433/products";
    private final String user = "postgres";
    private final String password = "123";
    private String table;
    private ProductFactory<T> productFactory;
    
    
    public PostgreSqlDAO() {
    	
    }
    
    
    public PostgreSqlDAO(String table,ProductFactory<T> productFactory) {
    	this.table=table;
    	this.productFactory=productFactory;
    }
    
    
	@Override
	public List<T> getAllProduct() {
		String sql = "SELECT * FROM "+ "laptop";
		return productFactory.afterQueryProduct(sql, url, user, password);
	}
	
	
	@Override
	public void insertProduct(T product) {
		Map<String, Object> attributes = product.mapToDatabase();
		StringBuilder columns = new StringBuilder();
	    StringBuilder placeholders = new StringBuilder();
	    //int col=0;
	    for (String key : attributes.keySet()) {
	            columns.append(key).append(",");
	            placeholders.append("?,");
	            //col++;
	    }
	    columns.deleteCharAt(columns.length() - 1);
	    placeholders.deleteCharAt(placeholders.length() - 1);
	    String sql = "INSERT INTO "+table+" ("+columns+") VALUES ("+placeholders+")";
	    try (Connection conn = DriverManager.getConnection(url, user, password);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	    	int index = 1;
	        for (Object value : attributes.values()) {
	            if (value instanceof String) {
	                pstmt.setString(index, (String) value);
	            } else if (value instanceof Integer) {
	                pstmt.setInt(index, (Integer) value);
	            } else if (value instanceof Float) {
	                pstmt.setFloat(index, (Float) value);
	            } else if (value instanceof Double) {
	                pstmt.setDouble(index, (Double) value);
	            } else if (value == null) {
	                pstmt.setNull(index, java.sql.Types.NULL);
	            } else {
	                throw new SQLException("Unsupported attribute type: " + value.getClass());
	            }
	            index++;
	        }
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			
	}
	
	
	@Override
	public void removeProduct(T product) {
		String sql = "DELETE FROM "+table+" where id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	
	@Override
	public List<T> searchByBrand(String brand) {
		String sql = "SELECT * FROM "+table+" WHERE brand='"+brand+"'";
		return productFactory.afterQueryProduct(sql, url, user, password);
	}
	
	
	@Override
	public List<T> searchBycategory(String category) {
		String sql = "SELECT * FROM "+table+" WHERE category='"+category+"'";
		return productFactory.afterQueryProduct(sql, url, user, password);
	}
	
	
	@Override
	public List<T> searchByOs(String os) {
		String sql = "SELECT * FROM "+table+" WHERE os='"+os+"'";
		return productFactory.afterQueryProduct(sql, url, user, password);
	}
	
	
	@Override
	public List<T> sortByCostUpToDown() {
		String sql = "SELECT * FROM "+table+" ORDER BY price DESC";
		return productFactory.afterQueryProduct(sql, url, user, password);
	}
	@Override
	public List<T> sortByCostDownToUp() {
		String sql = "SELECT * FROM "+table+" ORDER BY price ASC";
		return productFactory.afterQueryProduct(sql, url, user, password);
	}

}