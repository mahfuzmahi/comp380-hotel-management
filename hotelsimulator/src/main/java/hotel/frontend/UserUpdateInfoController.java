package hotel.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller that allows a user to update their account information.
 * Validates credentials and writes updated data to the customers file.
 *
 * Author: Jose
 * Version: 1.0
 */
public class UserUpdateInfoController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label statusLabel;

    private Hotel hotel = new Hotel();

    /**
     * Saves updated user information after validating the current username and password.
     * Reads from the customers file and writes changes to a temporary file before replacing it.
     */
    @FXML
    private void handleSave() {
        String username = usernameField.getText();
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();

        if (username == null || username.isEmpty()
                || oldPassword == null || oldPassword.isEmpty()
                || newPassword == null || newPassword.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        // Check that current username/password is valid
        if (!hotel.Login(username, oldPassword)) {
            statusLabel.setText("Current username/password is incorrect.");
            return;
        }

        File customersFile = new File(Hotel.filePath("customers.txt"));
        File tempFile = new File(customersFile.getParentFile(), "customers_tmp.txt");

        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(customersFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(username)) {
                    // Replace with new password
                    writer.write(username + ", " + newPassword);
                    writer.newLine();
                    updated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            statusLabel.setText("Error updating file.");
            e.printStackTrace();
            return;
        }

        if (!updated) {
            statusLabel.setText("User not found in file.");
            tempFile.delete();
            return;
        }

        // Replace original file with temporary updated file
        if (!customersFile.delete() || !tempFile.renameTo(customersFile)) {
            statusLabel.setText("Could not save changes.");
            return;
        }

        statusLabel.setText("Information updated successfully!");
        oldPasswordField.clear();
        newPasswordField.clear();
    }
}
