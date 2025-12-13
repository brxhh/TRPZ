package com.edu.web.restservicewebbrowser.repository.impl;


import com.edu.web.restservicewebbrowser.config.db.PostgresDBConnection;
import com.edu.web.restservicewebbrowser.domain.resource.Resource;
import com.edu.web.restservicewebbrowser.repository.ResourceRepository;
import com.edu.web.restservicewebbrowser.visitor.resource.impl.ResourceSaveVisitor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;

@ApplicationScoped
public class ResourceRepositoryImpl implements ResourceRepository {

    @Inject
    private PostgresDBConnection connectionManager;

    public void save(Resource resource, int webPageId) {
        try (Connection conn = connectionManager.getConnection()) {

            ResourceSaveVisitor saveVisitor = new ResourceSaveVisitor(conn, webPageId);

            resource.visit(saveVisitor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
