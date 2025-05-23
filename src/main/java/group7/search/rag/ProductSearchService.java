package group7.search.rag;

import group7.model.*;
import java.util.*;

public class ProductSearchService {
    public List<Laptop> searchVector(double[] queryVector, List<Laptop> laptops, int k) throws Exception {
        List<LaptopWithScore> scoredLaptops = new ArrayList<>();
        for (Laptop laptop : laptops) {
            double score = cosineSimilarity(queryVector, laptop.getVector()); // Tính điểm tương đồng giữa queryVector và vector của laptop.
            scoredLaptops.add(new LaptopWithScore(laptop, score));
        }
        // Sắp xếp và lấy top k
        scoredLaptops.sort((a, b) -> Double.compare(b.score, a.score));
        List<Laptop> results = new ArrayList<>();
        for (int i = 0; i < Math.min(k, scoredLaptops.size()); i++) {
            results.add(scoredLaptops.get(i).laptop);
        }
        return results;
    }

    private double cosineSimilarity(double[] vectorA, double[] vectorB) {
        float dotProduct = 0.0f;
        float normA = 0.0f;
        float normB = 0.0f;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }
        return (double) (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }
}