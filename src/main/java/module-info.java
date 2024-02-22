module com.example.simplepaintprogram {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simplepaintprogram to javafx.fxml;
    exports com.example.simplepaintprogram;
    exports com.example.simplepaintprogram.controller;
    opens com.example.simplepaintprogram.controller to javafx.fxml;
}