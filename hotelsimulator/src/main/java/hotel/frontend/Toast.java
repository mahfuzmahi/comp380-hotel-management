package hotel.frontend;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

public class Toast {

    // Simple version with default duration
    public static void show(Window ownerWindow, String message) {
        show(ownerWindow, message, 2500); // means 2.5 seconds
    }

    // Full version where you can set duration in ms
    public static void show(Window ownerWindow, String message, int durationMillis) {
        if (ownerWindow == null || message == null || message.trim().isEmpty()) {
            return;
        }

        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.85);"
              + "-fx-text-fill: white;"
              + "-fx-padding: 10 18;"
              + "-fx-background-radius: 20;"
              + "-fx-font-size: 13px;"
        );
        label.setWrapText(true);

        StackPane root = new StackPane(label);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: transparent;");
        root.setOpacity(0); // start transparent
        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);

        popup.getContent().add(root);

        // Show near bottom center of the window
        double x = ownerWindow.getX() + (ownerWindow.getWidth() / 2) - 150;
        double y = ownerWindow.getY() + ownerWindow.getHeight() - 100;

        popup.show(ownerWindow, x, y);

        // Fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Fade out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.millis(durationMillis));

        fadeOut.setOnFinished(e -> popup.hide());

        SequentialTransition sequence = new SequentialTransition(fadeIn, fadeOut);
        sequence.play();
    }
}

