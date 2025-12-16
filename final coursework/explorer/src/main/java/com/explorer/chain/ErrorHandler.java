package com.explorer.chain;

import com.explorer.connection.Response;

public class ErrorHandler extends ResponseHandler {

    @Override
    public Response handle(Response response) {
        if (response.isClientError() || response.isServerError()) {

            if (response.getBody() != null && !response.getBody().isEmpty()) {
                return response;
            }


            final int statusCode = response.getStatusCode();
            final String statusText = response.getStatusText();

            if (response.isNotFound()) {
                return Response.notFound("Resource not specified or found.");
            } else if (response.isBadRequest()) {
                return Response.badRequest(statusText);
            } else if (response.isServiceUnavailable() || response.isBadGateway()) {
                return Response.serviceUnavailable(statusText);
            } else if (response.isServerError()) {
                return Response.internalError(statusText);
            } else if (response.isClientError()) {
                String reason = String.format("%d %s: Access Denied or Malformed Request.", statusCode, statusText);
                return Response.create(
                        statusCode,
                        statusText,
                        null,
                        String.format("<html><body><h1>%d %s</h1><p>The server responded with an error.</p></body></html>", statusCode, statusText)
                );
            }
            return response;
        }

        return passToNext(response);
    }
}