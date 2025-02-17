package com.projetarchi.search.Generate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;

public class GeminiApiClient {
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private final String apiKey;
    private final HttpClient httpClient;
    private String Apiresponse;

    public void setApiresponse(String apiresponse) {
        Apiresponse = apiresponse;
    }
    
    public String getApiresponse() {
        return Apiresponse;
    }

    public GeminiApiClient() {
        this.apiKey = System.getenv("GEMINI_API_KEY");
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalStateException("GEMINI_API_KEY environment variable not set");
        }
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    }

    public String generateContent(String prompt) throws IOException, InterruptedException {
        JSONObject requestBody = new JSONObject();
        
        JSONObject systemInstruction = new JSONObject();
        JSONObject systemPart = new JSONObject();
        systemPart.put("text", "You are a helpful expert assistant. I will give you the results of the execution of pattern matching algorithms, and your job is to analyze them, try to comment on every algorithm, and point out the important aspects. Also, determine which algorithm is the best and explain why.\n Note that if the excution is fast thats probably because the text size is small.");
        systemInstruction.put("parts", new JSONArray().put(systemPart));
        requestBody.put("systemInstruction", systemInstruction);

        JSONObject content = new JSONObject();
        JSONObject contentPart = new JSONObject();
        contentPart.put("text", prompt);
        content.put("parts", new JSONArray().put(contentPart));
        requestBody.put("contents", new JSONArray().put(content));

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 0.3);
        generationConfig.put("maxOutputTokens", 400);
        requestBody.put("generationConfig", generationConfig);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "?key=" + apiKey))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return extractTextFromResponse(response.body());
    }

    private String extractTextFromResponse(String jsonResponse) {
        try {
            JSONObject responseObj = new JSONObject(jsonResponse);
            JSONArray candidates = responseObj.getJSONArray("candidates");
            if (candidates.length() > 0) {
                JSONObject firstCandidate = candidates.getJSONObject(0);
                JSONObject content = firstCandidate.getJSONObject("content");
                JSONArray parts = content.getJSONArray("parts");
                if (parts.length() > 0) {
                    return parts.getJSONObject(0).getString("text");
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing API response: " + e.getMessage());
        }
        return "";
    }

    public void processAndGenerateContent(MultiApiRequestData data) {
        try {
            String prompt = "Analyze this performance data: " + data.toString();
            String response = generateContent(prompt);
            setApiresponse(response);
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
