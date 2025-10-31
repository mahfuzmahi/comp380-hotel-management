module jscott.com {
    requires javafx.controls;
    requires javafx.fxml;

    opens jscott.com to javafx.fxml;
    exports jscott.com;
}
