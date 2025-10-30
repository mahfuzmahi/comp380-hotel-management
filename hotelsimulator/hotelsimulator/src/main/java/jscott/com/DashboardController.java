package jscott.com;

import java.io.IOException;
import javafx.fxml.FXML;
 
public class DashboardController {

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

}
