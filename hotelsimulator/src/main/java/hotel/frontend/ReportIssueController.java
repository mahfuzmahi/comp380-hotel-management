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
 * This class allows a user to describe an issue and saves it to a text file.
 * Input validation is performed and user feedback is displayed on screen.
 * 
 * Author: Jose
 * Version: 1.0
 */
public class ReportIssueController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextArea issueField;

    @FXML
    private Label statusLabel;

    /**
     * Handles the submit action when a user reports an issue.
     * Validates the fields, formats the text, and appends the issue
     * to the system's issue log file. Displays either a success or
     * error message depending on the result.
     */
    @FXML
    private void handleSubmit() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String issue = issueField.getText() == null ? "" : issueField.getText().trim();

        if (username.isEmpty() || issue.isEmpty()) {
            statusLabel.setText("Please enter your username and describe the issue.");
            return;
        }

        String path = Hotel.filePath("issues.txt");

        try (FileWriter writer = new FileWriter(path, true)) {
            
            writer.write(username + ", " + issue.replaceAll("\\r?\\n", " ") + System.lineSeparator());
        } catch (IOException e) {
            statusLabel.setText("Could not save issue.");
            e.printStackTrace();
            return;
        }

        statusLabel.setText("Issue submitted. Thank you!");
        issueField.clear();
    }
}
