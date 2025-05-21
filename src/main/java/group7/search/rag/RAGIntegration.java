package group7.search.rag;

import group7.model.*;

import java.util.List;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RAGIntegration {
    private final VectorSearchIntegration vectorSearch;
    private final String llmApiUrl = "https://api-inference.huggingface.co/models/meta-llama/Llama-2-7b-chat-hf";
    private final String apiKey = "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";

    public RAGIntegration() throws Exception {
        this.vectorSearch = new VectorSearchIntegration();
    }

    public String retrieveAndGenerate(String query) throws Exception {
        // Tìm kiếm top 5 laptop
        List<Laptop> laptops = vectorSearch.searchVector(query, 5);
        StringBuilder contextBuilder = new StringBuilder();
        for (Laptop laptop : laptops) {
            contextBuilder.append(laptop.toString()).append("\n\n");
        }
        String fullContext = contextBuilder.toString();

        // Gửi prompt đến LLM
        String prompt = String.format("""
            Dựa trên câu hỏi của người dùng và thông tin sau, hãy đưa ra gợi ý laptop phù hợp nhất kèm lý do:
            Query: %s
            Context: %s
            Trả lời ngắn gọn, tự nhiên, và nhấn mạnh các đặc điểm phù hợp với nhu cầu người dùng.
            """, query, fullContext);

        HttpClient client = HttpClient.newHttpClient();
        String jsonPayload = new ObjectMapper().writeValueAsString(new RequestPayload(prompt));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(llmApiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body(); // Xử lý phản hồi từ LLM
    }

    private static class RequestPayload {
        String inputs;
        RequestPayload(String inputs) {
            this.inputs = inputs;
        }
    }
}