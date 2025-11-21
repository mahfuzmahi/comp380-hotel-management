package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;
/**
 * PrimaryController(Class): Controller class for primary. Currently is a placeholder that is switched to during testing.
 * 
 */
public class PrimaryController {

    //controller from template, is used as a placeholder
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
