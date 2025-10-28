import java.util.*;

public class Hotel implements HotelSystem{
    public void Login(){
        Scanner input = new Scanner(System.in); 
        System.out.println("Welcome, please log into your account: "); 
        String username = input.nextLine(); 
        String password = input.nextLine();
        // Must combine with Database to verify active account (.txt files) 
    }
    public void Payment(){

    }
    public void Reservation(){

    }
    public boolean Rooms(){
        return true; 
    }
    public void Employee(){

    }
    public void Housekeeping(){

    }
    public void Manager(){

    }
}