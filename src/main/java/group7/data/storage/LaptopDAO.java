package group7.data.storage;

import group7.model.*;
import java.util.List;

public interface LaptopDAO {
	List<Laptop> getAllLaptop();
	void insertLaptop(Laptop laptop);
	void removeLaptop(Laptop laptop);
}
