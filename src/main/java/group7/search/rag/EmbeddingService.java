package group7.search.rag;

import group7.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EmbeddingService {
    private static final String API_URL = "http://localhost:5000/embed";

    public double[] embedQuery(String query) throws IOException {
        String[] singleInput = new String[]{ query };
        double[][] result = getEmbeddings(singleInput);
        return result.length > 0 ? result[0] : new double[0]; // Trả về vector đầu tiên (và duy nhất)
    }

    public double[][] embedProducts(List<Laptop> laptops) throws IOException, InterruptedException {
        List<String> texts = new ArrayList<>();
        for (Laptop l : laptops) {
            texts.add(l.toString());
        }
        String[] sentences = texts.toArray(new String[0]);
        double[][] embeddings = getEmbeddings(sentences);
        return  embeddings;
    }

    public double[][] getEmbeddings(String[] sentences) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"sentences\": [");
        for (int i = 0; i < sentences.length; i++) {
            jsonBuilder.append("\"").append(sentences[i].replace("\"", "\\\"")).append("\"");
            if (i < sentences.length - 1) jsonBuilder.append(",");
        }
        jsonBuilder.append("]}");
        String jsonInputString = jsonBuilder.toString();

        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        con.disconnect();

        return parseJsonArray2D(response.toString());
    }

    public double[][] parseJsonArray2D(String json) {
        json = json.trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1); 
        }

        String[] arrays = json.split("\\],\\[");
        double[][] result = new double[arrays.length][];

        for (int i = 0; i < arrays.length; i++) {
            String arr = arrays[i]
                    .replace("[", "")
                    .replace("]", "")
                    .trim();
            if (arr.isEmpty()) {
                result[i] = new double[0];
                continue;
            }
            String[] parts = arr.split(",");
            double[] vec = new double[parts.length];
            for (int j = 0; j < parts.length; j++) {
                vec[j] = Double.parseDouble(parts[j].trim());
            }
            result[i] = vec;
        }
        return result;
    }
}
