module com.example.masathai {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.masathai to javafx.fxml;
    exports com.example.masathai;
}