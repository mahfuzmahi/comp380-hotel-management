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
 * @version 1.0
 */

public interface HotelSystem {
    /**
     * Authenticates a user login attempt.
     * 
     * @param username The username of the user to login
     * @param password The password of the user to login
     * @return true if the username and password match a valid account in customers.txt file, false otherwise
     */
    boolean Login(String username, String password);

    boolean adminLogin(String username, String password);

    /**
     * Creates a new customer account in the system.
     * 
     * @param username Any username of the customer's choice
     * @param password Any password of the customer's choice
     * @return true if the account was successfully created, false otherwise.
     */
    boolean CreateAccount(String username, String password, String email, String phone, String bankInfo); 
    
    boolean updateAccount(String username, String password, String email, String phone, String bankInfo);

    /**
     * Records a payment transaction in the system.
     * 
     * @param customer The username of the customer making the payment
     * @param amount The payment amount 
     * @param method The payment method used such as credit, debit, or cash
     * @return true if the paymenrt was successfully recorded, false otherwise.
     */
    boolean Payment(String customer, double amount, String method); 

    /**
     * Creates a room reservation for the user.
     * 
     * @param customer The username of the customer making the payment
     * @param roomNumber The room number to be reserved
     * @return
     */
    boolean Reservation(String customer, String roomNumber); 

    boolean rentRoomProcess(String customer, String roomNumber, String floor, String paymentMethod);

    /**
     * Authenticates employee login attempt.
     * 
     * @param username The username of the employee attempting to log in
     * @param password The password of the employee attempting to log in
     * @return true is the username and password match a valid account in employees.txt file, false otherwise.
     */
    boolean Employee(String username, String password);

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
     * Saves a manager report in the system.
     * 
     * @param report The text report to be saved
     */
    void Manager(String report);

    boolean reportIssue(String username, String issue, String roomNumber, String floor, String assignedEmployee);
    boolean assignEmployeeToIssue(int issueIndex, String assignedEmployee);
    /**
     * Displays all current reservations in the system.
     * Also outputs reservation information in the console.
     */
    void viewReservations(); 
}