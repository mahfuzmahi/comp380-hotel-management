package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
 
/**
 * Controller class for the admin room details view. Contains methods to initialize the view by reading and displaying details of the selected room.
 * @author Justin_Scott, 11/20/2025
 */
public class AdminRoomDetailsController {
        private Hotel hotel = new Hotel();
    /**
     *Label to display the room position (number and floor).
     */
    @FXML
    private Label roomPositionLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label costLabel;
    /**
     *Label to display the renter information of the room.
     */
    @FXML
    private Label renterLabel;

    /**
     *Holds the list of buttons representing actions related to the room (evicting or viewing renter).
     */
    @FXML
    private HBox buttonsAreaBoxRooms;

    List<String> listOfRooms = null;
  
    List<Button> buttonlist = new ArrayList<>();

    /**
     *initializer method for AdminRoomDetailsController to read and display details of the selected room.
     */
    @FXML
    public void initialize()
    {
        // Get the index of the current room being viewed
        int roomIndex = App.getCurrentRoom();
        String renter;
        try {
            //makes a scanner to read the rooms file and uses \n and , as delimiters
            Scanner s = new Scanner(new File(Hotel.filePath("rooms.txt"))).useDelimiter("\\R|,");
            for(int i = 0; i < roomIndex * 8; i++) {
                s.next();
            }
            // Set roomPositionLabel, if not occupied skip unused fields
            roomPositionLabel.setText("Room " + s.next() + " Floor " + s.next());
            //holds if the room is occupied (T/F)
            String isOccupied = s.next();
            if(isOccupied.equals("FALSE")){
                renterLabel.setText("Available");
                s.next();
                s.next();
                s.next();
            } else {   
                renter = s.next();
                renterLabel.setText("Occupied by " + renter + "\n" + "Leave Date: " + s.next() + " at " + s.next());
                    //adds button to evict renter
                    buttonlist.add(new Button("Evict " + renter));
                    buttonlist.get(0).setOnAction(e -> {
                        App.setViewedAccount(renter);
                        try {
                          App appInstance = new App();
                          hotel.updateRoomStatus(appInstance.lineToRoom(App.getCurrentRoom()), appInstance.lineToFloor(App.getCurrentRoom()), "TRUE");    
                          App.setRoot("adminViewRooms");
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                    });
            
                    //adds button to view renter details
                    buttonlist.add(new Button("View " + renter));
                    buttonlist.get(1).setOnAction(e -> {
                        App.setViewedAccount(renter);
                        try {
                            App.setRoot("adminViewAccountDetails");
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                    });
             }
            detailsLabel.setText(s.next());
            costLabel.setText("Cost per Night: $" + s.next());

            //adds buttons to the HBox
            buttonsAreaBoxRooms.getChildren().clear();
            buttonsAreaBoxRooms.getChildren().addAll(buttonlist); 
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }


}