package hotel.backend;

import java.util.Scanner; 

public class RoomOptions {
    public void RoomOptions(){
        int RoomFloors = 5;
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