package com.edu.web.restservicewebbrowser.repository.impl;

import com.edu.web.restservicewebbrowser.config.db.PostgresDBConnection;
import com.edu.web.restservicewebbrowser.repository.WebPageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@ApplicationScoped
public class WebPageRepositoryImpl implements WebPageRepository {

    @Inject
    private PostgresDBConnection connectionManager;

    @Override
    public int saveAndGetId(String url, String htmlContent) throws SQLException {
        String sql = "INSERT INTO WebPages (url, html_content) VALUES (?, ?)";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, url);
            stmt.setString(2, htmlContent);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Не вдалося створити WebPage, ID не отримано.");
    }
}