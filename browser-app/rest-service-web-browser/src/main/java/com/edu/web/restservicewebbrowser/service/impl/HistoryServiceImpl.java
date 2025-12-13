package com.edu.web.restservicewebbrowser.service.impl;

import com.edu.web.restservicewebbrowser.domain.HistoryItem;
import com.edu.web.restservicewebbrowser.repository.HistoryRepository;
import com.edu.web.restservicewebbrowser.service.HistoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Set;

@ApplicationScoped
public class HistoryServiceImpl implements HistoryService {

    private static final Set<String> IGNORED_PROTOCOLS = Set.of(
            "about",
            "chrome",
            "file",
            "data"
    );

    private static final int MAX_URL_LENGTH = 2048;

    @Inject
    private HistoryRepository repository;

    public void saveHistoryItem(HistoryItem item) throws SQLException {

        if (item == null || item.url() == null || item.url().isBlank()) {
            throw new IllegalArgumentException("History item або URL не може бути порожнім.");
        }

        String urlString = item.url().trim();
        URI uri;

        try {
            uri = new URI(urlString);

            if (!uri.isAbsolute()) {
                throw new URISyntaxException(urlString, "URL повинен мати протокол (scheme).");
            }

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Некоректний формат URL: " + e.getMessage(), e);
        }

        String protocol = uri.getScheme();

        if (protocol == null || IGNORED_PROTOCOLS.contains(protocol.toLowerCase())) {
            System.out.println("Ігнорування URL з протоколом: " + protocol);
            return;
        }

        if (urlString.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("URL занадто довгий (макс " + MAX_URL_LENGTH + " символів).");
        }

        repository.save(item);
    }
}
