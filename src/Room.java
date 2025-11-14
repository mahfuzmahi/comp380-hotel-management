import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException;

public class Room {
    public boolean Rooms(){
        System.out.println("Rooms available: ");
        try (BufferedReader r = new BufferedReader(new FileReader("DataFiles/rooms.txt"))) {
            String l;

            while((l = r.readLine()) != null) {
                System.out.println(l);
            }
        } catch (IOException e) {
            System.out.println("Error reading rooms text file");
        }
        return true;
    }
} 