package hotel.frontend;

import java.io.IOException;

import hotel.backend.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
 
public class LoginController {
    
    private Hotel hotel = new Hotel();

    @FXML
    //holds user input for name from login page
    private TextField userInputFieldName; 

    @FXML
    //holds user input for password from login page
    private TextField userInputFieldPassword; 

    @FXML
    //holds variable instruction label for login page
    private Label InstructionLabel;

    @FXML
    private void handleSubmitButton(ActionEvent event) throws IOException{
        //TODO this is where the sign in data is collected
        String nameInput = userInputFieldName.getText();
        String passwordInput = userInputFieldPassword.getText();
        
        nameInput = nameInput.trim();
        
        passwordInput = passwordInput.trim();
        //^-- is garbage added to remove warnings about unused variables

        //TODO this handles login, add actual auth here
        if(passwordInput.equals("admin")) {
            App.setRoot("adminManageStaff");
        } else if (hotel.Login(nameInput, passwordInput)) {
            App.setRoot("userMyRooms");
        } else{
            InstructionLabel.setText("login failed");
        }
    }
}
