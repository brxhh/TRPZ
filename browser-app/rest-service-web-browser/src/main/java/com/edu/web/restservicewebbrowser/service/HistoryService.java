package com.edu.web.restservicewebbrowser.service;

import com.edu.web.restservicewebbrowser.domain.HistoryItem;

import java.sql.SQLException;

public interface HistoryService {
    void saveHistoryItem(HistoryItem historyItem) throws SQLException;
}
