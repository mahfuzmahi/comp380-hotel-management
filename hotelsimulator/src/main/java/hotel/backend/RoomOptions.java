package hotel.backend;

import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/** 
 * Class Name: RoomOptions
 * Date of Code: November 20, 2025
 * Programmer: Michael Garcia
 * Description: Hotel has a set number of floors and rooms per floor
 * The different room options that are shown via an array list taken from a 
 * text file whiich is read and written to. 
 * 
 * Important Data Structures: 
 * File I/O: BufferedReader and FileReader used to read from lists shown in 
 * text files 
 * ArrayList: Used to store and return the list of room types available in hotel 
 * read from file 
 * 
 * 
 * @author: Michael Garcia
 * @version: 1.0
 */
public class RoomOptions{
    /** 
     * Holds a fixed list for both room floors and room numbers not seen by user
     * Requests for user input to select room floor 
     * Once floor is selected and input is received, more information regarding 
     * rooms on that floor will be displayed
     * 
     */

    public void RoomOptions(){
        int[] RoomFloors = {1,2,3,4,5};
        int[] RoomNumbers = {1,2,3,4,5,6,7,8,9,10}; 
        Scanner input = new Scanner(System.in); 
        System.out.println("Select Room Floor: "); 
        int FloorChoice = input.nextInt(); 
        switch(FloorChoice){
            case 1: 
                System.out.println("Rooms on Floor 1: "); 
                // Add more about Reserved Rooms Later
                break; 
            case 2: 
                System.out.println("Rooms on Floor 2: "); 
                // Add more about Reserved Rooms Later
                break; 
            case 3: 
                System.out.println("Rooms on Floor 3: "); 
                // Add more about Reserved Rooms Later
                break; 
            case 4: 
                System.out.println("Rooms on Floor 4: "); 
                // Add more about Reserved Rooms Later
                break; 
            case 5: 
                System.out.println("Rooms on Floor 5: "); 
                // Add more about Reserved Rooms Later
                break; 
            default: 
            System.out.println("Invalid Floor. Choose Again."); 
        }
    }
    /**
     * Reads and updates room types from text file and displays options to user
     * Each line in the text file represents a different room type 
     * If file cannot be read, an error message is displayed 
     * @return list of string representing room types in Hotel 
     */

    public List<String> RoomTypes(){
    List<String> DisplayRoom = new ArrayList<>(); // Array List to show rooms offered by Hotel 
    System.out.println("Offered Room Types in Hotel: "); 
    try (BufferedReader r = new BufferedReader(new FileReader("DataFiles/rooms.txt"))){
        String Rtypes; 
        while((Rtypes = r.readLine()) != null){
            DisplayRoom.add(Rtypes); // Adds Room Types from rooms.txt to Array List
            System.out.println(Rtypes); 
        }
    }
    catch (IOException e){
        System.out.println("Error reading rooms.txt file"); 
    }
    return DisplayRoom; 
    }
    }