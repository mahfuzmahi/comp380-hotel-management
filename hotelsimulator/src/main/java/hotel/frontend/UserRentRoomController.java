package hotel.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

/**
 * Controller that loads and displays available rooms for users.
 * Room data is read from rooms.txt and displayed as buttons that the user can select.
 * Only rooms marked as unoccupied are shown.
 *
 * Author: Jose Mendiola, Mahfuz Ahmed
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

                // parse price 
                String priceStr = (parts.length > 7) ? parts[7].trim() : "100";
                double nightlyRate;
                try {
                    nightlyRate = Double.parseDouble(priceStr);
                } catch (NumberFormatException ex) {
                    nightlyRate = 100.0; 
                }

                // Only show rooms that are NOT occupied
                if (!"FALSE".equalsIgnoreCase(isOccupied)) {
                    continue;
                }

                String buttonText = String.format(
                        "Floor %s - Room %s  |  %s  |  $%.2f / night",
                        floor, roomNumber, description, nightlyRate
                );

                Button roomButton = new Button(buttonText);
                roomButton.setMaxWidth(Double.MAX_VALUE);

                // Make variables effectively final for the lambda
                final String fRoomNumber  = roomNumber;
                final String fFloor       = floor;
                final double fNightlyRate = nightlyRate;

    roomButton.setOnAction(e -> {
        String currentUser = App.getCurrentUser();
        if (currentUser == null || currentUser.trim().isEmpty()) {
         System.out.println("No user is currently logged in, cannot create reservation.");
            if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(),
                       "❌ No user is logged in. Please log in to rent a room.");
        }
        return;
    }
    currentUser = currentUser.trim();

    // 1) Ask user how many nights they want to stay
    Integer nights = promptForNights();
    if (nights == null) {
        // user cancelled or invalid input
        if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(),
                       "ℹ️ Reservation cancelled or invalid number of nights.");
        }
        return;
    }

    // 2) Calculate total price
    double total = fNightlyRate * nights;

    // 3) Show confirmation dialog with total
    Alert confirm = new Alert(AlertType.CONFIRMATION);
    confirm.setTitle("Confirm Reservation");
    confirm.setHeaderText("Confirm your stay");
    confirm.setContentText(String.format(
            "Room: %s (Floor %s)\nNights: %d\nPrice per night: $%.2f\n\nTOTAL: $%.2f\n\nDo you want to confirm this reservation?",
            fRoomNumber, fFloor, nights, fNightlyRate, total
    ));

    Optional<javafx.scene.control.ButtonType> choice = confirm.showAndWait();
    if (choice.isEmpty() || choice.get() != javafx.scene.control.ButtonType.OK) {
        // user cancelled in confirmation
        if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(),
                       "ℹ️ Reservation not confirmed.");
        }
        return;
    }


    // 4) Save reservation: username,room,floor,nights
    try (FileWriter fw = new FileWriter(
            Hotel.filePath("reservations.txt"), true)) {
        fw.write(currentUser + "," + fRoomNumber + "," + fFloor + "," + nights
                + System.lineSeparator());
    } catch (IOException ex) {
        ex.printStackTrace();
        if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(),
                       "❌ Failed to save reservation. Please try again.");
        }
        return;
    }

    // DO NOT REMOVE
    // this is to record the payments in the payments.txt file
    String paymentMethod = promptPaymentMethod();
    if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
        paymentMethod = "credit";
    }

    boolean paymentRecorded = hotel.Payment(currentUser, total, paymentMethod);
    if (!paymentRecorded) {
        System.out.println("reservation saved but payment not recorded.");
        if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(), "reservation saved but payment could not be recorded.");
        }
    }

    // 5) Mark this room as occupied by this user in rooms.txt
    boolean updated = markRoomAsOccupied(fRoomNumber, fFloor, currentUser, nights);
    if (!updated) {
        System.out.println("Warning: reservation saved but room status not updated.");
        if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(),
                       "⚠️ Reservation saved, but room status could not be updated.");
        }
    } else {
        if (roomsBox.getScene() != null) {
            Toast.show(roomsBox.getScene().getWindow(),
                       String.format(
                           "✅ Room %s booked for %d night(s).\nTotal: $%.2f ($%.2f/night)",
                           fRoomNumber, nights, total, fNightlyRate
                       ));
        }
    }

    // 6) Refresh list so this room disappears from "Rent a Room"
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
            if (roomsBox.getScene() != null) {
                Toast.show(roomsBox.getScene().getWindow(),
                           "❌ Error loading rooms data.");
            }
        }
    }

    /**
     * Prompts the user for how many nights they want to stay.
     * Returns an Integer >= 1, or null if cancelled/invalid.
     */
    private Integer promptForNights() {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Stay Duration");
        dialog.setHeaderText("How many nights would you like to stay?");
        dialog.setContentText("Nights:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return null; // user cancelled
        }

        String input = result.get().trim();
        try {
            int nights = Integer.parseInt(input);
            if (nights <= 0) {
                return null;
            }
            return nights;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Prompts the user for payment method.
     * DO NOT REMOVE
     * 
     * @return the payment method string or null, if cancelled.
     */
    private String promptPaymentMethod() {
        TextInputDialog dialog = new TextInputDialog("credit");
        dialog.setTitle("Payment Method");
        dialog.setHeaderText("Select payment method");
        dialog.setContentText("Payment method (credit/debit/cash):");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return null; // user cancelled the prompt
        }

        String input = result.get().trim().toLowerCase();
        if (input.isEmpty()) {
            return null;
        }
        
        // common payment method inputs
        if (input.equals("credit") || input.equals("credit card") || input.equals("cc")) {
            return "credit";
        } else if (input.equals("debit") || input.equals("debit card") || input.equals("dc")) {
            return "debit";
        } else if (input.equals("cash")) {
            return "cash";
        }
        
        // Return the input as it current is if it doesn't match any of the common patterns added
        return input;
    }

    /**
     * Updates rooms.txt so that the matching room line becomes:
     * roomNumber,floor,TRUE,currentUser,leaveDate,leaveTime,description,price
     *
     * leaveDate = today + nights
     * leaveTime = "11:00 AM"
     */
    private boolean markRoomAsOccupied(String roomNumber, String floor, String customer, int nights) {
        String path = Hotel.filePath("rooms.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {

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

                    LocalDate today     = LocalDate.now();
                    LocalDate leaveDate = today.plusDays(nights);
                    String date = leaveDate.toString();   // Leave date shown on admin side
                    String time = "11:00 AM";             // Checkout time

                    String newLine = String.join(",",
                            roomNumber,
                            floor,
                            "TRUE",      // isOccupied
                            customer,    // person
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
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




