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
 
public class AdminIssuesController {

    @FXML
    private ListView<Button> listviewView;

    @FXML
    private VBox buttonsAreaBoxIssues;

    List<String> listOfRooms = null;
    
    //*private ObservableList<Button> buttonlist = FXCollections.observableArrayList();
    List<Button> buttonlist = new ArrayList<>();
  
    @FXML
    public void initialize() {
        buttonsAreaBoxIssues.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            Scanner s = new Scanner(new File(Hotel.filePath("manager_report.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                    String buttonText = s.next() + "\nAt Room" + s.next() + " Floor " + s.next() + "\nAssigned to: " + s.next(); 
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
            buttonsAreaBoxIssues.getChildren().clear();
            buttonsAreaBoxIssues.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}