import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public static void main (String[] args) {
    login();
    signup();
}
    

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
    public static boolean checkEmail(String email) {
        boolean found = false;
        boolean firstLineSkipped = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    // Skip the first line
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split(","); //delimiter , to separate values using ,
                String storedEmail = parts[3];
                if (storedEmail.equals(email)) {
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
        input.close();
    }
    public static void signup() {
        Scanner input = new Scanner(System.in);
        String fName, lName, userType, email, password,confirmPassword;
        boolean loopTerminator = false;
        do {
            System.out.println("Enter your first name: ");
            fName = input.next();
            System.out.println("Enter your last name: ");
            lName = input.next();
            System.out.println("Are you a buyer or a seller?: ");
            userType = input.next();
            System.out.println("Enter your email address: ");
            email = input.next();
            System.out.println("Enter password: ");
            password = input.next();
            System.out.println("Confirm Password: ");
            confirmPassword = input.next();
            String userInfo = userId + ", " + userType + ", " + fName + " " + lName + ", " + email + ", " + password;

        // Write to file
        
            if (checkEmail(email) == false && password == confirmPassword) {
                try (PrintWriter writer = new PrintWriter(new FileWriter("Users.txt", true))) {
                    writer.println(userInfo);
                    System.out.println("Account created. Please log in.");
                }
                catch (IOException e) {
                    System.out.println("An error occurred while writing to file.");
                    e.printStackTrace();
                }
                loopTerminator = true;
            }
            else {
                System.out.println("Email already exists!");
                break;
            }
        }
        while (loopTerminator != true);
        input.close();
    }
  
    
}
