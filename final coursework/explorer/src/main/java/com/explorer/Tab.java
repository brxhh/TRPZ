package com.explorer;

import com.explorer.explorer.PageLoader;
import com.explorer.storage.HistoryVault;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Tab {

    private final BorderPane root;
    private final TextField addressBar;
    private final PageLoader pageLoader;
    private final StringProperty titleProperty = new SimpleStringProperty("Нова сторінка");

    public Tab() {
        VBox viewPort = new VBox(10);
        viewPort.setPadding(new Insets(15));
        viewPort.setStyle("-fx-background-color: #f0f0f0;");

        ScrollPane scrollPane = new ScrollPane(viewPort);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: #2b2b2b; -fx-border-color: transparent;");

        addressBar = new TextField("https://en.wikipedia.org/wiki/Space");
        addressBar.setPromptText("Введіть посилання");
        addressBar.setPrefHeight(35);

        Button goButton = new Button("➜");
        goButton.setPrefHeight(35);
        goButton.setPrefWidth(40);
        goButton.setStyle("-fx-font-size: 16px; -fx-padding: 0;");

        pageLoader = new PageLoader(viewPort, titleProperty);

        Button historyBtn = new Button("Історія");
        historyBtn.setPrefHeight(35);
        historyBtn.setOnAction(e -> new HistoryWindow().show());

        HBox topBar = new HBox(10, addressBar, goButton, historyBtn);
        HBox.setHgrow(addressBar, Priority.ALWAYS);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 15, 10, 15));

        topBar.setStyle("-fx-background-color: #1e1e1e; -fx-border-color: #000; -fx-border-width: 0 0 1 0;");

        goButton.setOnAction(e -> loadPage());
        addressBar.setOnAction(e -> loadPage());

        root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(scrollPane);
    }

    public BorderPane getView() {
        return root;
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    private void loadPage() {
        String url = addressBar.getText().trim();
        if (url.isEmpty()) return;

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
            addressBar.setText(url);
        }

        pageLoader.clearScripts();

        final String finalUrl = url;
        String currentTitle = titleProperty.get();
        HistoryVault.logVisit(finalUrl, currentTitle);
        new Thread(() -> pageLoader.loadAndRender(finalUrl)).start();
    }
}