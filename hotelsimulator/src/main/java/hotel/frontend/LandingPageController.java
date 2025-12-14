package hotel.frontend;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * Controller class for the landing page view. Contains methods to handle scene switching to login, create account, and guest views.
 * @author Justin_Scott, 11/6/2025
 */
public class LandingPageController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void switchToCreateAccount() throws IOException {
        App.setRoot("createAccount");
    } 
}
