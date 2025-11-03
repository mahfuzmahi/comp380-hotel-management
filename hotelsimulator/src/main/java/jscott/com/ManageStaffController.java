package jscott.com;

import java.io.IOException;
import javafx.fxml.FXML;
 
public class ManageStaffController {

    @FXML
    private void switchToAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
    }

        @FXML
    private void switchToIssueManager() throws IOException {
        App.setRoot("manageIssues");
    }

}
