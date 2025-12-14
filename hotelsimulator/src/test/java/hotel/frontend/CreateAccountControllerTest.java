package hotel.frontend;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;

import hotel.backend.Hotel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for Frontend Class Methods 
 * Specifically Testing CreateAccountController Methods 
 * 
 * @author Jose Mendiola, Justin Scott
 */

public class CreateAccountControllerTest {
    @Test
    public void handleAdminButtonTest_False() {
        //shows that account creation fails with null inputs
        String tempNULL = null;
        Hotel hotel = new Hotel();
        assertFalse(hotel.createEmployeeAccount(tempNULL, tempNULL, tempNULL, tempNULL), "Account creation should fail with null inputs");
    }

    @Test
    public void handleAdminButtonTest_True() {
        //shows that account creation succeeds with valid inputs
        boolean result;
        try (FileWriter fw = new FileWriter(Hotel.filePath("employees.txt"), true)) {
            result = true;
        } catch (IOException e) {
            System.out.println("Error writing to employees text file");
            result = false;
        }
        assertTrue(result, "Account creation should succeed with valid inputs");
    }


}
