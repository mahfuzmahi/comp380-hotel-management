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
 * Controller class for the admin view account view. Contains methods to initialize the view by reading customers from file and displaying them as buttons in the defined VBox.
 * @author Justin_Scott, 11/20/2025
 */
public class AdminViewAccountController {

    /**
    * Holds the list of buttons representing customers.
    */
    @FXML
    private VBox buttonsAreaBoxUsers;

    @FXML
    private ListView<Button> listviewView;

    List<String> listOfRooms = null;
    
    List<Button> buttonlist = new ArrayList<>();
  
    /**
     * initializer method for AdminViewAccountController to read customers from file and display them as buttons in the defined VBox.
     */
    @FXML
    public void initialize() {
        buttonsAreaBoxUsers.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            //makes a scanner to read the customers file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("customers.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    String accountText = s.next();
                    for (int j = 0; j < 4; j++){
                                //skips the next 4 tokens (password, email, phone, bankinfo)
                                s.next();
                    }
                    //adds a button for each line with ButtonText as the text and sends to adminViewAccountDetails when clicked
                    buttonlist.add(new Button("user: " + accountText));
                    buttonlist.get(i).setOnAction(e -> {
                    //sets the last viewed account to the name of the selected account
                    App.setViewedAccount(accountText);
                    try {
                        App.setRoot("adminViewAccountDetails");
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    });
                    i++;
            }
            // Clear existing buttons and add the newly created buttons to the VBox
            buttonsAreaBoxUsers.getChildren().clear();
            buttonsAreaBoxUsers.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}



