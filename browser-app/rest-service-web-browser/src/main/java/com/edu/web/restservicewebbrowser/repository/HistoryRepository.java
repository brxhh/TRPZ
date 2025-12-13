package com.edu.web.restservicewebbrowser.repository;

import com.edu.web.restservicewebbrowser.domain.HistoryItem;

import java.sql.SQLException;

public interface HistoryRepository {
    void save(HistoryItem HistoryItem) throws SQLException;
}
