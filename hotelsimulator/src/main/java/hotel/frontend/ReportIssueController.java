package hotel.frontend;

import java.io.FileWriter;
import java.io.IOException;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportIssueController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextArea issueField;

    @FXML
    private Label statusLabel;

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
