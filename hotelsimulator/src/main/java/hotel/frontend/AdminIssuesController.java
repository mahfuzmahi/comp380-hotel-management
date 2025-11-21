package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;

import hotel.backend.Hotel;

/**
 * AdminIssuesController(Class): Controller class for the admin issues view. Contains methods to initialize the view by reading reported issues from file and displaying them as buttons in the defined VBox.
 * @author Justin_Scott, 11/20/2025
 */
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
     * initialize(Method): initializer method for AdminIssuesController to read reported issues from file and display them as buttons in the defined VBox.
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
                     String buttonText = s.next() + "\nAt Room" + s.next() + " Floor " + s.next() + "\nAssigned to: " + s.next(); 
                     //adds a button for each line with ButtonText as the text and sends to adminManageIssues when clicked
                     buttonlist.add(new Button(buttonText));
                     buttonlist.get(i).setOnAction(e -> {
                        try {
                            //TODO: Replace NONE with selected staff.
                            App.setRoot("adminManageIssues");
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