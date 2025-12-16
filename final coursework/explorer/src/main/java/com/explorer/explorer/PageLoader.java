package com.explorer.explorer;

import com.explorer.explorer.render.*;
import com.explorer.explorer.render.model.PageStructure;
import com.explorer.connection.HttpProcessor;
import com.explorer.connection.Response;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PageLoader {

    private final HttpProcessor httpProcessor;
    private final Renderer renderer;
    private final HtmlParser htmlParser;
    private final VBox viewPort;
    private final StringProperty titleProperty;
    private final LayoutComposer layoutComposer;

    private final Map<String, String> loadedScripts = new HashMap<>();
    private String currentBaseUrl;

    public PageLoader(VBox viewPort, StringProperty titleProperty) {
        this.httpProcessor = new HttpProcessor();
        this.htmlParser = new HtmlParser();
        this.renderer = new Renderer();
        this.layoutComposer = new LayoutComposer();
        this.viewPort = viewPort;
        this.titleProperty = titleProperty;
    }

    public void clearScripts() {
        loadedScripts.clear();
    }

    public Map<String, String> getLoadedScripts() {
        return loadedScripts;
    }

    public final void loadAndRender(String url) {
        System.out.println("Starting page load for: " + url);

        Response response = fetchHttpResponse(url);

        if (response.isClientError() || response.isServerError()) {
            displayError(response);
            return;
        }

        PageStructure domPageStructure = parseHtml(response.getBody());

        fetchResources(domPageStructure);

        applyStyles(domPageStructure);

        buildFxNodes(domPageStructure);

        System.out.println("Page rendering finished.");
    }

    protected Response fetchHttpResponse(String url) {
        try {
            this.currentBaseUrl = new URL(url).toExternalForm();
        } catch (java.net.MalformedURLException e) {
            System.err.println("Invalid URL format for base URL: " + url);
        }
        return httpProcessor.loadUrl(url);
    }

    protected PageStructure parseHtml(String htmlContent) {
        return htmlParser.parse(htmlContent);
    }

    protected void fetchResources(PageStructure domPageStructure) {
        Platform.runLater(() -> viewPort.getChildren().addFirst(new Label("Fetching resources...")));

        ResourceLoader resourceVisitor = new ResourceLoader(httpProcessor, currentBaseUrl);

        TreeTraverser.traverse(domPageStructure.getRoot(), resourceVisitor);

        loadedScripts.forEach((scriptName, script) -> {
            System.out.println("Script name" + scriptName);
            System.out.println("Script" + script);
        });

        this.loadedScripts.putAll(resourceVisitor.getLoadedScripts());
    }

    protected void applyStyles(PageStructure domPageStructure) {
        TreeTraverser.traverse(domPageStructure.getRoot(), new StyleInjector());
    }

    protected void buildFxNodes(PageStructure domPageStructure) {
        RenderNode renderRoot = layoutComposer.build(domPageStructure);

        javafx.scene.Node fxRoot = renderer.render(renderRoot);

        Platform.runLater(() -> {
            if (!domPageStructure.title().isEmpty()) {
                titleProperty.set(domPageStructure.title());
            } else {
                titleProperty.set(currentBaseUrl);
            }

            viewPort.getChildren().clear();
            viewPort.getChildren().add(fxRoot);
        });
    }

    protected void displayError(Response response) {
        Platform.runLater(() -> {
            viewPort.getChildren().clear();
            viewPort.getChildren().add(new Label("Error: " + response.getStatusCode()));
        });
    }
}