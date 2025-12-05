package hotel.backend; 

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.BeforeEach; 
import java.io.*; 
import java.nio.file.*; 

public class hoteltest{
    private static Path tempDir; 
    private Hotel hotel; 

    @BeforeAll // creates a temporary directory for test data so real directory isn't affected
    static void setupDir() throws IOException {
        tempDir = Files.createTempDirectory("hotelTestData"); 
        System.setProperty("user.dir", tempDir.toString()); 
        Files.createDirectory(tempDir.resolve("DataFiles")); 
        // matches our filePath() logic 
    }

    @BeforeEach 
    void setUpHotel(){
        hotel = new Hotel(); // new instance of hotel created before each test 
    }
    /**
     * Helper method to create fake instances of files for testing 
     * @param name name of file to create 
     * @param content content that's written to the file 
     */
    private void writeFile(String name, String content) throws IOException{
        Path file = tempDir.resolve("DataFiles").resolve(name); 
        Files.write(file, content.getBytes()); 
    } 

    /**
     * Helper method to read contents of fake file being used for testing 
     * @param name name of file to be read
     */
    private String readFile(String name) throws IOException{
        Path file = tempDir.resolve("DataFiles").resolve(name); 
        return Files.readString(file); 
    } 

    @DisplayName("Checking for Successful Account Creation") 
    @Test 
    public void CreateAccount_Success() throws IOException {
        boolean result = hotel.CreateAccount("John", "practicerun", "john123@gmail.com", 
        "8181234567", "bank"); 
        assertTrue(result); 
        String file = readFile("customers.txt"); 
        assertTrue(file.contains("John,practicerun,john123@gmail.com,8181234567,bank")); 
    }
}