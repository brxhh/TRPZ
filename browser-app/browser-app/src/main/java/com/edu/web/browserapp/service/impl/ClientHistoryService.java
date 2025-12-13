package com.edu.web.browserapp.service.impl;

import com.edu.web.browserapp.service.IHistoryService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHistoryService implements IHistoryService {

    private static final ClientHistoryService INSTANCE = new ClientHistoryService();

    private final HttpClient httpClient;
    private final String API_URL = "http://localhost:8080/browser-service-1.0/api/history";

    private final List<String> localHistory;

    private ClientHistoryService() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.localHistory = Collections.synchronizedList(new ArrayList<>());
    }

    public static ClientHistoryService getInstance() {
        return INSTANCE;
    }

    @Override
    public void saveToHistory(String url) {
        if (!localHistory.contains(url)) {
            localHistory.add(url);
            System.out.println("[History] Saved locally for P2P: " + url);
        }

        try {
            String jsonPayload = "{\"url\":\"" + url.replace("\"", "\\\"") + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(this::handleResponse)
                    .exceptionally(this::handleError);

        } catch (Exception e) {
            System.err.println("[Proxy] Error during request: " + e.getMessage());
        }
    }

    public List<String> searchLocalHistory(String query) {
        String lowerQuery = query.toLowerCase();
        synchronized (localHistory) {
            return localHistory.stream()
                    .filter(url -> url.toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList());
        }
    }

    private void handleResponse(HttpResponse<String> response) {
        if (response.statusCode() == 201 || response.statusCode() == 200) {
        } else {
            System.err.println("[Proxy] Server error: " + response.statusCode());
        }
    }

    private Void handleError(Throwable ex) {
        System.err.println("[Proxy] Warning: Central History Server unavailable (" + ex.getMessage() + ")");
        return null;
    }
}