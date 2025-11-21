package hotel.frontend;

import java.io.IOException;

import hotel.backend.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * CreateAccountController(Class): Controller class for the create account view. Contains methods to handle user input for creating a new account and scene switching upon successful account creation.
 * @author Justin_Scott, 11/6/2025
 */
public class CreateAccountController {

    //instance of hotel to access backend methods
    private Hotel hotel = new Hotel();

    @FXML
    /**userInputFieldName(Variable): Holds user input for name from create account page */
    private TextField userInputFieldName; 

    @FXML
    /**userInputFieldPassword(Variable): Holds user input for password from create account page */
    private TextField userInputFieldPassword; 

    @FXML
    /**InstructionLabel(Variable): Holds the instruction label for create account page */
    private Label InstructionLabel;

    /**
     * handleSubmitButton(Method): Handles the submit button action for creating a new account.
     * Collects user input, attempts to create the account, and switches to the login view upon success.
     * @param event The ActionEvent triggered by clicking the submit button.
     * @throws IOException If there is an error switching scenes.
     */
    @FXML
    private void handleSubmitButton(ActionEvent event) throws IOException{
        //TODO this is where the new acc data is collected
        String nameInput = userInputFieldName.getText();
        String passwordInput = userInputFieldPassword.getText();

        //TODO add new acc auth here
        boolean holder = hotel.CreateAccount(nameInput, passwordInput);

        if(holder == false) {
            InstructionLabel.setText("Unable to create account");
        }
        else {
                App.setRoot("login"); 
        }
    }
}
