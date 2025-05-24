package group7.retrieval;

import group7.config.Configuration;
import group7.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.Gson;

public class EmbeddingService {
    private final Configuration config;

    public EmbeddingService(Configuration config) {
        this.config = config;
    }

    public double[] embedQuery(String query) throws IOException {
        String[] singleInput = new String[]{ query };
        double[][] result = getEmbeddings(singleInput);
        return result.length > 0 ? result[0] : new double[0];
    }

    public double[][] embedProducts(List<? extends Product> products) throws IOException, InterruptedException {
        List<String> texts = new ArrayList<>();
        for (Product p : products) {
            texts.add(p.toString());
        }
        String[] sentences = texts.toArray(new String[0]);
        double[][] embeddings = getEmbeddings(sentences);
        return embeddings;
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

        URL url = new URL(config.getApiUrl()); // Sử dụng config thay vì hard-code
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
        Gson gson = new Gson();
        return gson.fromJson(json, double[][].class);
    }
}
