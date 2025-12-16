package com.explorer.connection;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private final int statusCode;
    private final String statusText;
    private final Map<String, String> headers;
    private final String body;
    private final HttpStatus statusClass;

    private Response(int statusCode, String statusText, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.headers = headers != null ? headers : new HashMap<>();
        this.body = body != null ? body : "";
        this.statusClass = HttpStatus.getByCode(statusCode);
    }

    public static Response create(int statusCode, String statusText, Map<String, String> headers, String body) {
        return new Response(statusCode, statusText, headers, body);
    }

    public static Response notFound(String requestedPath) {
        String body = String.format(
                "<html><body><h1>404 Not Found</h1><p>The requested URL <strong>%s</strong> was not found on this server.</p></body></html>",
                requestedPath
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=utf-8");
        return new Response(404, "Not Found", headers, body);
    }

    public static Response serviceUnavailable(String reason) {
        String body = String.format(
                "<html><body><h1>503 Service Unavailable</h1><p>The server is temporarily unable to service your request: <strong>%s</strong></p></body></html>",
                reason
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=utf-8");
        return new Response(503, "Service Unavailable", headers, body);
    }

    public static Response internalError(String errorDetail) {
        String body = String.format(
                "<html><body><h1>500 Internal Server Error</h1><p>An unexpected error occurred: <strong>%s</strong></p></body></html>",
                errorDetail
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=utf-8");
        return new Response(500, "Internal Server Error", headers, body);
    }

    public static Response badRequest(String reason) {
        String body = String.format(
                "<html><body><h1>400 Bad Request</h1><p>The request could not be understood by the server due to malformed syntax: <strong>%s</strong></p></body></html>",
                reason
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=utf-8");
        return new Response(400, "Bad Request", headers, body);
    }


    public int getStatusCode() { return statusCode; }
    public String getStatusText() { return this.statusText; }

    public String getHeader(String name) {
        return headers.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public String getBody() { return body; }


    public boolean isSuccessful() { return statusClass == HttpStatus.SUCCESS; }
    public boolean isRedirect() { return statusClass == HttpStatus.REDIRECTION; }
    public boolean isClientError() { return statusClass == HttpStatus.CLIENT_ERROR; }
    public boolean isServerError() { return statusClass == HttpStatus.SERVER_ERROR; }

    public boolean isNotFound() { return statusCode == 404; }
    public boolean isServiceUnavailable() { return statusCode == 503; }
    public boolean isBadRequest() { return statusCode == 400; }
    public boolean isBadGateway() { return statusCode == 502; }

    @Override
    public String toString() {
        return "HTTP Status: " + statusCode + " " + statusText +
                " (" + statusClass + ")" + "\n" +
                "Headers: " + headers.size() + "\n" +
                "Body size: " + body.length();
    }


    public enum HttpStatus {
        SUCCESS(200, 299),
        REDIRECTION(300, 399),
        CLIENT_ERROR(400, 499),
        SERVER_ERROR(500, 599),
        INFORMATIONAL(100, 199),
        UNKNOWN(-1, -1);

        private final int min;
        private final int max;

        HttpStatus(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public static HttpStatus getByCode(int code) {
            if (code >= SUCCESS.min && code <= SUCCESS.max) return SUCCESS;
            if (code >= REDIRECTION.min && code <= REDIRECTION.max) return REDIRECTION;
            if (code >= CLIENT_ERROR.min && code <= CLIENT_ERROR.max) return CLIENT_ERROR;
            if (code >= SERVER_ERROR.min && code <= SERVER_ERROR.max) return SERVER_ERROR;
            if (code >= INFORMATIONAL.min && code <= INFORMATIONAL.max) return INFORMATIONAL;
            return UNKNOWN;
        }
    }
}