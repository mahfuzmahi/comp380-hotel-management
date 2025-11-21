package hotel.frontend;

import java.io.IOException;

import javafx.fxml.FXML;
 
public class UserMenuController {

    @FXML
    private void switchToRentRoom() throws IOException {
        App.setRoot("userRentRoom");
    }

        @FXML
    private void switchToViewRooms() throws IOException {
        App.setRoot("viewMyRooms");
    }

        @FXML
    private void switchToUpdateInfo() throws IOException {
        App.setRoot("userUpdateInfo");
    }

        @FXML
    private void switchToReport() throws IOException {
        App.setRoot("reportIssue");
    }

                @FXML
    private void switchToLanding() throws IOException {
        App.setRoot("landingPage");
    }
}
