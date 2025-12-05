package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.StringBuilder;

import hotel.backend.Hotel;
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
    private Label userNameLabel = new Label();
    @FXML
    private Label userEmailLabel = new Label(); 
    @FXML
    private Label userPhoneLabel = new Label();
    @FXML
    private Label userBankInfoLabel = new Label();
  
    /**
     * initialize(Method): initializer method for AdminViewAccountDetailsController to display details of the selected account.
     * //TODO if anyone wants to refatcor the entire page away, feel free.
     */
    @FXML
    public void initialize() {
        // Get the index of the current room being viewed
        //TODO refcactor to use room number directly, if possible
        String nameHolder = App.getViewedAccount();
        userNameLabel.setText("Username: " + nameHolder); 
        try {
            //makes a scanner to read the users file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("customers.txt"))).useDelimiter("\\R|,");
             
            while(s.hasNext() && s.next().equals(nameHolder) == false) {
            }

            //skips password
            s.next();
            userEmailLabel.setText("Email: " + s.next());
            userPhoneLabel.setText("Phone: " + s.next());
            StringBuilder tempBuilder = new StringBuilder(userPhoneLabel.getText());
            tempBuilder.insert(10, "-");
            tempBuilder.insert(14, "-");
            userPhoneLabel.setText(tempBuilder.toString());
            userBankInfoLabel.setText("Bank Info: " + s.next());
            s.close();;
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }              
}