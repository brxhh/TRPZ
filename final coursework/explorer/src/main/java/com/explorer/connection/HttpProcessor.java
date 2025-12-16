package com.explorer.connection;

import com.explorer.chain.ErrorHandler;
import com.explorer.chain.RedirectHandler;
import com.explorer.chain.IResponseHandler;

import java.io.IOException;

public class HttpProcessor implements DataFetcher {

    private final HttpConnector httpConnector;
    private final IResponseHandler chainStart;

    public HttpProcessor() {
        this.httpConnector = new HttpConnector();

        RedirectHandler redirectHandler = new RedirectHandler(httpConnector);
        IResponseHandler errorHandler = new ErrorHandler();

        redirectHandler.setNextHandler(errorHandler);

        this.chainStart = redirectHandler;
    }

    public Response loadUrl(String url) throws IllegalArgumentException {
        try {
            Request request = Request.createGet(url);

            Response initialResponse = httpConnector.sendRequest(request);

            return chainStart.handle(initialResponse);

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL provided: " + url + " - " + e.getMessage());
            return Response.badRequest("The provided URL format is invalid.");

        } catch (IOException e) {
            System.err.println("Unexpected fatal load error: " + e.getMessage());
            return Response.internalError("An unexpected system error occurred during page loading.");
        }
    }

    @Override
    public String loadResource(String url) {
        try {
            Request request = Request.createGet(url);
            Response response = httpConnector.sendRequest(request);

            if (response.isSuccessful()) {
                return response.getBody();
            } else {
                System.err.println("Failed to load resource " + url + ". Status: " + response.getStatusCode());
                return "";
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL for resource: " + url);
            return "";
        } catch (java.io.IOException e) {
            System.err.println("Error loading resource " + url + ": " + e.getMessage());
            return "";
        }
    }
}