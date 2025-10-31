package jscott.com;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
 
public class CreateAccController {


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
        else{
            App.setRoot("login");
        }
    }
}
