package hotel.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class: Hotel
 * 
 * Date: November 20, 2025
 * Programmers: Mahfuz Ahmed, Michael Garcia
 * 
 * Description:
 * This is the main implementation class for the hotel management system.
 * It saves and loads information into text files in the DataFiles folder.
 * It handles user/employee login, account management, payment, reservations, 
 * employee tasks, housekeeping updates and manager reports.
 * It follows the HotelSystem interface and manages all reading and writing to the files.
 * 
 * Important Data Structures:
 * File I/O: Uses BufferedReader, BufferedWriter, FileReader, and FileWriter
 * for reading from and writing to text files.
 * String Arrays: Uses String.split() to parse comma-separated values from files
 * 
 * Algorithms:
 * Linear Search: The verifyLoginInFile() method uses linear search through file lines
 * to find matching information. This was chosen because the text files are not sorted.
 * The files are simple and typical for a hotel system usage.
 * File Path: The filePath() method uses a fall back system to find the DataFiles directory
 * alongside the default path. This makes sure that the system works regardless of where the 
 * application is being run from.
 * All methods validate the input parameters such as null and empty string checks and trim() to ignore whitespaces
 * This makes sure that valid data is being passed through. 
 * 
 * @author Mahfuz Ahmed
 * @version 1.0
 */

public class Hotel implements HotelSystem {

    /**
     * Checks the file path for data files in the DataFiles folder/directory.
     * Uses a fallback method to find the directory despite execution context.
     * 
     * The method checks three locations in order:
     * DataFiles subdirectory of current working directory
     * Datafiles directory in the parent of current directory
     * Default path "DataFiles/" as a fallback technique
     * 
     * This makes sure the system works despite where the project is run from
     * the hotelsimulator directort or any other location.
     * 
     * @param fileName the name of the text file that needs to be found
     * @return the exact or relative path to the requested text file
     */

    public static String filePath(String fileName) {
        String current = System.getProperty("user.dir");

        File dataFiles = new File(current, "DataFiles");

        if(dataFiles.isDirectory()) {
            return new File(dataFiles, fileName).getPath();
        }

        File parentFiles = new File(current + "/../DataFiles");

        if(parentFiles.isDirectory()) {
            return new File(parentFiles, fileName).getPath();
        }

        // fallback
        return "DataFiles/" + fileName;
    }

