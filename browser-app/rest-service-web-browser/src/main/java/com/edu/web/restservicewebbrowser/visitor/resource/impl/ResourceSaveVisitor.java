package com.edu.web.restservicewebbrowser.visitor.resource.impl;

import com.edu.web.restservicewebbrowser.domain.resource.CssResource;
import com.edu.web.restservicewebbrowser.domain.resource.ImageResource;
import com.edu.web.restservicewebbrowser.domain.resource.JsResource;
import com.edu.web.restservicewebbrowser.visitor.resource.IResourceVisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResourceSaveVisitor implements IResourceVisitor {

    private Connection connection;
    private int webPageId;

    public ResourceSaveVisitor(Connection connection, int webPageId) {
        this.connection = connection;
        this.webPageId = webPageId;
    }

    @Override
    public void visit(CssResource css) throws Exception {
        System.out.println("VISITOR: Збереження CssResource в таблицю CssResources...");

        String sql = "INSERT INTO CssResources (web_page_id, css_content, url) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, this.webPageId);
            stmt.setString(2, css.getCssContent());
            stmt.setString(3, css.getUrl());
            stmt.executeUpdate();
        }
    }

    @Override
    public void visit(JsResource js) throws Exception {
        System.out.println("VISITOR: Збереження JsResource в таблицю JsResources...");

        String sql = "INSERT INTO JsResources (web_page_id, js_content, url) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, this.webPageId);
            stmt.setString(2, js.getJsContent());
            stmt.setString(3, js.getUrl());
            stmt.executeUpdate();
        }
    }

    @Override
    public void visit(ImageResource image) throws Exception {
        System.out.println("VISITOR: Збереження ImageResource в таблицю ImageResources...");

        String sql = "INSERT INTO ImageResources (web_page_id, image_data, url) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, this.webPageId);
            stmt.setBytes(2, image.getImageData());
            stmt.setString(3, image.getUrl());
            stmt.executeUpdate();
        }
    }
}