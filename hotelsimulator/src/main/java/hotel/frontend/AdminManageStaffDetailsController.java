package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
 
public class AdminManageStaffDetailsController {

    @FXML
    private Label staffNameLabel;

    @FXML
    public void initialize()
    {
        try {
            Scanner s = new Scanner(new File(Hotel.filePath("employees.txt"))).useDelimiter("\\R|,");
            String currString = s.next();
            String viewedUser = App.getViewedUser();
            while (!currString.equals(viewedUser) && s.hasNext()) {
                currString = s.next();
            }
            staffNameLabel.setText("Staff Member: " + currString);
            //TODO add more staff details when file format is updated
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}