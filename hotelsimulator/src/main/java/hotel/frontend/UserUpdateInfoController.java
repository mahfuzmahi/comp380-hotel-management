package hotel.frontend;

import hotel.backend.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller that allows a user to update their account information.
 * Username cannot be changed. User can update password, email, phone, and bank info.
 *
 * Author: Jose Mendiola
 */
public class UserUpdateInfoController {

    @FXML
    private TextField usernameField;        // read-only

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField bankField;

    @FXML
    private Label statusLabel;

    private Hotel hotel = new Hotel();

    /**
     * Initialize: show the currently logged-in user and lock the username field.
     */
    @FXML
    private void initialize() {
        String currentUser = App.getCurrentUser();

        if (currentUser != null && !currentUser.trim().isEmpty()) {
            usernameField.setText(currentUser.trim());
            usernameField.setEditable(false); // username cant be changed
        } else {
            usernameField.setText("");
            statusLabel.setText("No user is currently logged in.");
            // ⚠️ Don't use Toast here: scene/window may not be attached yet in initialize()
        }
    }

    /**
     * Saves updated user information after entering the current password.
     * Uses the Hotel.updateAccount class to update password, email, phone, and bank info.
     */
    @FXML
    private void handleSave() {
        String username = App.getCurrentUser();
        if (username == null || username.trim().isEmpty()) {
            statusLabel.setText("No user is currently logged in.");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "❌ No user is logged in. Please log in first.");
            }
            return;
        }
        username = username.trim();

        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String bank = bankField.getText();

        // current password has to be entered
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            statusLabel.setText("Please enter your current password.");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "⚠️ Please enter your current password.");
            }
            return;
        }

        // needs to change
        boolean anyChange =
            (newPassword != null && !newPassword.trim().isEmpty()) ||
            (email != null && !email.trim().isEmpty()) ||
            (phone != null && !phone.trim().isEmpty()) ||
            (bank != null && !bank.trim().isEmpty());

        if (!anyChange) {
            statusLabel.setText("Please change at least one field (password, email, phone, or bank).");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "ℹ️ Change at least one field before saving.");
            }
            return;
        }

        // Verify current password
        if (!hotel.Login(username, oldPassword)) {
            statusLabel.setText("Current password is incorrect.");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "❌ Current password is incorrect.");
            }
            return;
        }

        // Only send non-empty values; null = keep existing value in Hotel.updateAccount
        String newPassParam = (newPassword != null && !newPassword.trim().isEmpty())
                ? newPassword.trim() : null;
        String emailParam = (email != null && !email.trim().isEmpty())
                ? email.trim() : null;
        String phoneParam = (phone != null && !phone.trim().isEmpty())
                ? phone.trim() : null;
        String bankParam = (bank != null && !bank.trim().isEmpty())
                ? bank.trim() : null;

        boolean success = hotel.updateAccount(username, newPassParam, emailParam, phoneParam, bankParam);

        if (success) {
            statusLabel.setText("Information updated successfully!");
            oldPasswordField.clear();
            newPasswordField.clear();

            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "✅ Your information has been updated.");
            }
        } else {
            statusLabel.setText("Could not save changes.");
            if (statusLabel.getScene() != null) {
                Toast.show(statusLabel.getScene().getWindow(),
                           "❌ Could not save your changes.");
            }
        }
    }
}


