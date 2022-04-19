module com.main.moyuquesion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.main.moyuquesion to javafx.fxml;
    exports com.main.moyuquesion;
    exports com.main.moyuquesion.controller;
    opens com.main.moyuquesion.controller to javafx.fxml;
}