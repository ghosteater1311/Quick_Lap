package group7.data.storage;

import group7.model.*;
import java.util.List;

public interface ProductDAO<T extends Product> {
	List<T> getAllProduct();
	void insertProduct(T t);
	void removeProduct(T t);
	List<T> searchByBrand(String brand);
	List<T> searchBycategory(String category);
	List<T> searchByOs(String os);
	List<T> sortByCostUpToDown();
	List<T> sortByCostDownToUp();
}
