package com.explorer;

import com.explorer.storage.HistoryVault;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ExplorerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Explorer");

        HistoryVault.init();

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #2b2b2b;");

        createNewTab(tabPane);

        javafx.scene.control.Tab addTab = new javafx.scene.control.Tab("+");
        addTab.setClosable(false);
        addTab.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        tabPane.getTabs().add(addTab);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == addTab) {
                createNewTab(tabPane);
            }
        });

        Scene scene = new Scene(tabPane, 1100, 800);

        applyDarkTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createNewTab(TabPane tabPane) {
        Tab tabContent = new Tab();
        javafx.scene.control.Tab tab = new javafx.scene.control.Tab("Нова сторінка");
        tab.setContent(tabContent.getView());

        int size = tabPane.getTabs().size();
        int index = size > 0 ? size - 1 : 0;

        tabPane.getTabs().add(index, tab);
        tabPane.getSelectionModel().select(tab);

        tabContent.titleProperty().addListener((obs, oldTitle, newTitle) -> {
            if (newTitle != null && !newTitle.isEmpty()) {
                tab.setText(newTitle);
            }
        });
    }

    private void applyDarkTheme(Scene scene) {
        String css = """
            .root {
                -fx-base: #2b2b2b;
                -fx-background: #2b2b2b;
            }
            .tab-pane .tab-header-area .tab-header-background {
                -fx-background-color: #1e1e1e;
            }
            .tab-pane {
                -fx-tab-min-width: 120px;
                -fx-tab-max-width: 200px;
                -fx-tab-min-height: 35px;
            }
            .tab {
                -fx-background-color: #3c3f41;
                -fx-background-insets: 0 1 0 0;
                -fx-background-radius: 5 5 0 0;
                -fx-text-fill: #a9b7c6;
            }
            .tab:selected {
                -fx-background-color: #4e5254;
                -fx-text-fill: white;
                -fx-border-color: #00adb5; 
                -fx-border-width: 2 0 0 0; 
            }
            .tab-label { 
                -fx-font-family: 'Segoe UI', sans-serif;
                -fx-font-size: 13px;
            }
            .button {
                -fx-background-color: #00adb5;
                -fx-text-fill: white;
                -fx-background-radius: 4;
                -fx-cursor: hand;
            }
            .button:hover {
                -fx-background-color: #00ced1;
            }
            .text-field {
                -fx-background-color: #3c3f41;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #6d6d6d;
                -fx-background-radius: 4;
                -fx-border-color: #555;
                -fx-border-radius: 4;
            }
            .text-field:focused {
                -fx-border-color: #00adb5;
            }
            .scroll-pane > .viewport {
               -fx-background-color: #2b2b2b;
            }
        """;

        scene.getStylesheets().add("data:text/css," + css.replace("\n", ""));
    }

    public static void main(String[] args) {
        launch(args);
    }
}