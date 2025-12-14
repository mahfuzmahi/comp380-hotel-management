package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import hotel.backend.Hotel;

/**
 * Controller class for the admin issues view.  Shows details on a specific issue.  Contains methods to initialize the view by reading reported issues from file and displaying them as buttons in the defined VBox.
 * @author Justin_Scott, 12/3/2025
 */

public class AdminIssuesDetailsController {

    @FXML
    private Label issueLabel;

    @FXML
    private Label roomNumLabel;

    @FXML
    private Label floorNumLabel;

    @FXML
    private Label assignedLabel;

    @FXML
    private void switchToChooseStaff() throws IOException {
        App.setRoot("adminManageissuesDetails2");
    }

     @FXML
    public void initialize() {
        try {
            //makes a scanner to read the repot file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("manager_report.txt"))).useDelimiter("\\R|,");
            for(int i = 0; i < 4 * App.getCurrentRoom(); i++ ){
                if(s.hasNext()==false)
                { 
                    System.err.println("unexpected EoF at AIDC");
                    break;

                }
                s.next();
            }
            issueLabel.setText(s.next()+ "\n At:");
            roomNumLabel.setText("Room " + s.next());
            floorNumLabel.setText("Floor " + s.next());
            assignedLabel.setText("Assigned Staff: " + s.next());
            s.close();
        }catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }

}