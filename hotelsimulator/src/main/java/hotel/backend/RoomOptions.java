package hotel.backend;

import java.util.Scanner; 
import java.io.*; 

public class RoomOptions {
    public void RoomOptions(){
        int[] RoomFloors = {1,2,3,4,5};
        int[] RoomNumbers = {}; 
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
        System.out.println("Room Types: "); 

    }
    }
public class getRooms {
    public List<String> getRooms(){
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
public class ReservedRooms{
    public List<String> reservedRooms(){ 
        List<String> ReservedRoomList = new ArrayList<>(); // Array List to show rooms Reserved by Users 
        try (BufferedReader re = new BufferedReader(new FileReader("DataFiles/reservations.txt"))){
            String ReservedR; 
            while((ReservedR = re.readLine()) != null) {
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