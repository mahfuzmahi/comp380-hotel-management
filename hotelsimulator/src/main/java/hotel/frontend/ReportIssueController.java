package hotel.frontend;

import java.io.FileWriter;
import java.io.IOException;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller for the Report Issue feature.
 * Username is auto-filled from the logged-in user and cannot be edited.
 *
 * Author: Jose Mendiola
 */
public class ReportIssueController {

    @FXML
    private TextField usernameField;   // read-only

    @FXML
    private TextField roomNumberField;

    @FXML
    private TextField floorNumberField;

    @FXML
    private TextArea issueField;

    @FXML
    private Label statusLabel;

    /**
     * Initialize: auto-fill logged-in username and lock the field.
     */
    @FXML
    private void initialize() {
        String currentUser = App.getCurrentUser();

        if (currentUser != null && !currentUser.trim().isEmpty()) {
            usernameField.setText(currentUser.trim());
            usernameField.setEditable(false);
        } else {
            usernameField.setText("");
            statusLabel.setText("No user is currently logged in.");
        }
    }

    /**
     * Handles the submit action when a user reports an issue.
     */
    @FXML
    private void handleSubmit() {

        String username = App.getCurrentUser();
        if (username == null || username.trim().isEmpty()) {
            statusLabel.setText("No user is currently logged in.");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "❌ No user is logged in. Please log in again.");
            }
            return;
        }
        username = username.trim();

        String roomNumber  = roomNumberField.getText() == null
                ? "" : roomNumberField.getText().trim();
        String floorNumber = floorNumberField.getText() == null
                ? "" : floorNumberField.getText().trim();
        String issue       = issueField.getText() == null
                ? "" : issueField.getText().trim();

        // Username no longer validated — it comes from session
        if (roomNumber.isEmpty() || floorNumber.isEmpty() || issue.isEmpty()) {
            statusLabel.setText("Please fill out room, floor, and issue.");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "⚠️ Please complete all fields before submitting.");
            }
            return;
        }

        String path = Hotel.filePath("manager_report.txt");

        try (FileWriter writer = new FileWriter(path, true)) {
            String cleanIssue = issue.replaceAll("\\r?\\n", " ");
            String employee = "NONE";

            // Username is implicit via session, but you can log it if you want later
            String line = cleanIssue + "," +
                          roomNumber + "," +
                          floorNumber + "," +
                          employee +
                          System.lineSeparator();

            writer.write(line);

        } catch (IOException e) {
            statusLabel.setText("Could not save issue.");
            e.printStackTrace();

            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "❌ Could not save your issue. Please try again.");
            }
            return;
        }

        statusLabel.setText("Issue submitted. Thank you!");
        if (statusLabel.getScene() != null) {
            Toast.show(statusLabel.getScene().getWindow(),
                       "✅ Issue submitted successfully.");
        }

        // Clear issue fields only (username stays)
        roomNumberField.clear();
        floorNumberField.clear();
        issueField.clear();
    }
}



