package hotel.backend;

public interface HotelSystem {
    void Login(String username, String password); 
    boolean CreateAccount(String username, String password); 
    boolean Payment(String customer, double amount, String method); 
    boolean Reservation(String customer, String roomNumber); 
    void Employee(); 
    boolean Housekeeping(String roomNumber, String status); 
    void Manager(String report);
    void viewReservations(); 
}