package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
 
public class AdminManageStaffDetailsController {

    String filePath(String fileName) {
        String current = System.getProperty("user.dir");

        File dataFiles = new File(current, "DataFiles");

        if(dataFiles.isDirectory()) {
            return new File(dataFiles, fileName).getPath();
        }

        File parentFiles = new File(current + "/../DataFiles");

        if(parentFiles.isDirectory()) {
            return new File(parentFiles, fileName).getPath();
        }

        // fallback
        return "DataFiles/" + fileName;
    }

    @FXML
    private Label staffNameLabel;

    @FXML
    public void initialize()
    {
        try {
            Scanner s = new Scanner(new File(filePath("employees.txt"))).useDelimiter("\\R|,");
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