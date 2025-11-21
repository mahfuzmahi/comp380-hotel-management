package hotel.backend;

import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/** 
 * Class: Room
 * Date: November 21, 2025 
 * Description: Establishes the status of rooms in the hotel which can display 
 * as available or reserved.
 * Status of reserved rooms is retrieved and read from a text file of currently
 * reserved rooms. 
 * 
 * Important Data Structures: 
 * File I/O: BufferedReader and FileReader are utilized when reading from lists in text files 
 * ArrayList: Specifically used for reserved rooms. List is updated with current reserved rooms 
 * 
 * @author: Michael Garcia 
 * @version: 1.0
 */
public class Room {
    /** 
     * Constructs a Room object which displays available rooms to user from text file in database 
     * Displays error message if text file cannot be retrieved or read 
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
    /**
     * Reads updated list of reserved rooms from text file
     * Displays error message if text file cannot be read
     * @return list of reserved rooms 
     */
    public List<String> reservedRooms(){ 
        List<String> ReservedRoomList = new ArrayList<>(); // Array List to show rooms Reserved by Users 
        try (BufferedReader re = new BufferedReader(new FileReader("DataFiles/reservations.txt"))){
            String ReservedR; 
            while((ReservedR = re.readLine()) != null) {
                System.out.println("List of Reserved Rooms: "); 
                ReservedRoomList.add(ReservedR); // Adds Reserved Rooms from reservations.txt to Array List 
                System.out.println(ReservedR); 
            }
        }
        catch(IOException e){
            System.out.println("Error reading reservations.txt file");
        }
        return ReservedRoomList; 
        }
} 