package com.explorer.chain;

import com.explorer.connection.HttpConnector;
import com.explorer.connection.Request;
import com.explorer.connection.Response;

import java.io.IOException;

public class RedirectHandler extends ResponseHandler {
    private final HttpConnector httpConnector;
    private int redirectCount = 0;
    private static final int MAX_REDIRECTS = 5;

    public RedirectHandler(HttpConnector client) {
        this.httpConnector = client;
    }

    @Override
    public Response handle(Response response) {
        if (response.isRedirect()) {

            if (redirectCount >= MAX_REDIRECTS) {
                return Response.internalError("Redirect loop detected. Exceeded " + MAX_REDIRECTS + " redirects.");
            }

            String newLocation = response.getHeader("Location");

            if (newLocation != null) {
                System.out.println("Redirecting to: " + newLocation);
                redirectCount++;
                try {
                    Request newRequest = Request.createGet(newLocation);
                    Response newResponse = httpConnector.sendRequest(newRequest);

                    return handle(newResponse);
                } catch (IOException | IllegalArgumentException e) {
                    System.err.println("Redirect failed due to I/O or invalid URL: " + e.getMessage());
                    return Response.internalError("Error during redirect processing to " + newLocation + ": " + e.getMessage());
                }
            } else {
                System.err.println("Redirect response received without Location header.");
                return Response.internalError("Server sent a redirect code without a Location header.");
            }
        }

        return passToNext(response);
    }
}