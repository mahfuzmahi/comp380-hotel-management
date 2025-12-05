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
 
/**
 * Controller class for the admin manage staff view. Contains methods to initialize the view by reading staff members from file and displaying them as buttons in the defined VBox.
 * @author Justin_Scott, 11/20/2025, 12/3/25
 */
public class AdminManageStaffController{
   
    /**
    * Holds the list of buttons representing staff members.
    */
    @FXML
    private VBox buttonsAreaBoxStaff;
   
    @FXML
    private ListView<Button> listviewView;

    List<String> listOfRooms = null;
    
    List<Button> buttonlist = new ArrayList<>();
  
    /**
     *initializer method for AdminManageStaffController to read staff members from file and display them as buttons in the defined VBox.
     * uses a while(s.hasNext()) loop to run until the file is empty.
    */
    @FXML
    public void initialize() {
        buttonsAreaBoxStaff.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            //makes a scanner to read the employees file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("employees.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    //TODO change with employee file format changes
                    String buttonText = s.next(); 
                    //adds a button for each line with ButtonText as the text and sends to adminManageStaffDetails when clicked
                    buttonlist.add(new Button(buttonText));
                    buttonlist.get(i).setOnAction(e -> {
                    //sets the last viewed user to the name of the selected staff
                    App.setViewedAccount(buttonText);
                    try {
                        App.setRoot("adminManageStaffDetails");
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    });
                    for(int j = 0; j < 3; j++) {
                        s.next(); //skip the next 3 tokens (password, email, phone)
                    }
                    i++;
            }
            // Clear existing buttons and add the newly created buttons to the VBox
            buttonsAreaBoxStaff.getChildren().clear();
            buttonsAreaBoxStaff.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}