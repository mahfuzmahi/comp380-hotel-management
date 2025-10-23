import java.util.Scanner; 
public class Hotel implements HotelSystem{
    public void Login(){
        Scanner Logininput = new Scanner(System.in); 
        System.out.println("Welcome, please log into your account: "); 
        String username = Logininput.nextLine(); 
        String password = Logininput.nextLine();
        // Must combine with Database to verify active account (.txt files) 
    }
    public void Payment(){
        Scanner Paymentinput = new Scanner(System.in); 
        String Credit; 
        String Debit; 
        System.out.println("Please enter your payment method: "); 
        String Paymentmethod = Paymentinput.nextLine(); 
        if (Paymentmethod.equals("Credit")){

        }
        else if (Paymentmethod.equals("Debit")){

        }
        else {
            System.out.println("Invalid payment method, please contact Management.");
        }


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