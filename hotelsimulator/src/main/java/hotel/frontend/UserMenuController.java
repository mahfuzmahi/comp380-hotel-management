package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;
 
public class UserMenuController {

    @FXML
    private void switchToRentRoom() throws IOException {
        App.setRoot("secondary");
    }

        @FXML
    private void switchToViewRooms() throws IOException {
        App.setRoot("secondary");
    }

        @FXML
    private void switchToUpdateInfo() throws IOException {
        App.setRoot("secondary");
    }

        @FXML
    private void switchToReport() throws IOException {
        App.setRoot("secondary");
    }

                @FXML
    private void switchToLanding() throws IOException {
        App.setRoot("landingPage");
    }
}
