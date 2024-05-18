import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User {
    public static final Scanner input = new Scanner(System.in);
    public static void main (String[] args) {
        homePage();
    }
    public static void homePage() {
        int choice;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Ecom Project!! Do you want to \n1. Sign Up\n2. Log In\n3. Exit");
                choice = input.nextInt();
                input.nextLine(); // Consume newline left-over
                switch (choice) {
                    case 1: {
                        validInput = true;
                        if (signup()) {
                            System.out.println("Sign Up Successful!! Now Log in please.");
                        }
                        break;
                    }
                    case 2: {
                        validInput = true;
                        String out = login();
                        if (!out.equals("Invalid")) {
                            String userType = findUserTypeByEmail(out);
                            if (userType.equals("seller")) {
                                sellerHomePage(out);
                            } else if (userType.equals("buyer")) {
                                buyerHomePage(out);
                            } else if (userType.equals("admin")) {
                                adminHomePage(out);
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
    }
    public static void sellerHomePage(String email) {
        System.out.println("Welcome\nWhat would you like to do: ");
        System.out.println("1. View Products\n2. Update Products\n3. Remove a Product\n4. View Orders\n5. View Profile\n5. Log Out");
        // viewProducts();
        // updateProducts();
        // removeProducts();
        // viewOrder();
        // executeOrder();
        // viewProfile();
        // stats();
    }
    public static void buyerHomePage(String email) {
        System.out.println("Welcome!\nWhat would you like to do: ");
        System.out.println("1. View Products\n2. searchProducts\n3. viewProfile\n4. Log Out");
        // viewProducts();
        // searchProducts();
        // placeOrder();
        // viewOrder();
        // viewReceipt();
        // viewProfile();
    }
    public static void displayProductsBySeller(String sellerName) {
        String filePath = "products.txt"; // Path to your text file
        BufferedReader reader = null;
        String line;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                // Splitting the line to extract sellerName
                String[] productInfo = line.split(",");
                if (productInfo.length == 4 && productInfo[0].equals(sellerName)) {
                    // If the sellerName matches, print the product information
                    System.out.println("Seller: " + productInfo[0] +
                            ", Product: " + productInfo[1] +
                            ", Price: " + productInfo[2] +
                            ", Quantity: " + productInfo[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void adminHomePage(String email) {
        System.out.println("Welcome!\nWhat would you like to do: ");
        System.out.println("1. Add Category/ies\n2. Register Sellers\n3. Register Buyers\n4. Search a seller\n5. Search a buyer\n6. View Products\n7. Log Out");
        // addcategories();
        // sellerRegistration();
        // buyerRegistration();
        // searchbuyer();
        // searchSeller();
        // viewProducts();
    }
    
    public static boolean checkCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split(","); //delimiter , to separate values using ,
                if (parts.length >= 5) {
                    String storedEmail = parts[3].trim();
                    String storedPassword = parts[4].trim();
                    if (storedEmail.equals(email) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }    
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    public static boolean checkEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;       
            boolean firstLineSkipped = false;
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
                        return true;
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Users.txt: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }
    public static String findUserTypeByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
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
                        return parts[1].trim();
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Users.txt: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return "error";
    }
    
    public static String login() {
        System.out.println("Enter your email address: ");
        String email = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.next();
        if (checkCredentials(email,password)) {
            System.out.println("You are signed in");
            return email;
        }
        else {
            System.out.println("Incorrect email or password");
            return "Invalid";
        } 
    }
    public static boolean signup() {
        int userTypeInt;
        String firstName, lastName, email, password,confirmPassword;
        String userType = "";
            System.out.println("Enter your first name: ");
            firstName = input.next();
            System.out.println("Enter your last name: ");
            lastName = input.next();
            boolean validUserType = false;
            while (!validUserType) {
                System.out.println("Are you a \n1. Buyer or \n2. Seller? ");
                userTypeInt = input.nextInt();
                input.nextLine(); // Consume newline left-over
                if (userTypeInt == 1) {
                    userType = "buyer";
                    validUserType = true;
                }
                else if (userTypeInt == 2) {
                    userType = "seller";
                    validUserType = true;
                }
                else {
                    System.out.println("Invalid option entered");
                }
            }
            System.out.println("Enter your email address: ");
            email = input.next();
            System.out.println("Enter password: ");
            password = input.next();
            System.out.println("Confirm Password: ");
            confirmPassword = input.next();
            if (!checkEmail(email) && password.equals(confirmPassword)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt", true))) {
                    int newId = getLastId() + 1;
                    String userInfo = newId + ",\t\t\t" + userType + ",\t\t\t" + firstName + ",\t\t\t" + lastName + ",\t\t\t" + email + ",\t\t\t" + password;
                    writer.write(userInfo + "\n");
                    System.out.println("Account created successfully. Please log in.");
                    return true;
                } catch (IOException e) {
                    System.out.println("Error occurred while writing to file: " + e.getMessage());
                    return false;
                }
            } else {
                System.out.println("Email already exists or passwords do not match. Please try again.");
                return false;
            }
        }
    
    public static int getLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            String lastLine = null;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            if (lastLine != null) {
                String[] parts = lastLine.split(",");
                return Integer.parseInt(parts[0].trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return 0; // Default ID if file is empty or not found
    }
}

public static void logOut() {
    System.out.println("Logging out...");
    homePage();
}