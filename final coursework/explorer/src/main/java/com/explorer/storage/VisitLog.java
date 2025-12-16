package com.explorer.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitLog {
    private final String url;
    private final String title;
    private final LocalDateTime timestamp;

    public VisitLog(int id, String url, String title, String timestampStr) {
        this.url = url;
        this.title = title;
        this.timestamp = LocalDateTime.parse(timestampStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUrl() { return url; }
    public String getTitle() { return title; }
    public String getFormattedTime() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm dd/MM"));
    }
}