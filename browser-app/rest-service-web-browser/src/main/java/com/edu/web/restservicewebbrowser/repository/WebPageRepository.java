package com.edu.web.restservicewebbrowser.repository;

import java.sql.SQLException;

public interface WebPageRepository {
    int saveAndGetId(String url, String htmlContent) throws SQLException;
}
