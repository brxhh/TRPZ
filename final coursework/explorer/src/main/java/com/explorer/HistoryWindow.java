package com.explorer;

import com.explorer.storage.HistoryVault;
import com.explorer.storage.VisitLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HistoryWindow {

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Історія");

        TableView<VisitLog> table = new TableView<>();

        TableColumn<VisitLog, String> timeCol = new TableColumn<>("Час");
        timeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFormattedTime()));
        timeCol.setPrefWidth(120);

        TableColumn<VisitLog, String> urlCol = new TableColumn<>("Посилання");
        urlCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUrl()));
        urlCol.setPrefWidth(300);

        TableColumn<VisitLog, String> titleCol = new TableColumn<>("Заголовок");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        titleCol.setPrefWidth(200);

        table.getColumns().addAll(timeCol, urlCol, titleCol);

        table.getItems().addAll(HistoryVault.fetchAll());

        BorderPane root = new BorderPane(table);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-base: #2b2b2b; -fx-control-inner-background: #2b2b2b; -fx-background-color: #2b2b2b; -fx-table-cell-border-color: transparent;");

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}