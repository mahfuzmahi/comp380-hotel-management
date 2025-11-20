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
 
public class AdminManageStaffController{

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
    private ListView<Button> listviewView;

    @FXML
    private VBox buttonsAreaBoxStaff;

    List<String> listOfRooms = null;
    
    //*private ObservableList<Button> buttonlist = FXCollections.observableArrayList();
    List<Button> buttonlist = new ArrayList<>();
  
    @FXML
    public void initialize() {
        buttonsAreaBoxStaff.getChildren().addAll(buttonlist);
        int i = 0;
        try {
            Scanner s = new Scanner(new File(filePath("employees.txt"))).useDelimiter("\\R|,");
            while (s.hasNext()) {
                   //TODO change with employee file format changes
                    String buttonText = s.next(); 
                    buttonlist.add(new Button(buttonText));
                    buttonlist.get(i).setOnAction(e -> {
                    App.setViewedUser(buttonText);
                    try {
                        App.setRoot("adminManageStaffDetails");
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    });
                    i++;
            }
            buttonsAreaBoxStaff.getChildren().clear();
            buttonsAreaBoxStaff.getChildren().addAll(buttonlist);
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}