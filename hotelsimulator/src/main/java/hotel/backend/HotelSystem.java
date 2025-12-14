package hotel.backend;

/**
 * Interface: HotelSystem
 * Date: November 20, 2025
 * Programmers: Mahfuz Ahmed, Michael Garcia
 * 
 * Description:
 * This interface defines the methods for the Hotel management system operations.
 * It specifies all the methods that need to be implemented in the back-end hotel files
 * that manages all the functionality, such as user authentication, account management,
 * payment processing, reservation management, employee managemenet, housekeeping,
 * and manager reporting.
 * 
 * This interface uses no internal data structures as it only defines the method signatures.
 * 
 * This interface follows the interface formatting, allowing for implementation throughout
 * necessary files.
 * This allows many implementations such as our file-based approach without changing any
 * dependent code.
 * 
 * @author Mahfuz Ahmed, Michael Garcia
 * @version 2.0
 */
public interface HotelSystem {

    /**
     * Authenticates a customer login by veryfing information in the customers.txt file.
     * 
     * @param username The username to verify
     * @param password The password to verify
     * @return true if information matches the customer account in the text file, false otherwise.
     */
    boolean Login(String username, String password);


    /**
     * Authenticates an administrator login by verifying information in the admins.txt file.
     * 
     * @param username The administrator username to verify
     * @param password The administrator password to verify
     * @return true if the credentials match an administrator account in admins.txt, false otherwise.
     */
    boolean adminLogin(String username, String password);

     /**
     * Creates a new customer account and saves it to the customer.txt file.
     * 
     * @param username Any username the user chooses
     * @param password Any password the user chooses
     * @param email The email address of the customer
     * @param phone The phone number of the customer
     * @param bankInfo The bank information of the customer
     * @return true if account creation succeeds, false if validation fails
     */
    boolean CreateAccount(String username, String password, String email, String phone, String bankInfo); 

     /**
     * A new administration account is created when an employee creates an account 
     * This administration information is stored in admins.txt file.
     * Email and Phone are used as contact information to reach the administrator if needed.
     * 
     * @param username Any username that the administrator chooses 
     * @param password Any password chosen by the administrator
     * @param email Email the administrator provides 
     * @param phone Phone number the administrator provides 
     * @return true if admin account is successfully created and saved to file, false otherwise
     */
    boolean CreateAdmin(String username, String password, String email, String phone); 
    
    /**
     * Updates an existing customer account with new information.
     * Only non-empty parameters will be updated.
     * Empty or null parameters will keep their existing values.
     * 
     * @param username The username of the account to update (required, cannot be null or empty)
     * @param password The new password (optional, empty string keeps current password)
     * @param email The new email address (optional, empty string keeps current email)
     * @param phone The new phone number (optional, empty string keeps current phone)
     * @param bankInfo The new bank information (optional, empty string keepss current bank info)
     * @return true if the account was successfully updated, false otherwise. 
     */
    boolean updateAccount(String username, String password, String email, String phone, String bankInfo);

        /**
     * Records payment transactions in the payments.txt file
     * 
     * @param customer The username of the customer making the payment
     * @param amount The amount the customer is paying
     * @param method The payment method such as credit, debit, cash, etc.
     * @return true if payment was successful, false otherwise
     */
    boolean Payment(String customer, double amount, String method); 

    /**
     * Creates a reservation and saves it to the reservations.txt file
     * 
     * @param customer The username of the customer making a reservation
     * @param roomNumber The room number to be reserved
     * @return true if reservation was successful, false otherwise
     */
    boolean Reservation(String customer, String roomNumber); 

    /**
     * Processes a room rental transaction that includes a room assignment and a payment.
     * This method finds the room price from the rooms.txt file, then assigns the customer
     * to the room, and also records the payment transaction
     * 
     * @param customer The username of the customer renting the room
     * @param roomNumber The room number to be rented
     * @param floor The floor number where the room is located at
     * @param paymentMethod The payment method such as credit, debit or cash
     * @return true if the room rental and payment were successfully processed, false otherwise
     */
    boolean rentRoomProcess(String customer, String roomNumber, String floor, String paymentMethod);

    /**
     * Authenticates an employee login by verifying informations in employees.txt file.
     * 
     * @param username The username of employee to verify
     * @param password The password of employee to verify
     * @return true if information matches employee account on employees.txt file, false otherwise
     */
    boolean Employee(String username, String password);

    /**
     * Creates a new employee account which asks for username, password, name, and role 
     * Information is saved and stored in employees.txt file 
     * @param username Any username that employee chooses 
     * @param password Password that employee chooses for login
     * @param name Name of the employee logging in 
     * @param role Role of the employee such as housekeeper, receptionist, manager
     * @return true if account is successfully created and saved to file, false otherwise 
     */
    boolean createEmployeeAccount(String username, String password, String name, String role);

    /**
     * Updates the housekeeping status for a room.
     * 
     * @param roomNumber The room number to update
     * @param status The new housekeeping status such as clean, dirty, or maintenance
     * @return true is the status was successfully updated, false otherwise.
     */
    boolean Housekeeping(String roomNumber, String status); 

    /**
     * Saves a manager report to the manager_report.txt file.
     * 
     * @param report The report text to be saved
     */
    void Manager(String report);

    /** 
     * Reports an issue written by customer and saves report to managers_report.txt file. 
     * 
     * @param username The username of the customer reporting the issue 
     * @param issue A description of the issue being reported 
     * @param roomNumber The room number where the customer is staying 
     * @param floor The floor in which the room number and customer are located 
     * @return true if issue report was successfully saved to file, false if not 
     */
    boolean reportIssue(String username, String issue, String roomNumber, String floor);

    /**
     * Assigns an employee to a specific issue based on the issue index.
     * 
     * @param lineIndex the line index in the issues file where the "Assigned Employee" field currently is
     * @param assignedEmployee The employee who is assigned to the issue
     * @return true if the employee was successfully assigned, false otherwise
     */
    boolean assignEmployeeToIssue(int lineIndex, String assignedEmployee);
    
    /**
     * Displays all current reservations by reading from the reservations.txt file.
     * 
     * Uses BufferedReader for line-by-line file reading.
     * Output: Prints all the reservation records to the console/main customer page.
     */
    void viewReservations(); 

    /**
     * Updates the occupancy status of a room in the rooms.txt file.
     * This is the public wrapper method that calls the private updateStatus method.
     * 
     * @param roomNumber The room number to update
     * @param floor The flor number where the room currently is located
     * @param status The new status value, must either be "TRUE" or "FALSE"
     * @return true if the room status was sucessfully updated, false otherwise
     */
    boolean updateRoomStatus(String roomNumber, String floor, String status); 
}