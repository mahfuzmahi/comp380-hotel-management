module hotel {
    requires javafx.controls;
    requires javafx.fxml;

    opens hotel.frontend to javafx.fxml;
    exports hotel.frontend;
}
