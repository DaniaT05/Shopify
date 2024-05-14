import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class User {
    public static boolean checkCredentials(String email, String password) {
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); //delimiter , to separate values using ,
                String storedEmail = parts[3];
                String storedPassword = parts[4];

                if (storedEmail.equals(email) && storedPassword.equals(password)) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return found;
    }
    public static void login() {
        Scanner input = new Scanner(System.in);
        String email, password;
        boolean loopTerminator = false;
        do {
            System.out.println("Enter your email address: ");
            email = input.next();
            System.out.println("Enter password: ");
            password = input.next();
            if (checkCredentials(email,password) == true) {
                System.out.println("You are signed in");
                loopTerminator = true;
            }
            else {
                System.out.println("Incorrect email or password");
            }
        }
        while (loopTerminator != true);
    }
    public static void main (String[] args) {
        login();
    }
        
    
}
