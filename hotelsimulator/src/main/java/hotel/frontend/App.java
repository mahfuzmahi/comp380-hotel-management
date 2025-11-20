package hotel.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.scene.layout.Region;
//import javafx.stage.Stage;
//import javafx.scene.control.Label;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static String currentUser;
    private static String viewedUser;
    private static String currentRoomIndex;
    public static void main(String[] args) {
        launch();
    }

    @Override

    public void start(Stage stage) throws IOException {
       //Scene scene = new Scene(createContent());
        scene = new Scene(loadFXML("landingPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static String getCurrentUser() {
        return  currentUser;
    }

    public static void setCurrentRoom(String room) {
        currentRoomIndex = room;
    }

    public static String getCurrentRoom() {
        return currentRoomIndex;
    }

    public static void setViewedUser(String username) {
        viewedUser = username;
    }

    public static String getViewedUser() {
        return viewedUser;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/hotel/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    

}