package hotel.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
 
public class AdminViewAccountDetailsController {

    @FXML
    private Label userDetailsLabel = new Label();
  
    @FXML
    public void initialize() { 
        userDetailsLabel.setText("User: " + App.getViewedUser());
    }
       
}