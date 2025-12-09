package hotel.frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller that loads and displays available rooms for users.
 * Room data is read from rooms.txt and displayed as buttons that the user can select.
 * Only rooms marked as unoccupied are shown.
 *
 * Author: Jose
 * Version: 2.0
 */
public class UserRentRoomController implements Initializable {

    @FXML
    private VBox roomsBox;

    private final Hotel hotel = new Hotel();

    /**
     * Initializes the screen by loading available rooms when the fxml is displayed.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAvailableRooms();
    }

    /**
     * Loads available rooms from rooms.txt.
     * Creates a button for each available room and adds it to the VBox.
     * If no rooms are found or if an error occurs, a message is shown.
     */
    private void loadAvailableRooms() {
        roomsBox.getChildren().clear();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(Hotel.filePath("rooms.txt")))) {

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                // Expected format in rooms.txt:
                // roomNumber,floor,isOccupied,person,date,time,description,price
                String[] parts = line.split(",", 8);
                if (parts.length < 7) {
                    continue;
                }

                String roomNumber = parts[0].trim();
                String floor      = parts[1].trim();
                String isOccupied = parts[2].trim();
                String person     = parts[3].trim();
                String date       = parts[4].trim();
                String time       = parts[5].trim();
                String description= parts[6].trim();
                // price (parts[7]) is optional

                // Only show rooms that are NOT occupied
                if (!"FALSE".equalsIgnoreCase(isOccupied)) {
                    continue;
                }

                String buttonText = String.format(
                    "Floor %s - Room %s  |  %s  |  %s",
                    floor, roomNumber, description, "Available"
                );

                Button roomButton = new Button(buttonText);
                roomButton.setMaxWidth(Double.MAX_VALUE);

                // When the user clicks this room:
                roomButton.setOnAction(e -> {

                    String currentUser = App.getCurrentUser();
                    if (currentUser == null || currentUser.trim().isEmpty()) {
                        System.out.println("No user is currently logged in, cannot create reservation.");
                        return;
                    }
                    currentUser = currentUser.trim();

                    // 1) Append reservation: username,room,floor
                    try (FileWriter fw = new FileWriter(
                            Hotel.filePath("reservations.txt"), true)) {
                        fw.write(currentUser + "," + roomNumber + "," + floor
                                 + System.lineSeparator());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return;
                    }

                    // 2) Mark room as occupied in rooms.txt
                    boolean updated = hotel.updateRoomStatus(roomNumber, floor, currentUser);
                    if (!updated) {
                        System.out.println("Warning: reservation saved but room status not updated.");
                    }

                    // 3) Refresh list so this room disappears from "Rent a Room"
                    loadAvailableRooms();
                });

                roomsBox.getChildren().add(roomButton);
            }

            if (roomsBox.getChildren().isEmpty()) {
                roomsBox.getChildren().add(new Label("No available rooms found."));
            }

        } catch (IOException e) {
            e.printStackTrace();
            roomsBox.getChildren().add(
                new Label("Error loading rooms from DataFiles/rooms.txt"));
        }
    }
}

