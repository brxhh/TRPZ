package com.edu.web.restservicewebbrowser.config.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class PostgresDBConnection {

    @Inject
    @ConfigProperty(name = "DB_URL")
    private String dbUrl;

    @Inject
    @ConfigProperty(name = "DB_USER")
    private String dbUser;

    @Inject
    @ConfigProperty(name = "DB_PASSWORD")
    private String dbPassword;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
