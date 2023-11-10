module com.example.masathai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens com.example.masathai to javafx.fxml;
    exports com.example.masathai;
    exports com.example.masathai.controller;
    opens com.example.masathai.controller to javafx.fxml;
}