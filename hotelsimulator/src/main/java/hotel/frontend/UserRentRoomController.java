package hotel.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
 * Author: Jose Mendiola
 */
public class UserRentRoomController implements Initializable {

    @FXML
    private VBox roomsBox;

    private final Hotel hotel = new Hotel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAvailableRooms();
    }

    /**
     * Loads available rooms from rooms.txt.
     * Shows only rows where isOccupied == FALSE.
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

                // Expected format:
                // roomNumber,floor,isOccupied,person,date,time,description,price
                String[] parts = line.split(",", 8);
                if (parts.length < 7) {
                    continue;
                }

                String roomNumber = parts[0].trim();
                String floor      = parts[1].trim();
                String isOccupied = parts[2].trim();
                String description= parts[6].trim();

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

                roomButton.setOnAction(e -> {
                    String currentUser = App.getCurrentUser();
                    if (currentUser == null || currentUser.trim().isEmpty()) {
                        System.out.println("No user is currently logged in, cannot create reservation.");
                        return;
                    }
                    currentUser = currentUser.trim();

                    // 1) Save reservation: username,room,floor
                    try (FileWriter fw = new FileWriter(
                            Hotel.filePath("reservations.txt"), true)) {
                        fw.write(currentUser + "," + roomNumber + "," + floor
                                + System.lineSeparator());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return;
                    }

                    // 2) Mark this room as occupied by this user in rooms.txt
                    boolean updated = markRoomAsOccupied(roomNumber, floor, currentUser);
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

    /**
     * Updates rooms.txt so that the matching room line becomes:
     * roomNumber,floor,TRUE,currentUser,date,time,description,price
     */
    private boolean markRoomAsOccupied(String roomNumber, String floor, String customer) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(Hotel.filePath("rooms.txt")))) {

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    lines.add(line);
                    continue;
                }

                String[] parts = line.split(",", 8);
                if (parts.length < 2) {
                    lines.add(line);
                    continue;
                }

                String rNum   = parts[0].trim();
                String rFloor = parts[1].trim();

                if (rNum.equals(roomNumber) && rFloor.equals(floor)) {
                    found = true;

                    String description = (parts.length > 6) ? parts[6].trim()
                                                            : "Details of Room " + roomNumber;
                    String price = (parts.length > 7) ? parts[7].trim() : "100";

                    String date = LocalDate.now().toString();
                    String time = LocalTime.now().withNano(0).toString(); // HH:MM:SS

                    // isOccupied = TRUE, person = customer
                    String newLine = String.join(",",
                            roomNumber,
                            floor,
                            "TRUE",
                            customer,
                            date,
                            time,
                            description,
                            price
                    );
                    lines.add(newLine);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!found) {
            System.out.println("Room " + roomNumber + " on floor " + floor + " not found in rooms.txt.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(Hotel.filePath("rooms.txt")))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

