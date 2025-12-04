package hotel.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean updateAccount(String username, String password, String email, String phone, String bankInfo) {
        if(username != null) {
            username = username.trim();
        }

        if(username == null || username.isEmpty()) {
            return false;
        }

        try {
            List<String> lines = new ArrayList<>();
            File customers = new File(filePath("customers.txt"));
            boolean accountFound = false;

            try (BufferedReader r = new BufferedReader(new FileReader(customers))) {
                String l;
                while((l = r.readLine()) != null) {
                    String[] p = l.split(",", 5);
                    
                    if(p.length >= 2) {
                        String currentUsername = p[0].trim();

                        // update the current matching account
                        if(currentUsername.equals(username)) {
                            accountFound = true;

                            // keep current if new ones are not added
                            String newPass;
                            if(password != null && !password.trim().isEmpty()) {
                                newPass = password.trim();
                            } else {
                                newPass = p[1].trim();
                            }

                            String newEmail;
                            if(email != null && !email.trim().isEmpty()) {
                                newEmail = email.trim();
                            } else {
                                if(p.length > 2) {
                                    newEmail = p[2].trim();
                                } else {
                                    newEmail = "";
                                }
                            }

                            String newPhone;
                            if(phone != null && !phone.trim().isEmpty()) {
                                newPhone = phone.trim();
                            } else {
                                if(p.length > 3) {
                                    newPhone = p[3].trim();
                                } else {
                                    newPhone = "";
                                }
                            }

                            String newBankInfo;
                            if(bankInfo != null && !bankInfo.trim().isEmpty()) {
                                newBankInfo = bankInfo.trim();
                            } else {
                                if(p.length > 4) {
                                    newBankInfo = p[4].trim();
                                } else {
                                    newBankInfo = "";
                                }
                            }
                            l = username + "," + newPass + "," + newEmail + "," + newPhone + "," + newBankInfo;
                        }
                    }
                    lines.add(l);
                }
            }

            if(!accountFound) {
                System.out.println("Account not found for the username: " + username);
                return false;
            }

            // write all the lines back again
            try(FileWriter w = new FileWriter(customers)) {
                for(int i = 0; i < lines.size(); i++) {
                    String l = lines.get(i);
                    w.write(l + "\n");
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error updating account information");
            return false;
        }
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
     * Updates housekeeping for a reom and records it in the housekeeping.txt file
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
     * Displays all current reservations by reading from the reservations.txt file.
     * 
     * Uses BufferedReader for line-by-line file reading.
     * Output: Prints all the reservation records to the console/main customer page.
     */

    @Override
    public boolean reportIssue(String username, String issue, String roomNumber, String floor, String assignedEmployee) {
        return true;
    }

    @Override
    public boolean assignEmployeeToIssue(int issueIndex, String assignedEmployee) {
        return true;
    }

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