package hotel.frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller that loads and displays available rooms for users.
 * Room data is read from a file and displayed as buttons that the user can select.
 * Only rooms marked as unoccupied are shown.
 *
 * Author: Jose
 * Version: 1.0
 */
public class UserRentRoomController implements Initializable {

    @FXML
    private VBox roomsBox;

    /**
     * Initializes the screen by loading available rooms when the fxml is displayed.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAvailableRooms();
    }

    /**
     * Loads available rooms from DataFiles/rooms.txt.
     * Creates a button for each available room and adds it to the VBox.
     * If no rooms are found or if an error occurs, a message is shown.
     */
    private void loadAvailableRooms() {
        roomsBox.getChildren().clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("DataFiles/rooms.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] parts = line.split(",", 7);
                if (parts.length < 7) {
                    continue; 
                }

                String roomNumber = parts[0].trim();
                String floor = parts[1].trim();
                String isOccupied = parts[2].trim();
                String person = parts[3].trim();
                String date = parts[4].trim();
                String time = parts[5].trim();
                String description = parts[6].trim();

                
                if (!"FALSE".equalsIgnoreCase(isOccupied)) {
                    continue;
                }

                String buttonText = String.format(
                    "Floor %s - Room %s  |  %s  |  %s",
                    floor, roomNumber, description, "Available"
                );

                Button roomButton = new Button(buttonText);
                roomButton.setMaxWidth(Double.MAX_VALUE);

                // Placeholder action for room selection
                roomButton.setOnAction(e -> {
                    // In the future we could navigate to a "room details" or "confirm booking" page.
                    System.out.println("User selected room " + roomNumber + " on floor " + floor);
                });

                roomsBox.getChildren().add(roomButton);
            }

            if (roomsBox.getChildren().isEmpty()) {
                roomsBox.getChildren().add(new Label("No available rooms found."));
            }

        } catch (IOException e) {
            e.printStackTrace();
            roomsBox.getChildren().add(new Label("Error loading rooms from DataFiles/rooms.txt"));
        }
    }
}