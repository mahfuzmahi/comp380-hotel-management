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
 
public class AdminRoomsController {

    @FXML
    private ListView<Button> listviewView;

    @FXML
    private VBox buttonsAreaBox;

    List<String> listOfRooms = null;
    
    //*private ObservableList<Button> buttonlist = FXCollections.observableArrayList();
    List<Button> buttonlist = new ArrayList<>();
  
    @FXML
    public void initialize() {
        buttonsAreaBox.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            Scanner s = new Scanner(new File(Hotel.filePath("rooms.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    String buttonText = "Room " + s.next() + " Floor " + s.next() + " "; 
                    String isOccupied = s.next();
                    if(isOccupied.equals("FALSE")){
                        buttonText += "Available ";
                        s.next();
                    } else {    
                        buttonText += "Occupied by " + s.next() +" ";
                     } 
                     s.next();
                     s.next();
                     s.next();
                     String rIndex = String.valueOf(i);
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
            buttonsAreaBox.getChildren().clear();
            buttonsAreaBox.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}