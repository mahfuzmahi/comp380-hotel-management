package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;                                                                    
/**
 * Controller class for the admin dropdown menu and initializer for viewRevenue Contains methods to initialize the view, pull payment data from file, print it to the TextArea, and scene switches for most of the admin side .
 * @author Justin_Scott, 11/10/2025
 */

public class AdminController {

    /**TextArea to display payment information in fxml. 
     *
     */
    @FXML TextArea paymentsTextArea = new TextArea();

    /**
     * initializer method for adminViewRevenue.fxml to read payments from file and display them in the defined TextArea.
     * uses a while(s.hasNext()) loop to run until the file is empty.
     */

    @FXML
    public void initialize() {
        try {
            Scanner s = new Scanner(new File(Hotel.filePath("payments.txt"))).useDelimiter("\\r");
            while (s.hasNext()) {
                    paymentsTextArea.appendText(s.next() + "\n"); // display the found integer
                } 
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        paymentsTextArea.setEditable(false);
    }

    /*
     * Switches scenes, used in admin dropdown menu
     */
    
    @FXML
    private void switchToViewAccount() throws IOException {
        App.setRoot("adminViewAccounts");
    }

        @FXML
    private void switchToViewRooms() throws IOException {
        App.setRoot("adminViewRooms");
    }

        @FXML
    private void switchToViewRevenue() throws IOException {
        App.setRoot("adminViewRevenue");
    }

            @FXML
    private void switchToStaffManager() throws IOException {
        App.setRoot("adminManageStaff");
    }

        @FXML
    private void switchToReportManager() throws IOException {
        App.setRoot("adminManageIssues");
    }

            @FXML
    private void switchToLanding() throws IOException {
        App.setRoot("landingPage");
    }
}