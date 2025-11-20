package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
 
public class AdminRoomDetailsController {

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
    private Label roomPosLabel;

    @FXML
    private Label renterLabel;

    @FXML
    private HBox buttonsAreaBoxRooms;

    List<String> listOfRooms = null;
  
    List<Button> buttonlist = new ArrayList<>();

    @FXML
    public void initialize()
    {
        int roomIndex = Integer.parseInt(App.getCurrentRoom());
        String renter;
        try {
            Scanner s = new Scanner(new File(filePath("rooms.txt"))).useDelimiter("\\R|,");
            for(int i = 0; i < roomIndex * 7/* TODO this is the number of fieds in rooms, change if more is added */; i++) {
                s.next();
            }
            roomPosLabel.setText("Room " + s.next() + " Floor " + s.next());
            String isOccupied = s.next();
            if(isOccupied.equals("FALSE")){
                renterLabel.setText("Available");
                s.next();
                s.next();
            } else {   
                renter = s.next();
                renterLabel.setText("Occupied by " + renter + "\n" + "Leave Date: " + s.next() + " at " + s.next());
                buttonlist.add(new Button("Evict " + renter));
                     buttonlist.get(0).setOnAction(e -> {
                        App.setViewedUser(renter);
                        try {
                            /*TODO Add backend to evict user */
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                    });
                    buttonlist.add(new Button("View " + renter));
                     buttonlist.get(1).setOnAction(e -> {
                        App.setViewedUser(renter);
                        try {
                            App.setRoot("adminViewAccountDetails");
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                    });
             }
            buttonsAreaBoxRooms.getChildren().clear();
            buttonsAreaBoxRooms.getChildren().addAll(buttonlist); 
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }


}