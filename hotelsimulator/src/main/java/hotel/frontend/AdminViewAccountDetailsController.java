package hotel.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * AdminViewAccountDetailsController(Class): Controller class for the admin view account view. Contains methods to initialize the view by reading and displaying details of the selected account.
 * @author Justin_Scott, 11/20/2025
 */
public class AdminViewAccountDetailsController {

    /**
     * userDetailsLabel(Variable): Label to display the info of the account whose details are being viewed.
     */
    @FXML
    private Label userDetailsLabel = new Label();
  
    /**
     * initialize(Method): initializer method for AdminViewAccountDetailsController to display details of the selected account.
     * //TODO if anyone wants to refatcor the entire page away, feel free.
     */
    @FXML
    public void initialize() { 
        userDetailsLabel.setText("User: " + App.getViewedAccount());
    }
       
}