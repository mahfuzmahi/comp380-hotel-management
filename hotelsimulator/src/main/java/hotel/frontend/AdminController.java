package hotel.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AdminController {

    @FXML TextArea paymentsTextArea = new TextArea();

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
    public void initialize() {
        try {
            Scanner s = new Scanner(new File(filePath("payments.txt"))).useDelimiter("\\r");
            while (s.hasNext()) {
                    paymentsTextArea.appendText(s.next() + "\n"); // display the found integer
                } 
            s.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        paymentsTextArea.setEditable(false);
    }

    @FXML
    private void switchToViewAccount() throws IOException {
        App.setRoot("adminViewAccounts");
    }

        @FXML
    private void switchToViewRooms() throws IOException {
        App.setRoot("adminViewRooms");
    }

        @FXML
    private void switchToViewRevenue() throws IOException {
        App.setRoot("adminViewRevenue");
    }

            @FXML
    private void switchToStaffManager() throws IOException {
        App.setRoot("adminManageStaff");
    }

        @FXML
    private void switchToReportManager() throws IOException {
        App.setRoot("adminManageIssues");
    }

            @FXML
    private void switchToLanding() throws IOException {
        App.setRoot("landingPage");
    }
}