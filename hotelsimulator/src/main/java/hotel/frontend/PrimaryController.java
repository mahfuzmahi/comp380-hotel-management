package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;
 
public class PrimaryController {

    //controller from template, is used as a placeholder
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
