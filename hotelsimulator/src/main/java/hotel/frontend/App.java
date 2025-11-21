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
    /**currentUser(Variable): Stores the username of the currently logged-in user. */
    private static String currentUser;
    /**viewedUser(Variable): Stores the username of the account currently being viewed by the admin. */
    private static String viewedUser;
    /**currentRoomIndex(Variable): Stores the index of the room currently being viewed. */
    private static String currentRoomIndex;
    public static void main(String[] args) {
        launch();
    }

    @Override
    /**start(Method): Sets up the initial scene of the application to the landing page.
     * @param stage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public void start(Stage stage) throws IOException {
       //Scene scene = new Scene(createContent());
        scene = new Scene(loadFXML("landingPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * setCurrentUser(Method): Sets the username of the currently logged-in user.
     * @param username The username to set as the current user.
     */
    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    /**
     * getCurrentUser(Method): Retrieves the username of the currently logged-in user.
     * @return The username of the current user.
     */
    public static String getCurrentUser() {
        return  currentUser;
    }

    /**
     * setCurrentRoom(Method): Sets the index of the room currently being viewed.
     * @param room The index of the room to set as the current room.
     */
    public static void setCurrentRoom(String room) {
        currentRoomIndex = room;
    }

    /**
     * getCurrentRoom(Method): Retrieves the index of the room currently being viewed.
     * @return The index of the current room.
     */
    public static String getCurrentRoom() {
        return currentRoomIndex;
    }

    /**
     * setViewedAccount(Method): Sets the username of the account currently being viewed by the admin.
     * @param username The username to set as the viewed account.
     */
    public static void setViewedAccount(String username) {
        viewedUser = username;
    }

    /**
     * getViewedAccount(Method): Retrieves the username of the account currently being viewed by the admin.
     * @return The username of the viewed account.
     */
    public static String getViewedAccount() {
        return viewedUser;
    }

    /**
     * setRoot(Method): Changes the root of the scene to the specified FXML file.
     * @param fxml The name of the FXML file (without extension) to load as the new root.
     * @throws IOException If the FXML file cannot be loaded.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**loadFXML(Method): Loads an FXML file and returns its root node.
     * @param fxml The name of the FXML file (without extension) to load.
     * @return The root node of the loaded FXML file.
     * @throws IOException If the FXML file cannot be loaded.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/hotel/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    

}