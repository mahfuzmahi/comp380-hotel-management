package hotel.frontend;

import java.io.IOException;

import hotel.backend.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class for the create account view. Contains methods to handle user input for creating a new account and scene switching upon successful account creation.
 * @author Justin_Scott, 11/6/2025, 12/2/25
 */

public class CreateAccountController {

    //instance of hotel to access backend methods
    private Hotel hotel = new Hotel();

    @FXML
    /**Holds user input for name from create account page */
    private TextField userInputFieldName; 

    @FXML
    /**Holds user input for password from create account page */
    private TextField userInputFieldPassword; 

    @FXML
    /**Holds user input for email from create account page */
    private TextField userInputEmail; 

    @FXML
    /**Holds user input for phone number from create account page */
    private TextField userInputPhoneNumber; 

    @FXML
    /**Holds user input for bank information from create account page */
    private TextField userInputBankInfo; 

    @FXML
    /**Holds the instruction label for create account page */
    private Label InstructionLabel;

    /**
     *Handles the submit button action for creating a new account.
     * Collects user input, attempts to create the account, and switches to the login view upon success.
     * @param event The ActionEvent triggered by clicking the submit button.
     * @throws IOException If there is an error switching scenes.
     */
    
    @FXML
       private void handleUserButton(ActionEvent event) throws IOException{
        //this is where the new acc data is collected for user account
        String nameInput = userInputFieldName.getText();
        String passwordInput = userInputFieldPassword.getText();
        String emailInput = userInputEmail.getText();
        String phoneInput = userInputPhoneNumber.getText(); 
        String bankInfoInput = userInputBankInfo.getText();

        if(hotel.CreateAccount(nameInput, passwordInput, emailInput, phoneInput, bankInfoInput) == false) {
            InstructionLabel.setText("Unable to create account");
        }
        else {
                App.setRoot("login"); 
        }
    }

    @FXML
    private void handleAdminButton(ActionEvent event) throws IOException{
        //this is where the new acc data is collected for admin account
        String nameInput = userInputFieldName.getText();
        String passwordInput = userInputFieldPassword.getText();
        String emailInput = userInputEmail.getText();
        String phoneInput = userInputPhoneNumber.getText(); 

        if(hotel.createEmployeeAccount(nameInput, passwordInput, phoneInput, emailInput) == false) {
            InstructionLabel.setText("Unable to create account");
        }
        else {
                App.setRoot("login"); 
        }
    }
}