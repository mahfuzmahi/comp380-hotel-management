package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    //controller from template, is used as a placeholder
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}