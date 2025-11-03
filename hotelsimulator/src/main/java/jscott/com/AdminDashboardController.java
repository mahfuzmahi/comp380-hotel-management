package jscott.com;

import java.io.IOException;
import javafx.fxml.FXML;
 
public class AdminDashboardController {

    @FXML
    private void switchToViewAccount() throws IOException {
        App.setRoot("secondary");
    }

        @FXML
    private void switchToViewRooms() throws IOException {
        App.setRoot("adminViewRooms");
    }

        @FXML
    private void switchToViewRevenue() throws IOException {
        App.setRoot("secondary");
    }

            @FXML
    private void switchToStaffManager() throws IOException {
        App.setRoot("manageStaff");
    }

        @FXML
    private void switchToReportManager() throws IOException {
        App.setRoot("manageIssues");
    }

}
