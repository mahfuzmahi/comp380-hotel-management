package hotel.frontend;

import java.io.IOException;

import hotel.backend.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * LoginController(Class): Controller class for the login view. Contains methods to handle user input for logging in and scene switching upon successful login.
 * @author Justin_Scott, 11/6/2025
 */
public class LoginController {
    
    //instance of hotel to access backend methods
    private Hotel hotel = new Hotel();

    @FXML
    /**userInputFieldName(Variable): Holds user input for name from login page */
    private TextField userInputFieldName; 

    @FXML
    /**userInputFieldPassword(Variable): Holds user input for password from login page */
    private TextField userInputFieldPassword; 

    @FXML
    /**InstructionLabel(Variable): Holds the instruction label for login page */
    private Label InstructionLabel;

    /**
     * handleSubmitButton(Method): Handles the submit button action for logging in.
     * Collects user input, attempts to log in, and switches to the appropriate view upon success.
     * @param event The ActionEvent triggered by clicking the submit button.
     * @throws IOException If there is an error switching scenes.
     */
    @FXML
    private void handleSubmitButton(ActionEvent event) throws IOException{
        //TODO this is where the sign in data is collected
        String nameInput = userInputFieldName.getText();
        String passwordInput = userInputFieldPassword.getText();


        if(passwordInput.equals("admin")/*TODO hotel.AdminLogin(nameInput, passwordInput) */) {
            App.setCurrentUser(nameInput);
            App.setRoot("adminManageStaff");
        } else if (hotel.Login(nameInput, passwordInput)) {
            App.setCurrentUser(nameInput);
            App.setRoot("userMyRooms");
        } else{
            InstructionLabel.setText("login failed");
        }
    }
}