    /**
     * Creates a new customer account and saves it to the customer.txt file
     * 
     * @param username Any username the user choses
     * @param password Any password the user choses
     * @return true if account creation succeeds, false if validation fails
     */
    @Override
    public boolean CreateAccount(String username, String password, String email, String phone, String bankInfo) {
        if(username != null) {
            username = username.trim();
        }

        if(password != null) {
            password = password.trim();
        }

        if(email != null) {
            email = email.trim();
        }

        if(phone != null) {
            phone = phone.trim();
        }

        if(bankInfo != null) {
            bankInfo = bankInfo.trim();
        }

        if(username == null || username.isEmpty() || password == null || password.isEmpty() || 
            email == null || email.isEmpty() || phone == null || phone.isEmpty() || bankInfo == null || bankInfo.isEmpty()) {
            return false;
        }

        String r = username + "," + password + "," + email + "," + phone + "," + bankInfo;
        try (FileWriter fw = new FileWriter(filePath("customers.txt"), true)) {
            fw.write(r + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to customers text file");
            return false;
        }
    }

    /**
     * A new administration account is created when an employee creates an account 
     * This administration information is stored in admins.txt file
     * @param username Any username that the administrator chooses 
     * @param password Any password chosen by the administrator
     * @param email Email the administrator provides 
     * @param phone Phone number the administrator provides 
     * Email and Phone are used as contact information to reach the administrator if needed
     * @return true if admin account is successfully created and saved to file, false otherwise
     */
    public boolean CreateAdmin(String username, String password, String email, String phone){
        if (username != null){
            username = username.trim();
        }
        if (password != null){
            password = password.trim(); 
        }
        if (email != null){
            email = email.trim(); 
        }
        if (phone != null){
            phone = phone.trim(); 
        }
        try (BufferedWriter aw = new BufferedWriter(new FileWriter(filePath("admins.txt"), true))){
            String AdminInfo = username + ", " + password + ", " + email + ", " + phone; 
            aw.write(AdminInfo + "\n"); 
            return true; 
        }
        catch (IOException e){
            System.out.println("Error writing admin information to file. "); 
            return false; 
        }
    }


    public boolean updateAccount(String username, String password, String email, String phone, String bankInfo) {
        return true;
    }
    /**
     * Authenticates a customer login by veryfing information in the customers.txt file
     * 
     * @param username The username to verify
     * @param password The password to verify
     * @return true if information matches the customer account in the text file, false otherwise
     */

    @Override
    public boolean Login(String username, String password) {
        return verifyCustomer(username, password);
    }

    @Override
    public boolean adminLogin(String employeeId, String password) {
        return verifyAdmin(password, password);
    }

    /**
     * Verifies login information by searching through a specific file
     * 
     * @param username The username to verify
     * @param password The password to verify
     * @param fileName The name of the file ot search
     * @return true if matching information is found, false otherwise
     */

    public boolean verifyLoginInFile(String username, String password, String fileName) {
        if(username != null) {
            username = username.trim();
        }

        if(password != null) {
            password = password.trim();
        }

        try (BufferedReader r = new BufferedReader(new FileReader(filePath(fileName)))) {
            String line;
            // read from each lines of the file
            while((line = r.readLine()) != null) {
                // split each line using a comma
                String[] d = line.split(",");

                // checks if the username & passwords match
                if(d.length >= 2 && d[0].trim().equals(username) && d[1].trim().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer file");
        }
        return false;
    }

    /**
     * Verifies customer login information
     * 
     * @param username The customer username to verify
     * @param password The customer password to verify
     * @return true if information match a customer account in customers.txt, false otherwise
     */

    public boolean verifyCustomer(String username, String password) {
        return verifyLoginInFile(username, password, "customers.txt");
    }

    /**
     * Verifies employee login information
     * 
     * @param username The employee username to verify
     * @param password The employee password to verify
     * @return true if infomration match an employee account in employees.txt, false otherwise
     */

    public boolean verifyEmployee(String username, String password) {
        return verifyLoginInFile(username, password, "employees.txt");
    }

    public boolean verifyAdmin(String username, String password) {
        return verifyLoginInFile(username, password, "admins.txt");
    }

    /**
     * Records payment transactions in the payments.txt file
     * 
     * @param customer The username of the customer making the payment
     * @param amount The amount the customer is paying
     * @param method The payment method such as credit, debit, cash, etc.
     * @return true if payment was successful, false otherwise
     */

    @Override
    public boolean Payment(String customer, double amount, String method) {
        if(customer == null || customer.isEmpty() || method == null || method.isEmpty()) {
            return false;
        }

        String r = customer + ", " + amount + "," + method;
        try (FileWriter fw = new FileWriter(filePath("payments.txt"), true)) {
            fw.write(r + '\n');
            return true;
        } catch (IOException e) {
            System.out.println("Error writing in payments file");
            return false;
        }
    }

    /**
     * Creates a reservation and saves it to the reservations.txt file
     * 
     * @param customer The username of the customer making a reservation
     * @param roomNumber The room number to be reserved
     * @return true if reservation was successful, false otherwise
     */

    @Override
    public boolean Reservation(String customer, String roomNumber) {
        if(customer == null || customer.isEmpty() || roomNumber == null || roomNumber.isEmpty()) {
            return false;
        }

        try (FileWriter fw = new FileWriter(filePath("reservations.txt"), true)) {
            fw.write(customer + ", " + roomNumber);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error writing in reservation file");
            return false;
        }
    }

    @Override
    public boolean rentRoom(String customer, String roomNumber, String floor, String paymentMethod) {
        if(customer == null || customer.isEmpty() || roomNumber == null || roomNumber.isEmpty() || 
            floor == null || floor.isEmpty() || paymentMethod == null || paymentMethod.isEmpty()) {
                return false;
            }
        
        double base = 100.0;

        try {
            int floorNo = Integer.parseInt(floor.trim());
            double roomPrice = base + (floorNo * 20.0);

            /*
            if(!Reservation(customer, roomNumber)) {
                return false;
            }

            if(!Payment(customer, roomPrice, paymentMethod)) {
                return false;
            }
            */
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid floor number");
            return false;
        }
    }

    /**
     * Authenticates an employee login by verifying informations in employees.txt file
     * 
     * @param username The username of employee to verify
     * @param password The password of employee to verify
     * @return true if information matches employee account on employee.txt file, false otherwise
     */

    @Override
    public boolean Employee(String username, String password){
        return verifyEmployee(username, password);
    }
  
    /**
     * Creates a new employee account which asks for username, password, name, and role 
     * Information is saved and stored in employees.txt file 
     * @param username Any username that employee chooses 
     * @param password Password that employee chooses for login
     * @param name Name of the employee logging in 
     * @param role Role of the employee such as housekeeper, receptionist, manager
     * @return true if account is successfully created and saved to file, false otherwise 
     */
    @Override
    public boolean createEmployeeAccount(String username, String password, String name, String role) {
        if(username != null) {
            username = username.trim();
        }

        if(password != null) {
            password = password.trim();
        }

        if(name != null) {
            name = name.trim();
        }

        if(role != null) {
            role = role.trim();
        }

        if(username == null || username.isEmpty() || password == null || password.isEmpty() ||
           name == null || name.isEmpty() || role == null || role.isEmpty()) {
            return false;
        }

        String r = username + "," + password + "," + name + "," + role;
        try (FileWriter fw = new FileWriter(filePath("employees.txt"), true)) {
            fw.write(r + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to employees text file");
            return false;
        }
    }

    /**
     * Updates housekeeping for a room and records it in the housekeeping.txt file
     * 
     * @param roomNumber the room needed to be updated
     * @param status the new houskeeping status such as clean, dirty, maintenence, etc.
     * @return true if status was successfully updated, false otherwise
     */

    @Override
    public boolean Housekeeping(String roomNumber, String status) {
        if(roomNumber == null || roomNumber.isEmpty() || status == null || status.isEmpty()) {
            return false;
        }

        try (FileWriter fw = new FileWriter(filePath("housekeeping.txt"), true)) {
            fw.write(roomNumber + ", " + status);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing housekeeping file");
            return false;
        }
    }

    /**
     * Saves a manager report to the manager_report.txt file.
     * 
     * @param report The report text to be saved
     */

    @Override
    public void Manager(String report) {
        if(report == null || report.isEmpty()) {
            return;
        }

        System.out.println("Write Report: "); 

        try (BufferedWriter r = new BufferedWriter(new FileWriter(filePath("manager_report.txt"), true))) {
            r.write(report); 
            r.newLine();
        } catch (IOException e){
            System.out.println("Error saving Manager Report."); 
        } 
    }

    /** 
     * Reports an issue written by customer and saves report to issues.txt file. 
     * @param username The username of the customer reporting the issue 
     * @param issue A description of the issue being reported 
     * @param roomNumber The room number where the customer is staying 
     * @param floor The floor in which the room number and customer are located 
     * @return true if issue report was successfully saved to file, false if not 
     * 
     */

    @Override
    public boolean reportIssue(String username, String issue, String roomNumber, String floor) {
        if (username == null || username.isEmpty() || issue == null || issue.isEmpty() || 
            roomNumber == null || roomNumber.isEmpty() || floor == null || floor.isEmpty()) {
                return false; 
            }
            int IssueID = generateIssueID(); 
        try (BufferedWriter reportissue = new BufferedWriter(new FileWriter(filePath("issues.txt"), true))){
            reportissue.write("IssueID: " + IssueID);
            reportissue.write("Username: " + username + ", " + " Room Number: " + roomNumber + ", " + " Floor: " + floor); 
            reportissue.newLine(); 
            reportissue.write("Issue: " + issue); 
            reportissue.newLine(); 
            reportissue.write("Assigned Employee: None"); 
            reportissue.newLine();
            return true; 
        }
        catch (IOException e){
            System.out.println("Error reporting and saving issue to file."); 
            return false;
        }
    }

    /**
     * Assigns an employee to a specific issue based on the issue index.
     * @param assignedEmployee The employee who is assigned to the issue
     * @return true if the employee was successfully assigned, false otherwise
     */

    @Override
    public boolean assignEmployeeToIssue(int IssueID, String assignedEmployee) {
        if (assignedEmployee == null || assignedEmployee.isEmpty()) {
            return false; 
        }
        File file = new File(filePath("issues.txt")); 
        try (BufferedReader rf = new BufferedReader(new FileReader(file))){
            return true; 
        }
        catch (IOException e){
            System.out.println("Error assigning employee to issue."); 
            return false;
        }
    }

    /**
     * Displays all current reservations by reading from the reservations.txt file.
     * 
     * Uses BufferedReader for line-by-line file reading.
     * Output: Prints all the reservation records to the console/main customer page.
     */

    @Override
    public void viewReservations() {
        System.out.println("Current Resevations:");

        try(BufferedReader r = new BufferedReader(new FileReader(filePath("reservations.txt")))) {
            String l;

            while((l = r.readLine()) != null) {
                System.out.println(l);
            }
        } catch (IOException e) {
            System.out.println("Error reading reservations text file");
        }
    }
}