package com.edu.web.restservicewebbrowser.api;

import com.edu.web.restservicewebbrowser.domain.HistoryItem;
import com.edu.web.restservicewebbrowser.service.HistoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/history")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryResource {

    @Inject
    private HistoryService service;

    @POST
    public Response addHistory(HistoryItem item) {
        try {
            service.saveHistoryItem(item);
            return Response.status(Response.Status.CREATED).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Помилка збереження")
                    .build();
        }
    }
}