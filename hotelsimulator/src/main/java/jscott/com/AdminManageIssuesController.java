package jscott.com;

import java.io.IOException;
import javafx.fxml.FXML;
 
public class AdminManageIssuesController {

    @FXML
    private void switchToAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
    }

        @FXML
    private void switchToStaffManager() throws IOException {
        App.setRoot("adminManageStaff");
    }

}
