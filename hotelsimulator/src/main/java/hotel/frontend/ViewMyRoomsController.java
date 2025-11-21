package hotel.frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller for displaying the rooms reserved by a specific user.
 * This class reads the reservation file, filters entries by username,
 * and shows the rooms in a list. It also provides simple user feedback
 * for empty input, missing data, or successful loading.
 *
 * Author: Jose
 * Version: 1.0
 */
public class ViewMyRoomsController {

    @FXML
    private TextField usernameField;

    @FXML
    private ListView<String> roomsList;

    @FXML
    private Label statusLabel;

    /**
     * Loads and displays all rooms reserved by the entered username.
     * Clears previous results, validates input, reads from reservations.txt,
     * and updates the UI based on whether matching rooms are found.
     */
    @FXML
    private void handleLoadRooms() {
        roomsList.getItems().clear();
        statusLabel.setText("");

        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            statusLabel.setText("Enter your username.");
            return;
        }

        String path = Hotel.filePath("reservations.txt");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String user = parts[0].trim();
                    String room = parts[1].trim();

                    if (user.equals(username)) {
                        roomsList.getItems().add("Room " + room);
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

     /**
     * Returns the user to the previous screen or main menu.
     * This is triggered by the Back button on the UI.
     */
    @FXML
    private void handleBack() throws Exception {
        App.setRoot("userMyRooms"); 
    }
}
