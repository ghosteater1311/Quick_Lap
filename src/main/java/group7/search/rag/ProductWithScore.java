package group7.search.rag;

import group7.model.*;

public class ProductWithScore {
    private final Product product;
    private double score;

    public Product getProduct(){
        return product;
    }

    public double getScore(){
        return score;
    }

    public ProductWithScore(Product product, double score) {
        this.product = product;
        this.score = score;
    }
}
