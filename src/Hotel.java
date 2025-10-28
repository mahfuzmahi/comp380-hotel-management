import java.io.*;
import java.util.*;

public class Hotel implements HotelSystem{
    public void Login(){
        Scanner input = new Scanner(System.in); 
        System.out.println("Welcome, please log into your account: "); 
        System.out.println("Username: ");
        String username = input.nextLine();
        System.out.println("Password: "); 
        String password = input.nextLine();
        // Must combine with Database to verify active account (.txt files) 

        if(verifyLogin(username, password)) {
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public boolean verifyLogin(String username, String password) {
        try (BufferedReader r = new BufferedReader(new FileReader("DataFiles/customers.txt"))) {
            String line;
            // read from each lines of the file
            while((line = r.readLine()) != null) {
                // split each line using a comma
                String[] d = line.split(",");

                // checks if the username & passwords match
                if(d.length >= 2 && d[0].equals(username) && d[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer file");
        }
        return false;
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