package hotel.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Hotel implements HotelSystem {

    @Override
    public boolean CreateAccount(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        String r = username + ", " + password;
        try (FileWriter fw = new FileWriter("DataFiles/customers.txt", true)) {
            fw.write(r + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to customers text file");
            return false;
        }
    }

    @Override
    public void Login(String username, String password) {
        verifyLogin(username, password);
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
    public boolean Payment(String customer, double amount, String method) {
        if(customer == null || customer.isEmpty() || method == null || method.isEmpty()) {
            return false;
        }

        String r = customer + ", " + amount + "," + method;
        try (FileWriter fw = new FileWriter("DataFiles/payments.txt", true)) {
            fw.write(r + '\n');
            return true;
        } catch (IOException e) {
            System.out.println("Error writing in payments file");
            return false;
        }
    }

    @Override
    public boolean Reservation(String customer, String roomNumber) {
        if(customer == null || customer.isEmpty() || roomNumber == null || roomNumber.isEmpty()) {
            return false;
        }

        try (FileWriter fw = new FileWriter("DataFiles/reservations.txt", true)) {
            fw.write(customer + ", " + roomNumber);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error writing in reservation file");
            return false;
        }
    }

    @Override
    public void Employee(){
        System.out.println("Employees: ");
        try (BufferedReader r = new BufferedReader(new FileReader("DataFiles/employees.txt"))) {
            String l;
            while((l = r.readLine()) != null) {
                System.out.println(l);
            }
        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }

    @Override
    public boolean Housekeeping(String roomNumber, String status) {
        if(roomNumber == null || roomNumber.isEmpty() || status == null || status.isEmpty()) {
            return false;
        }

        try (FileWriter fw = new FileWriter("DataFiles/housekeeping.txt", true)) {
            fw.write(roomNumber + ", " + status);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing housekeeping file");
            return false;
        }
    }

    @Override
    public void Manager(String report) {
        if(report == null || report.isEmpty()) {
            return;
        }

        System.out.println("Write Report: "); 

        try (BufferedWriter r = new BufferedWriter(new FileWriter("DataFiles/manager_report.txt", true))) {
            r.write(report); 
            r.newLine();
        } catch (IOException e){
            System.out.println("Error saving Manager Report."); 
        } 
    }

    @Override
    public void viewReservations() {
        System.out.println("Current Resevations:");

        try(BufferedReader r = new BufferedReader(new FileReader("DataFiles/reservations.txt"))) {
            String l;

            while((l = r.readLine()) != null) {
                System.out.println(l);
            }
        } catch (IOException e) {
            System.out.println("Error reading reservations text file");
        }
    }
}