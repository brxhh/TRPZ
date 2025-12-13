package com.edu.web.browserapp;

import com.edu.web.browserapp.requestHandle.IRequestHandler;
import com.edu.web.browserapp.requestHandle.RequestContext;
import com.edu.web.browserapp.requestHandle.handler.HistoryRequestHandler;
import com.edu.web.browserapp.requestHandle.handler.NetworkHandler;
import com.edu.web.browserapp.requestHandle.handler.SomeOtherRequestHandler;
import com.edu.web.browserapp.service.P2PSearchService;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BrowserApp extends Application {

    private WebView webView;
    private TextField addressBar;
    private Label statusLabel;
    private IRequestHandler loadPipeline;

    private P2PSearchService p2pService;
    private ListView<String> p2pResultsList;

    @Override
    public void start(Stage stage) {

        webView = new WebView();
        addressBar = new TextField("https://www.google.com");
        statusLabel = new Label("Готовий");

        buildLoadPipeline();

        p2pService = new P2PSearchService(foundUrl -> {
            if (!p2pResultsList.getItems().contains(foundUrl)) {
                p2pResultsList.getItems().addFirst(foundUrl);
            }
        });

        addressBar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loadPage(addressBar.getText());
            }
        });

        webView.getEngine().getLoadWorker().stateProperty().addListener((_, _, newState) -> {
            statusLabel.setText(newState.toString());
            if (newState == Worker.State.SUCCEEDED) {
                String loadedUrl = webView.getEngine().getLocation();
                statusLabel.setText("Завантажено: " + loadedUrl);
            }
        });

        HBox topBar = new HBox(10, new Label("URL:"), addressBar);
        topBar.setPadding(new Insets(10));
        HBox.setHgrow(addressBar, Priority.ALWAYS);

        VBox rightPanel = createP2PPanel();

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(webView);
        root.setRight(rightPanel);
        root.setBottom(statusLabel);
        BorderPane.setMargin(statusLabel, new Insets(5));

        loadPage(addressBar.getText());

        Scene scene = new Scene(root, 1200, 768);
        stage.setTitle("Web Browser + P2P Search");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createP2PPanel() {
        TextField searchField = new TextField();
        searchField.setPromptText("Пошук у мережі...");

        Button btnSearch = new Button("Знайти у пірів");
        p2pResultsList = new ListView<>();

        btnSearch.setOnAction(_ -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                p2pResultsList.getItems().clear();
                p2pResultsList.getItems().add("Шукаю: " + query + "...");
                p2pService.broadcastSearchRequest(query);
            }
        });

        p2pResultsList.setOnMouseClicked(_ -> {
            String selected = p2pResultsList.getSelectionModel().getSelectedItem();
            if (selected != null && selected.startsWith("http")) {
                addressBar.setText(selected);
                loadPage(selected);
            }
        });

        VBox panel = new VBox(10,
                new Label("P2P Історія"),
                searchField,
                btnSearch,
                new Separator(),
                p2pResultsList
        );
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(250);
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 0 0 0 1px;");
        return panel;
    }

    private void loadPage(String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "https://" + url;
        }

        var requestContext = new RequestContext(webView, url);

        loadPipeline.handle(requestContext);
    }

    private void buildLoadPipeline() {
        IRequestHandler historyHandler = new HistoryRequestHandler();
        IRequestHandler someOtherHandler = new SomeOtherRequestHandler();
        IRequestHandler networkHandler = new NetworkHandler();

        historyHandler.setNext(someOtherHandler)
                .setNext(networkHandler);

        loadPipeline = historyHandler;
    }

    @Override
    public void stop() throws Exception {
        if (p2pService != null) {
            p2pService.stop();
        }
        super.stop();
    }
}