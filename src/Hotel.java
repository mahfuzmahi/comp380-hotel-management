import java.io.*;
import java.util.*;

public class Hotel implements HotelSystem{
    @Override
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

    @Override
    public void Payment(){
        Scanner input = new Scanner(System.in);

        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Amount: ");
        double amount = input.nextDouble();
        input.nextLine();
        System.out.println("Payment method: ");
        String method = input.nextLine();

        String r = name + ", " + amount + "," + method;
        try (FileWriter fw = new FileWriter("DataFiles/payments.txt", true)) {
            fw.write(r + '\n');
            System.out.println("Payment successful");
        } catch (IOException e) {
            System.out.println("Error writing in payments file");
        }
    }

    @Override
    public void Reservation(){

    }

    @Override
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

    @Override
    public void Employee(){

    }

    @Override
    public void Housekeeping(){
    
    }

    public void Manager(){

    }
}