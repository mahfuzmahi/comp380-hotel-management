package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * SecondaryController(Class): Controller class for secondary. Currently is a placeholder that is switched to during testing.
 */
public class SecondaryController {

    //controller from template, is used as a placeholder
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}