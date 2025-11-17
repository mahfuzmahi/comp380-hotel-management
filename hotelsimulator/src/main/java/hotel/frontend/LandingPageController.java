package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;

public class LandingPageController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void switchToCreateAcc() throws IOException {
        App.setRoot("createAcc");
    } 

        @FXML
    private void switchToGuest() throws IOException {
        App.setRoot("primary");
    } 
}
