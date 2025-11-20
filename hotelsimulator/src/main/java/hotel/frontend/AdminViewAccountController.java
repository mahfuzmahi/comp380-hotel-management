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
 
public class AdminViewAccountController {

    @FXML
    private ListView<Button> listviewView;

    @FXML
    private VBox buttonsAreaBoxUsers;

    List<String> listOfRooms = null;
    
    //*private ObservableList<Button> buttonlist = FXCollections.observableArrayList();
    List<Button> buttonlist = new ArrayList<>();
  
    @FXML
    public void initialize() {
        buttonsAreaBoxUsers.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            Scanner s = new Scanner(new File(Hotel.filePath("customers.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    String accText = s.next();
                    s.next();
                     buttonlist.add(new Button("user: " + accText));
                     buttonlist.get(i).setOnAction(e -> {
                        App.setViewedUser(accText);
                        try {
                            App.setRoot("adminViewAccountDetails");
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                     });
                     i++;
            }
            buttonsAreaBoxUsers.getChildren().clear();
            buttonsAreaBoxUsers.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}