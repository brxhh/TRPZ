package com.explorer.storage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryVault {
    private static final String DB_URL = "jdbc:sqlite:history.db";

    public static void init() {
        String sql = """
            CREATE TABLE IF NOT EXISTS history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                url TEXT NOT NULL,
                title TEXT,
                visit_time TEXT
            );
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logVisit(String url, String title) {
        String sql = "INSERT INTO history(url, title, visit_time) VALUES(?, ?, ?)";
        String timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, url);
                pstmt.setString(2, title == null ? "Unknown Page" : title);
                pstmt.setString(3, timeNow);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static List<VisitLog> fetchAll() {
        List<VisitLog> logs = new ArrayList<>();
        String sql = "SELECT id, url, title, visit_time FROM history ORDER BY id DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(new VisitLog(
                        rs.getInt("id"),
                        rs.getString("url"),
                        rs.getString("title"),
                        rs.getString("visit_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}