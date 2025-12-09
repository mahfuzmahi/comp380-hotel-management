package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
 
public class AdminIssuesController {

     /**
     * buttonsAreaBoxIssues(Variable): holds the list of buttons representing issues reported by guests. 
     */
    @FXML
    private VBox buttonsAreaBoxIssues;

    @FXML
    private ListView<Button> listviewView;

    List<String> listOfRooms = null;
    
    List<Button> buttonlist = new ArrayList<>();
  
    /**
     * initializer method for AdminIssuesController to read reported issues from file and display them as buttons in the defined VBox.
     * uses a while(s.hasNext()) loop to run until the file is empty.
    */
    @FXML
    public void initialize() {
        buttonsAreaBoxIssues.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            //makes a scanner to read the repot file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("manager_report.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    String buttonText = s.next() + "\nAt Room " + s.next() + " Floor " + s.next() + "\nAssigned to: " + s.next(); 
                     buttonlist.add(new Button(buttonText));
                     int iTemp = i;
                     buttonlist.get(i).setOnAction(e -> {
                        try {
                            App.setCurrentRoom(iTemp);
                            App.setRoot("adminManageIssuesDetails");
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                     });
                     i++;
            }
            // Clear existing buttons and add the newly created buttons to the VBox
            buttonsAreaBoxIssues.getChildren().clear();
            buttonsAreaBoxIssues.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}