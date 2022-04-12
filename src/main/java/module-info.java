module com.occupiedcursor.occupied_cursor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.occupiedcursor to javafx.fxml;
    exports com.occupiedcursor;
}