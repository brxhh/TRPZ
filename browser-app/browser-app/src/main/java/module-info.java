module com.edu.web.browserapp {
    requires javafx.controls;
    requires javafx.web;
    requires java.net.http;


    opens com.edu.web.browserapp to javafx.fxml;
    exports com.edu.web.browserapp;
}