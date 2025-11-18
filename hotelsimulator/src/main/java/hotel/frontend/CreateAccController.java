package hotel.frontend;

import java.io.IOException;

import hotel.backend.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
 
public class CreateAccController {

    private Hotel hotel = new Hotel();

    @FXML
    //holds user input for name from create account page
    private TextField userInputFieldName; 

    @FXML
    //holds user input for password from create account page
    private TextField userInputFieldPassword; 

    @FXML
    //holds variable instruction label for create account page
    private Label InstructionLabel;

    @FXML
    private void handleSubmitButton(ActionEvent event) throws IOException{
        //TODO this is where the new acc data is collected
        String nameInput = userInputFieldName.getText();
        String passwordInput = userInputFieldPassword.getText();


        
        //TODO add new acc auth here
        if(nameInput.isEmpty() || passwordInput.isEmpty()) {
            InstructionLabel.setText("Invalid input");
        }
        else {
            if(hotel.CreateAccount(nameInput, passwordInput)) {
                InstructionLabel.setText("Account created successfully");
                App.setRoot("login");
            } else {
                InstructionLabel.setText("Unable to create account");
            }
        }
    }
}
