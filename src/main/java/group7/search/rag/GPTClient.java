package group7.search.rag;

import group7.model.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GPTClient {
    private static final String API_KEY = "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";
    private static final String ENDPOINT = "https://openrouter.ai/api/v1/chat/completions";

    public String askGPT(String userQuery, List<? extends Product> products) {
        try {
            StringBuilder productListBuilder = new StringBuilder();
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                productListBuilder.append(p.toString()).append("\n\n");
            }

            String prompt = "User has the following question: \"" + userQuery + "\"\n"
                + "Below is a list of suggested products:\n"
                + productListBuilder.toString()
                + "Based on the information of the above products, give advice to users on which 5 products are most suitable and explain why.";

            JSONObject message1 = new JSONObject()
                .put("role", "system")
                .put("content", "You are a useful product consultant.");

            JSONObject message2 = new JSONObject()
                .put("role", "user")
                .put("content", prompt);

            JSONArray messages = new JSONArray()
                .put(message1)
                .put(message2);

            JSONObject jsonBody = new JSONObject()
                .put("model", "mistralai/devstral-small:free")
                .put("messages", messages)
                .put("temperature", 0.7);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            String answer = message.getString("content");

            return answer;  // Trả về chỉ phần văn bản trả lời GPT
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi gọi GPT API";
        }
    }
}
