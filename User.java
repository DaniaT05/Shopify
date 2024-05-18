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
                String storedEmail = parts[3].trim();
                String storedPassword = parts[4].trim();
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
                if (parts.length >= 4) { // Ensure parts has at least 4 elements
                    String storedEmail = parts[3].trim();
                    if (storedEmail.equals(email)) {
                        found = true;
                        break;
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Users.txt: " + line);
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
            email = input.nextLine();
            System.out.println("Enter password: ");
            password = input.next();
            if (checkCredentials(email,password)) {
                System.out.println("You are signed in");
                loopTerminator = true;
            }
            else {
                System.out.println("Incorrect email or password");
            } 
        }
        while (!loopTerminator);
        input.close();
    }
    public static void signup() {
        Scanner input = new Scanner(System.in);
        String firstName, lastName, email, password,confirmPassword;
        boolean loopTerminator = false;
        do {
            System.out.println("Enter your first name: ");
            firstName = input.next();
            System.out.println("Enter your last name: ");
            lastName = input.next();
            System.out.println("Enter your email address: ");
            email = input.next();
            System.out.println("Enter password: ");
            password = input.next();
            System.out.println("Confirm Password: ");
            confirmPassword = input.next();
            if (!checkEmail(email) && password.equals(confirmPassword)) {
                System.out.println("Account created. Please log in.");
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
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Ecom Project!! Do you want to \n1. Sign Up\n2. Log In\n3. Exit");
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        validInput = true;
                        signup();
                        break;
                    case 2:
                        validInput = true;
                        login();
                        break;
                    case 3:
                        validInput = true;
                        System.out.println("Thank you for using our service!!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}