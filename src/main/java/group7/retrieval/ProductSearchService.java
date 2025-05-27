package group7.retrieval;

import group7.model.*;
import java.util.*;

public class ProductSearchService {
    @SuppressWarnings("unchecked")
    public <T extends Product> List<T> searchVector(double[] queryVector, List<T> products, int k) throws Exception {
        List<ProductWithScore> scoredProducts = new ArrayList<>();
        for (Product product : products) {
            double score = cosineSimilarity(queryVector, product.getVector());
            scoredProducts.add(new ProductWithScore(product, score));
        }

        scoredProducts.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        List<T> results = new ArrayList<>();
        for (int i = 0; i < Math.min(k, scoredProducts.size()); i++) {
            results.add((T) scoredProducts.get(i).getProduct());
        }

        return results;
    }

    private double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }
        return (double) (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }
}