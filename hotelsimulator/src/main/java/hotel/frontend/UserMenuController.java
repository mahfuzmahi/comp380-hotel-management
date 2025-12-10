package hotel.frontend;

import java.io.IOException;

import javafx.fxml.FXML;

/**
 * UserMenuController(Class): Controller class for the user dropdown menu. Contains methods to handle scene switching to various user functionalities.
 * @author Justin_Scott, 11/11/2025
 */
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
    private void switchToDashboard() throws IOException {
        App.setRoot("UserDashboard");
}

                @FXML
    private void switchToLanding() throws IOException {
        App.setRoot("landingPage");
    }
}
