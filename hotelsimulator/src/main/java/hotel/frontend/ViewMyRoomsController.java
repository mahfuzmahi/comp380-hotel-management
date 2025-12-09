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
* Reads reservations.txt (username,room,floor) and cross-checks rooms.txt to
* show only rooms that are STILL occupied by that user.
*
* Author: Jose Mendiola
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

        String reservationsPath = Hotel.filePath("reservations.txt");
        boolean foundAnyActive = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(reservationsPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                // expect: username,room,floor
                if (parts.length >= 3) {
                    String user  = parts[0].trim();
                    String room  = parts[1].trim();
                    String floor = parts[2].trim();

                    // Only consider reservations belonging to this logged in user
                    if (!user.equals(username)) {
                        continue;
                    }

                    // Check rooms.txt to see if this room is STILL occupied by this user
                    if (isRoomCurrentlyOccupiedByUser(room, floor, username)) {
                        roomsList.getItems().add("Room " + room + " (Floor " + floor + ")");
                        foundAnyActive = true;
                    }
                }
            }
        } catch (IOException e) {
            statusLabel.setText("Could not read reservations file.");
            return;
        }

        if (!foundAnyActive) {
            statusLabel.setText("No active rooms found for this user.");
        } else {
            statusLabel.setText("Rooms loaded.");
        }
    }

    /**
     * Checks rooms.txt to confirm that:
     *  - isOccupied == TRUE
     *  - person == username
     */
    private boolean isRoomCurrentlyOccupiedByUser(String roomNumber, String floor, String username) {
        String roomsPath = Hotel.filePath("rooms.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(roomsPath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Expected format:
                // roomNumber,floor,isOccupied,person,date,time,description,price
                String[] parts = line.split(",", 8);
                if (parts.length < 4) {
                    continue;
                }

                String rNum       = parts[0].trim();
                String rFloor     = parts[1].trim();
                String isOccupied = parts[2].trim();
                String person     = parts[3].trim();

                if (rNum.equals(roomNumber) && rFloor.equals(floor)) {
                    // Only count it as "my room" if still occupied by THIS user
                    return "TRUE".equalsIgnoreCase(isOccupied) && person.equals(username);
                }
            }
        } catch (IOException e) {
            // If error reading rooms.txt, treat as not occupied
            System.err.println("Error checking room status: " + e.getMessage());
        }

        return false;
    }

    @FXML
    private void handleBack() throws Exception {
        App.setRoot("userMyRooms");
    }
}



