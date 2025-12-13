package com.edu.web.restservicewebbrowser.api;

import com.edu.web.restservicewebbrowser.api.dto.ParseRequest;
import com.edu.web.restservicewebbrowser.service.ParsingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/parse-jobs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParseResource {

    @Inject
    private ParsingService parsingService;

    @POST
    public Response createParsingJob(ParseRequest request) {
        if (request == null || request.getUrl() == null || request.getUrl().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"url is required\"}")
                    .build();
        }

        parsingService.parseAndSaveAll(request.getUrl());

        String responseMessage = "{\"status\":\"accepted\", \"message\":\"Parsing job started for " + request.getUrl() + "\"}";

        return Response.status(Response.Status.ACCEPTED)
                .entity(responseMessage)
                .build();
    }
}