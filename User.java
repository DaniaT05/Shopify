import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        // Start system
        while (true) {
            System.out.println("Welcome to Ecommerce Management System!");
            System.out.println("Do you want to log in or sign up?");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            
            System.out.println("Enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    login();  
                    break;
                case 2:
                    signup();
                    break;
                case 3:
                    input.close(); 
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }    
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
            if (checkCredentials(email, password)) {
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

    public static boolean checkCredentials(String email, String password) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split(","); //delimiter , to separate values using ,
                if (parts.length > 0) {
                    String storedEmail = parts[3];
                    String storedPassword = parts[4];
                    if (storedEmail.equals(email) && storedPassword.equals(password)) {
                        found = true;
                        break;
                    }
                }
                else
                    continue;
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
    }gg

    public static void signup() {
        Scanner input = new Scanner(System.in);
        int lastUserId = loadLastUserId();
        int userId = lastUserId + 1;
        String fName, lName, userType, email, password,confirmPassword;
        boolean loopTerminator = false;
        do {
            System.out.println("Enter your first name: ");
            fName = input.next();
            System.out.println("Enter your last name: ");
            lName = input.next();
            System.out.println("Are you a buyer or a seller?: ");   
            while (true) {
                System.out.println("Are you a buyer or a seller?: ");
                userType = input.next().toLowerCase();
                if (userType.equals("seller") || userType.equals("buyer")) {
                    break; // Exit the loop if a valid user type is entered
                } else {
                    System.out.println("Invalid user type. Please enter 'seller' or 'buyer'.");
                }
            }
            System.out.println("Enter your email address: ");
            email = input.next();
            System.out.println("Enter password: ");
            password = input.next();
            System.out.println("Confirm Password: ");
            confirmPassword = input.next();
            String userInfo = userId + ", " + userType + ", " + fName + " " + lName + ", " + email + ", " + password;

        // Write to file
        
            if (!checkEmail(email) && password.equals(confirmPassword)) {
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
        while (!loopTerminator);
        input.close();
    }

    public static int loadLastUserId() {
        int lastUserId = 0; // Default value if no file exists or cannot be read
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
        String line;
        boolean firstLineSkipped = false;
        while ((line = reader.readLine()) != null) {
            if (!firstLineSkipped) {
                // Skip the first line
                firstLineSkipped = true;
                continue;
            }
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0].trim());
            if (id > lastUserId) {
                lastUserId = id;
            }
            }
        }
        catch (IOException | NumberFormatException e) {
        System.err.println("Error loading last User ID. Using default value." + e.getMessage());
    }
    return lastUserId;
    }
}