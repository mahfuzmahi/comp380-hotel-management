package hotel.frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller for displaying the rooms reserved by the current logged-in user.
 * Reads reservations.txt (username,room,floor) and lists "Room X (Floor Y)".
 *
 * Author: Jose
 * Version: 2.0
 */
public class ViewMyRoomsController {

    @FXML
    private ListView<String> roomsList;

    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        handleLoadRooms();
    }

    @FXML
    private void handleLoadRooms() {
        roomsList.getItems().clear();
        statusLabel.setText("");

        String username = App.getCurrentUser();
        if (username == null || username.trim().isEmpty()) {
            statusLabel.setText("No user is currently logged in.");
            return;
        }
        username = username.trim();

        String path = Hotel.filePath("reservations.txt");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // expect: username,room,floor
                if (parts.length >= 3) {
                    String user = parts[0].trim();
                    String room = parts[1].trim();
                    String floor = parts[2].trim();

                    if (user.equals(username)) {
                        roomsList.getItems().add("Room " + room + " (Floor " + floor + ")");
                        found = true;
                    }
                }
            }
        } catch (IOException e) {
            statusLabel.setText("Could not read reservations file.");
            return;
        }

        if (!found) {
            statusLabel.setText("No rooms found for this user.");
        } else {
            statusLabel.setText("Rooms loaded.");
        }
    }

    @FXML
    private void handleBack() throws Exception {
        App.setRoot("userMyRooms");
    }
}


