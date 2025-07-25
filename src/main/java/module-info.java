module com.coder.finalweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;


    opens com.coder.finalweatherapp to javafx.fxml;
    exports com.coder.finalweatherapp;
}