package com.edu.web.restservicewebbrowser.repository.impl;

import com.edu.web.restservicewebbrowser.config.db.PostgresDBConnection;
import com.edu.web.restservicewebbrowser.domain.HistoryItem;
import com.edu.web.restservicewebbrowser.repository.HistoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ApplicationScoped
public class HistoryPostgresRepository implements HistoryRepository {

    @Inject
    private PostgresDBConnection connectionManager;

    public void save(HistoryItem item) throws SQLException {
        String sql = "INSERT INTO HistoryItem (url, visit_time) VALUES (?, ?)";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.url());
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        }
    }
}