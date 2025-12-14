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
 * Controller class for the admin rooms view. Contains methods to initialize the view by reading rooms from file and displaying them as buttons in the defined VBox.
 * @author Justin_Scott, 11/19/2025
 */

public class AdminRoomsController {

    /**
     *Holds the list of buttons representing rooms.
     */

    @FXML
    private VBox buttonsAreaBox;

    @FXML
    private ListView<Button> listviewView;

    List<String> listOfRooms = null;
    
    List<Button> buttonlist = new ArrayList<>();
  

    /**
     *Initializer method for AdminRoomsController to read rooms from file and display them as buttons in the defined VBox.
     */

    @FXML
    public void initialize() {
        buttonsAreaBox.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            //makes a scanner to read the rooms file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("rooms.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    String buttonText = "Room " + s.next() + " Floor " + s.next() + " "; 
                    String isOccupied = s.next();
                    //checks if room is occupied and adds relevant info to button text
                    if(isOccupied.equals("FALSE")){
                        buttonText += "Available ";
                        s.next();
                    } else {    
                    buttonText += "Occupied by " + s.next() +" ";
                    } 
                    // Skip the next three fields (leave date and time, room features, cost per night)
                    s.next();
                    s.next();
                    s.next();
                    s.next();
                    //saves the room index to send to adminRoomDetails when clicked
                    int rIndex = i;
                    //adds a button for each line with ButtonText as the text and sends to adminRoomDetails when clicked
                    buttonlist.add(new Button(buttonText));
                    buttonlist.get(i).setOnAction(e -> {
                    App.setCurrentRoom(rIndex);
                    try {
                        App.setRoot("adminRoomDetails");
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    });
                    i++;
            }
            // Clear existing buttons and add the newly created buttons to the VBox
            buttonsAreaBox.getChildren().clear();
            buttonsAreaBox.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}