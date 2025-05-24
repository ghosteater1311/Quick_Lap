package group7.llm;

import java.util.List;
import group7.model.Product;

public interface AIClient {
    String getResponse(String userQuery, List<? extends Product> products); // throw exception
}