package hotel.backend; 

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
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
    @Test 
    public void testExample(){
        assertEquals(2,1 + 1); 
    }
}