package hotel.frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Dashboard shown after a user logs in.
 * Displays a welcome message, quick summary, and navigation buttons.
 *
 * Author: Jose Mendiola
 */
public class UserDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label activeRoomsLabel;

    @FXML
    private Label statusLabel;

    private final Hotel hotel = new Hotel();

    @FXML
    private void initialize() {
        String currentUser = App.getCurrentUser();

        if (currentUser != null && !currentUser.trim().isEmpty()) {
            currentUser = currentUser.trim();
            welcomeLabel.setText("Welcome, " + currentUser + " 👋");
            updateActiveRoomsSummary(currentUser);
        } else {
            welcomeLabel.setText("Welcome! (No user logged in)");
            activeRoomsLabel.setText("");
            statusLabel.setText("No user is currently logged in.");
        }
    }

    /**
     * Counts how many rooms this user currently occupies
     * by reading rooms.txt where:
     * isOccupied == TRUE and person == username.
     */
    private void updateActiveRoomsSummary(String username) {
        int count = 0;

        String path = Hotel.filePath("rooms.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // roomNumber,floor,isOccupied,person,date,time,description,price
                String[] parts = line.split(",", 8);
                if (parts.length < 4) {
                    continue;
                }

                String isOccupied = parts[2].trim();
                String person     = parts[3].trim();

                if ("TRUE".equalsIgnoreCase(isOccupied)
                        && person.equalsIgnoreCase(username)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            activeRoomsLabel.setText("Could not read room status.");
            return;
        }

        if (count == 0) {
            activeRoomsLabel.setText("You currently have no active room reservations.");
        } else if (count == 1) {
            activeRoomsLabel.setText("You currently have 1 active room reservation.");
        } else {
            activeRoomsLabel.setText("You currently have " + count + " active room reservations.");
        }
    }

    // ===== Navigation buttons =====

    @FXML
    private void handleRentRoom() {
        try {
            App.setRoot("UserRentRoom"); 
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Could not open Rent Room page.");
        }
    }

    @FXML
    private void handleViewMyRooms() {
        try {
            
            App.setRoot("MyRooms");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Could not open View My Rooms page.");
        }
    }

    @FXML
    private void handleUpdateInfo() {
        try {
            App.setRoot("UserUpdateInfo"); 
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Could not open Update Info page.");
        }
    }

    @FXML
    private void handleReportIssue() {
        try {
            App.setRoot("ReportIssue"); 
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Could not open Report Issue page.");
        }
    }
}
