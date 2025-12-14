package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.StringBuilder;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
 
/**
 * Controller class for the admin manage staff details view. Contains methods to initialize the view by reading and displaying details of the selected staff member.
 * @author Justin_Scott, 11/20/2025
 */
public class AdminManageStaffDetailsController {

    /**
     * Label to display the info of the staff member whose details are being viewed.
     */
    @FXML
    private Label staffNameLabel;

    @FXML
    private Label staffEmailLabel;

    @FXML
    private Label staffPhoneLabel;

    /**
     * initializer method for AdminManageStaffDetailsController to read and display details of the selected staff member.
     * uses a while loop with s.hasNext() and a current employee != person we want to find check to safely find the first staff member that matches the viewed account.
     */
    @FXML
    public void initialize()
    {
        try {
            //makes a scanner to read the employees file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("employees.txt"))).useDelimiter("\\R|,");
            //currentString holds the current employee name being checked
            String currentString = s.next();
            String viewedUser = App.getViewedAccount();
            //finds the viewed user in the file
            while (!currentString.equals(viewedUser) && s.hasNext()) {
                currentString = s.next();
            }
            staffNameLabel.setText("Staff Member: " + currentString);
            s.next(); // skip password
            staffEmailLabel.setText("Email: " + s.next());
            staffPhoneLabel.setText("Phone: " + s.next());
            StringBuilder tempBuilder = new StringBuilder(staffPhoneLabel.getText());
            tempBuilder.insert(10, "-");
            tempBuilder.insert(14, "-");
            staffPhoneLabel.setText(tempBuilder.toString());
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}