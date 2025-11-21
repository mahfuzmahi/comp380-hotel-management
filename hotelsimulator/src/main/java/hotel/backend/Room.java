package hotel.backend;

import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException;
/** 
 * Establishes the status of a specific room in the hotel which can display 
 * as available or reserved.
 * @author: M. Garcia, M. Ahmed
 * @version: 1.0
 */
public class Room {
    /** 
     * Constructs a Room object which displays available rooms to user from text file in database 
     * @return the current status of available rooms in hotel 
     */
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