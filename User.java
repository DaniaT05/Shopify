import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    public static void main (String[] args) {
        homePage();
    }
    public static void homePage() {
        Scanner input = new Scanner(System.in);
        int choice;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Ecom Project!! Do you want to \n1. Sign Up\n2. Log In\n3. Exit");
                choice = input.nextInt();
                switch (choice) {
                    case 1: {
                        validInput = true;
                        if (signup()) {
                            System.out.println("Sign Up Successful!!");
                            System.out.println("Now Log in please.");
                        }
                        break;
                    }
                    case 2: {
                        validInput = true;
                        String out = login();
                        if (out != "Invalid") {
                            if (findUserTypeByEmail(out) == "seller") {
                                sellerHomePage();
                            }
                            else if (findUserTypeByEmail(out) == "buyer") {
                                buyerHomePage();
                            }
                            if (findUserTypeByEmail(out) == "admin") {
                                adminHomePage();
                            }
                        }
                        else {
                            System.out.println("Would you like to: \n1. Sign Up \n2. Log In");
                        }
                        break;
                    }
                    case 3: {
                        validInput = true;
                        System.out.println("Thank you for using our service!!");
                        System.exit(0);
                        break;
                    }
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: Invalid Input");                
                input.next();
            }
        }
        input.close();
    }
    public static void sellerHomePage() {

    }
    public static void buyerHomePage() {
        
    }
    public static void adminHomePage() {
        
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
    }
    public static String findUserTypeByEmail(String email) {
        boolean firstLineSkipped = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line, userType;
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
                        userType = parts[1];
                        return userType;
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Users.txt: " + line);
                    return "error";
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return "error";
    }
    
    public static String login() {
        Scanner input = new Scanner(System.in);
        String email, password;
        System.out.println("Enter your email address: ");
        email = input.nextLine();
        System.out.println("Enter password: ");
        password = input.next();
        if (checkCredentials(email,password)) {
            System.out.println("You are signed in");
            input.close();
            return email;
        }
        else {
            System.out.println("Incorrect email or password");
            input.close();
            return "Invalid";
        } 
    }
    public static boolean signup() {
        Scanner input = new Scanner(System.in);
        int userTypeInt;
        String firstName, lastName, userType, email, password,confirmPassword;
            System.out.println("Enter your first name: ");
            firstName = input.next();
            System.out.println("Enter your last name: ");
            lastName = input.next();
            while (userTypeGiven)
                System.out.println("Are you a \n1. Buyer or \n2. Seller? ");
                userTypeInt = input.nextInt();
                if (userTypeInt == 1) {
                    userType = "buyer";
                }
                else if (userTypeInt == 2) {
                    userType = "seller";
                }
                else {
                    System.out.println("Invalid option entered");
                }
            System.out.println("Enter your email address: ");
            email = input.next();
            System.out.println("Enter password: ");
            password = input.next();
            System.out.println("Confirm Password: ");
            confirmPassword = input.next();
        
            if (!checkEmail(email) && password.equals(confirmPassword)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt", true)); BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
                    String line;
                    String lastLine = null;
                    while ((line = reader.readLine()) != null) {
                        lastLine = line; // Store the last line read
                    }
                    int lastId = 0;
                    if (lastLine != null) {
                        String[] parts = lastLine.split(",");
                        lastId = Integer.parseInt(parts[0]); // Extract the last ID
                    }
                    int newId = lastId + 1; // Increment the last ID to get the new ID
                    String userInfo = newId + ",\t\t\t" + userType + ",\t\t\t" + firstName + ",\t\t\t" + lastName + ",\t\t\t" + email + ",\t\t\t" + password;
                    writer.write(userInfo + "\n");
                    System.out.println("Account created successfully. Please log in.");
                    input.close();
                    return true;
                } 
                catch (IOException e) {
                    System.out.println("Error occurred while writing to file: " + e.getMessage());
                    input.close();
                    return false;
                }
            }
            else {
                System.out.println("Email already exists or passwords do not match. Please try again.");
                input.close();
                return false;
            }
        
    }
    
}