package jscott.com;

import java.io.IOException;
import javafx.fxml.FXML;

public class AdminViewRoomsController {

    @FXML
    private void switchToAdminDashboard() throws IOException {
        App.setRoot("adminDashboard");
    }
}
