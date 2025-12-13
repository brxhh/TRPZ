package com.edu.web.restservicewebbrowser.config.migration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

@ApplicationScoped
public class FlywayMigration {

    @Inject
    @ConfigProperty(name = "DB_URL")
    private String dbUrl;

    @Inject
    @ConfigProperty(name = "DB_USER")
    private String dbUser;

    @Inject
    @ConfigProperty(name = "DB_PASSWORD")
    private String dbPassword;

    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dbUrl, dbUser, dbPassword)
                    .locations("classpath:db/migration")
                    .load();

            flyway.migrate();

        } catch (Exception e) {
            System.err.println("[Flyway] Migration failed");
            e.printStackTrace();
        }
    }
}