package group7.search.rag;

import group7.data.storage.*;
import group7.model.Laptop;

import ai.djl.inference.Predictor; // Dùng để tạo vector embedding từ văn bản
import ai.djl.repository.zoo.Criteria; // Dùng để tải mô hình từ HuggingFace hoặc repo khác
import ai.djl.repository.zoo.ZooModel;

import java.util.ArrayList; // Xử lý dữ liệu
import java.util.List;

public class VectorSearchIntegration {
    private final Predictor<String, float[]> textEmbedding;
    private final List<Laptop> laptops;

    // Khai báo biến và constructor
    public VectorSearchIntegration() throws Exception {
        // Tải mô hình bge-m3 để tạo embedding
        Criteria<String, float[]> criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optModelUrls("https://huggingface.co/BAAI/bge-m3")
                .optEngine("PyTorch")
                .build();
        ZooModel<String, float[]> model = criteria.loadModel();
        this.textEmbedding = model.newPredictor();
        LaptopDAO abc = new PostgresLaptopDAO();
        this.laptops = abc.getAllLaptop();
    }

    // Hàm tìm kiếm vector
    public List<Laptop> searchVector(String query, int k) throws Exception {
        float[] queryVector = textEmbedding.predict(query); // Tạo vector từ câu hỏi người dùng (queryVector).
        List<LaptopWithScore> scoredLaptops = new ArrayList<>();
        for (Laptop laptop : laptops) {
            float score = cosineSimilarity(queryVector, laptop.getVector(textEmbedding)); // Tính điểm tương đồng giữa queryVector và vector của laptop.
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

    // Hàm chuyển mảng từ Double → float
    // Giải quyết sự không tương thích giữa Java SQL mảng (Double[]) và kiểu float[] mà DJL dùng.
    //private float[] toFloatArray(Object array) {
    //    Double[] doubleArray = (Double[]) array;
    //    float[] floatArray = new float[doubleArray.length];
    //    for (int i = 0; i < doubleArray.length; i++) {
    //        floatArray[i] = doubleArray[i].floatValue();
    //    }
    //    return floatArray;
    //}

    // Hàm tính cosine similarity
    private float cosineSimilarity(float[] vectorA, float[] vectorB) {
        float dotProduct = 0.0f;
        float normA = 0.0f;
        float normB = 0.0f;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }
        return (float) (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }

    private static class LaptopWithScore {
        Laptop laptop;
        float score;
        LaptopWithScore(Laptop laptop, float score) {
            this.laptop = laptop;
            this.score = score;
        }
    }
}
