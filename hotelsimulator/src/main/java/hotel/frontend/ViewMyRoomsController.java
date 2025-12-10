package hotel.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller for displaying the rooms reserved by the current logged-in user.
 * Reads reservations.txt (username,room,floor[,nights]) and cross-checks rooms.txt to
 * show only rooms that are STILL occupied by that user.
 * Also allows the user to cancel a reservation.
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
                // expected: username,room,floor[,nights]
                if (parts.length >= 3) {
                    String user  = parts[0].trim();
                    String room  = parts[1].trim();
                    String floor = parts[2].trim();
                    String nights = (parts.length >= 4) ? parts[3].trim() : "";

                    // Only consider reservations belonging to this logged in user
                    if (!user.equals(username)) {
                        continue;
                    }

                    // Check rooms.txt to see if this room is STILL occupied by this user
                    if (isRoomCurrentlyOccupiedByUser(room, floor, username)) {
                        String labelText;
                        if (!nights.isEmpty()) {
                            labelText = "Room " + room + " (Floor " + floor + ") - " +
                                        nights + " night(s)";
                        } else {
                            labelText = "Room " + room + " (Floor " + floor + ")";
                        }
                        roomsList.getItems().add(labelText);
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
                    return "TRUE".equalsIgnoreCase(isOccupied) && person.equals(username);
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking room status: " + e.getMessage());
        }

        return false;
    }

    /**
     * Called when the user clicks "Cancel Reservation" button.
     * Cancels the selected reservation (if any) by:
     *  - marking the room as available in rooms.txt
     *  - removing the reservation line from reservations.txt
     */
    @FXML
    private void handleCancelReservation() {
        String selected = roomsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Please select a room to cancel.");
            return;
        }

        String username = App.getCurrentUser();
        if (username == null || username.trim().isEmpty()) {
            statusLabel.setText("No user is currently logged in.");
            return;
        }
        username = username.trim();

        // Parse "Room X (Floor Y) - Z night(s)" or "Room X (Floor Y)"
        String roomNumber;
        String floor;

        try {
            // Room number
            if (!selected.startsWith("Room ")) {
                statusLabel.setText("Unexpected room format.");
                return;
            }
            String afterRoom = selected.substring("Room ".length()); // "101 (Floor 2) - 3 night(s)"
            int spaceAfterRoom = afterRoom.indexOf(' ');
            roomNumber = afterRoom.substring(0, spaceAfterRoom).trim();

            // Floor
            int floorStart = selected.indexOf("(Floor ");
            int floorEnd = selected.indexOf(")", floorStart);
            floor = selected.substring(floorStart + "(Floor ".length(), floorEnd).trim();
        } catch (Exception e) {
            statusLabel.setText("Could not parse selected room.");
            return;
        }

        // 1) Mark room as available again in rooms.txt
        boolean freed = markRoomAsAvailable(roomNumber, floor);
        if (!freed) {
            statusLabel.setText("Could not cancel: room not found in rooms file.");
            return;
        }

        // 2) Remove reservation line for this user+room+floor from reservations.txt
        cancelReservationForUserAndRoom(username, roomNumber, floor);

        // 3) Refresh list
        handleLoadRooms();
        statusLabel.setText("Reservation cancelled.");
    }

    /**
     * Marks a room as AVAILABLE in rooms.txt, keeping description/price,
     */
    private boolean markRoomAsAvailable(String roomNumber, String floor) {
        String path = Hotel.filePath("rooms.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
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

                    String newLine = String.join(",",
                            roomNumber,
                            floor,
                            "FALSE",        // isOccupied
                            "#PERSON#",     // person
                            "XX/XX/XXXX",   // date
                            "XX:XX",        // time
                            description,
                            price
                    );
                    lines.add(newLine);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading rooms.txt in markRoomAsAvailable: " + e.getMessage());
            return false;
        }

        if (!found) {
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing rooms.txt in markRoomAsAvailable: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Removes a reservation line for this username + room + floor
     * from reservations.txt 
     */
    private void cancelReservationForUserAndRoom(String username, String roomNumber, String floor) {
        String path = Hotel.filePath("reservations.txt");
        List<String> keepLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                // expected: username,room,floor[,nights]
                if (parts.length >= 3) {
                    String user = parts[0].trim();
                    String room = parts[1].trim();
                    String flr  = parts[2].trim();

                    // skips the one we are cancelling
                    if (user.equals(username) && room.equals(roomNumber) && flr.equals(floor)) {
                        continue;
                    }
                }
                keepLines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading reservations.txt while cancelling: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String l : keepLines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing reservations.txt while cancelling: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() throws Exception {
        App.setRoot("userMyRooms");
    }
}





