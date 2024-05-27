import java.io.*;
import java.util.*;
/*signUp
  logIn -> checkCredentials -> emailExists
*/
public class User {
    public static void main(String[] args) {
        homePage();
    }
    public static final Scanner input = new Scanner(System.in);
    public static void homePage() {
        int choice=0;
        boolean validInput = false;
        while (!validInput) {
        while(choice!=3){
            try {
                System.out.println("Ecom Project!! Do you want to \n1. Sign Up\n2. Log In\n3. Exit");
                choice = input.nextInt();
                input.nextLine(); // Consume newline left-over
                switch (choice) {
                    case 1: {
                        validInput = true;
                        if (signUp()) {
                            System.out.println("Sign Up Successful!! Now Log in please.");
                        }
                        break;
                    }
                    case 2: {
                        validInput = true;
                        String email = logIn();
                        //logIn() returns email. if email is invalid, it will ask again
                        if (!email.equals("Invalid")) {
                            String userType = findUserTypeByEmail(email);
                            
                            System.out.println(userType);
                            if (userType.equals("seller")) {
                                sellerHomePage(email);
                            } else if (userType.equals("admin")) {
                                adminHomePage(email);
                            } else {
                                buyerHomePage(email);
                            }
                        }
                        else {
                            System.out.println("Would you like to: \n1. Sign Up \n2. Log In");
                        }
                        break;
                    }
                    case 3: {
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
      
    }
    public static void buyerHomePage(String email) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome!\nWhat would you like to do: ");
            System.out.println("1. View Categories\n2. Search Products\n3. View Products\n4. View Profile\n5. View Order Status\n6. View Cart\n7. Log Out");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline left-over
            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                   viewProducts();
                    break;
                case 4:
                    viewProfile(email);
                    break;
                case 5:
                    viewOrderStatus(email);
                    break;
                case 6:
                    viewCart(email);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
    public static void sellerHomePage(String email) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome Seller! What would you like to do:");
            System.out.println("1. View Products\n2. Update Products\n3. Remove a Product\n4. View Orders\n5. Execute Order\n6. View Profile\n7. View Stats\n8. Log Out");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline left-over
            switch (choice) {
                case 1:
                    viewSellerProducts(email);
                    break;
                case 2:{
                    boolean validProduct = false;
                    int productID = 0;
                    System.out.println("Enter the Product ID of the product you want to update: ");
                    while (!validProduct) {
                        try {
                            productID = input.nextInt();
                            validProduct = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer for the Product ID.");
                            input.nextLine(); // consume the invalid input
                        }
                    }
                    input.nextLine(); // consume the newline
            
                    System.out.println("Enter new Product Name: ");
                    String newProductName = input.nextLine();
                    update(productID, newProductName, "Products.txt", "Product");
                    break;
                }
                case 3:
                   removeProducts();
                    break;
                case 4:
                    viewOrders(email);
                    break;
                case 5:
                    // executeOrder();
                    break;
                case 6:
                    viewProfile(email);
                    break;
                case 7:
                    // viewStats(email);
                    break;
                case 8:
                    exit = true;
                    logOut();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                    break;
            }
        }
    }

    public static void adminHomePage(String email) {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("Welcome Admin! What would you like to do:");
                System.out.println("1. Add Categories\n2. View Categories\n3. Update Categories\n4. Remove Categories\n5. Approve Sellers\n6. Search a user (buyer/seller)\n7. Remove User\n8. View Products\n9. View Profile\n10. Log Out");
                int choice = input.nextInt();
                input.nextLine(); // Consume newline left-over
                switch (choice) {
                    case 1:
                        addCategories();
                        break;
                    case 2:
                        viewCategories();
                        break;
                    case 3: {
                        viewCategories();
                        boolean validCategory = false;
                        int categoryID = 0;
                        System.out.println("Enter the Category ID of the category you want to update: ");
                        while (!validCategory) {
                            try {
                                categoryID = input.nextInt();
                                validCategory = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer for the Category ID.");
                                input.nextLine(); // consume the invalid input
                            }
                        }
                        input.nextLine(); // consume the newline
                
                        System.out.println("Enter new Category Name: ");
                        String newCategoryName = input.nextLine();
                        update(categoryID, newCategoryName, "Category.txt", "Category");
                        break;
                    }
                    case 4:
                        try {
                        System.out.println("Enter the ID of category you want to remove: ");
                        int categoryID = input.nextInt();
                        remove(categoryID, "Category.txt", "Category");
                        }
                        catch (InputMismatchException ex) {
                            System.out.println("Invalid Input");
                        }
                        break;
                    case 5:
                        approveSellers();
                        break;
                    case 6:
                        searchUsers();
                        break;
                    case 7:
                        removeUser();
                        break;
                    case 8:
                        viewProducts();
                        break;
                    case 9:
                        viewProfile(email);
                        break;
                    case 10:
                        exit = true;
                        logOut();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 10.");
                        break;
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("Invalid Input");
            }
        }
    }    
    public static String findUserTypeByEmail(String email) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Users.txt")); 
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    // Skip the first line
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|"); 
                if (parts.length >= 5) { // Ensure parts has at least 5 elements
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
    //logIn() -> checkCredentials() -> emailExists()
    public static String logIn() {
        System.out.println("Enter your email address: ");
        String email = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.next();
        input.nextLine(); // Consume newline
        if (checkCredentials(email,password)) {
            System.out.println("You are signed in");
            return email;
        }
        else {
            System.out.println("Incorrect email or password");
            return "Invalid";
        } 
    }
    public static boolean signUp() { //uses getLastID() emailExists()
        int newId = getLastId("Users.txt") + 1;
        int userTypeInt;
        String firstName, lastName, email, password,confirmPassword, address;
        String userType = "";
        System.out.println("Enter your first name: ");
        firstName = input.next();
        System.out.println("Enter your last name: ");
        lastName = input.next();
        boolean validUserType = false;
        while (!validUserType) {
            try {
                System.out.println("Are you a \n1. Buyer or \n2. Seller? ");
                userTypeInt = input.nextInt();
                input.nextLine(); // Consume newline left-over
                switch (userTypeInt) {
                    case 1:
                        userType = "buyer";
                        validUserType = true;
                        break;
                    case 2:
                        userType = "seller";
                        validUserType = true;
                        break;
                    default:
                        System.out.println("Invalid option entered");
                        break;
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("Invalid Input.");
                input.nextLine();
            }
        }
        System.out.println("Enter your email address: ");
        email = input.next();
        System.out.println("Enter password: ");
        password = input.next();
        System.out.println("Confirm Password: ");
        confirmPassword = input.next();
        input.nextLine(); // Consume newline
        System.out.println("Enter your address: ");
        address = input.nextLine();
        //checking if email already exists
        if (!emailExists(email) && password.equals(confirmPassword)) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("Users.txt", true));
                if (userType.equals("buyer")) {
                String userInfo =newId + "|\t" + userType + "|\t" + firstName + " " + lastName + "|\t" + email + "|\t" + password + "|\t" + address +"|";
                System.out.println(userInfo);
               
                writer.write( userInfo + "\n");
                System.out.println("Account created successfully. Please log in.");
                return true;
                }
                else { //for sellers
                    String userInfo = newId + "|\t" + userType + "|\t" + firstName + " " + lastName + "|\t" + email + "|\t" + password + "|\t" + address + "|\t" + "pending";
                    writer.write(userInfo + "\n");

                    System.out.println("Account pending admin's approval.");
                    return true;
                } 
            } catch (IOException e) {
                System.out.println("Exception:"+e.getMessage());
                return false;
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    System.out.println("Exception:"+e.getMessage());
                    return false;
                }
            }
        } else {
            System.out.println("Email already exists or passwords do not match. Please try again.");
            return false;
        }
    }
    //Helper Functions for Login
    public static boolean checkCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|"); //delimiter , to separate values using ,
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
    public static boolean emailExists(String email) { //uses no other function
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));
            String line;       
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    // Skip the first line
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|"); //delimiter , to separate values using ,
                if (parts.length >= 5) { //Ensuring parts has at least 5 elements
                    String storedEmail = parts[3].trim();
                    if (storedEmail.equals(email)) {
                        return true;
                    }
                } else {
                    System.err.println("Invalid format in Users.txt: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }
    public static void logOut() {
        System.out.println("Logging out...");
        homePage();
    }
    //Helper Fuctions for SignUp
    public static int getLastId(String fileName) {
        String lastLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            // reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        if (lastLine != null) {
            String[] parts = lastLine.split("\\|");
            try {
                return Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                System.err.println("Error parsing integer: " + e.getMessage());
            }
        }

        return -1; // Return a default value or handle as appropriate
    }
    //View Profile // Buyer Home Page Functions
    public static void viewProfile(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String storedEmail = parts[4].trim();
                    if (storedEmail.equals(email)) {
                        System.out.println("Profile Information:");
                        System.out.println("First Name: " + parts[2].trim());
                        System.out.println("Last Name: " + parts[3].trim());
                        System.out.println("Email: " + parts[4].trim());
                        System.out.println("Address: " + parts[6].trim());
                        break;
                    }
                } else {
                    System.err.println("Invalid format in Users.txt: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewOrderStatus(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Orders.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String buyerEmail = parts[1].trim();
                    if (buyerEmail.equals(email)) {
                        System.out.println("Order ID: " + parts[0].trim());
                        System.out.println("Price: " + parts[3].trim());
                        System.out.println("Status: " + parts[4].trim());
                        System.out.println("Order Placed: " + parts[5].trim());
                        System.out.println("Execution Date: " + parts[6].trim());
                        System.out.println("Address: " + parts[7].trim());
                        System.out.println("--------------------------");
                    }
                } else {
                    System.err.println("Invalid format in Orders.txt: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewCategories() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Category.txt")); 
            String line;
            int count = 0;
            int categoryID = 1000;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    // Skip the first line
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                System.out.println("Category ID: " + parts[0].trim() + ", Category Name: " + parts[1].trim());
                count++;
            } 
        //         try {
        //             while (!(categoryID <= count)) {
        //                 System.out.println("Enter Category ID to view its products: ");
        //                 categoryID = input.nextInt();
        //             }
        //             viewProductsByCategories(categoryID);
        //         }
        //         catch (InputMismatchException ex) {
        //             System.out.println("Incorrect Input. Please enter a number.");
        //         }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewCart(String email) {
        // View, place, update, and cancel orders in the cart
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Cart.txt")); 
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].trim().equals(email)) {
                    System.out.println("Product ID: " + parts[1]);
                    System.out.println("Quantity: " + parts[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void addCategories() {
        System.out.println("Enter category name: ");
        String categoryName = input.nextLine();
        try {
            int newId = getLastId("Category.txt") + 1;
            String categoryInfo = newId + "| " + categoryName;
            BufferedWriter writer = new BufferedWriter(new FileWriter("Category.txt", true)); 
            writer.write(categoryInfo + "\n");
            writer.close();
            
            System.out.println("Category added successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing to file: " + e.getMessage());
        }
    }
    public static void searchProducts() {
        System.out.println("Enter product name to search: ");
        String productName = input.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].trim().equalsIgnoreCase(productName)) {
                    System.out.println("Product found:");
                    System.out.println("Product ID: " + parts[0].trim());
                    System.out.println("Name: " + parts[1].trim());
                    System.out.println("Price: " + parts[2].trim());
                    System.out.println("Seller ID: " + parts[3].trim());
                    System.out.println("Category ID: " + parts[4].trim());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Product not found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewProducts() {
        // Product_ID | Name | Price | Seller_ID | Category_ID
        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            System.out.println("Available Products:");
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                System.out.println("Product ID: " + parts[0]);
                System.out.println("Name: " + parts[1]);
                System.out.println("Price: $" + parts[2]);
                System.out.println("Seller ID: " + parts[3]);
                System.out.println("Category ID: " + parts[4]);
                System.out.println("---------------------------------");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    public static void viewSellerProducts(String email) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Products.txt"));
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    String sellerEmail = parts[3].trim(); // Assuming seller's email is stored at index 3
                    if (sellerEmail.equals(email)) {
                        System.out.println("Product ID: " + parts[0].trim());
                        System.out.println("Name: " + parts[1].trim());
                        System.out.println("Price: " + parts[2].trim());
                        System.out.println("Category ID: " + parts[4].trim());
                        System.out.println("--------------------------");
                    }
                } else {
                    System.err.println("Invalid format in Products.txt: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewOrders(String email) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Orders.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(email)) {
                    System.out.println(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
    }
    public static void removeUser() {
        System.out.println("Enter email to remove: ");
        String emailToRemove = input.next();
        File originalFile = new File("Users.txt");
        File tempFile = new File("tempUsers.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the email to be removed
                if (line.contains(emailToRemove)) {
                    userFound = true;
                    continue; // Skip this line
                }
                // Write the line to the temporary file
                writer.write(line + System.lineSeparator());
            }

            if (!userFound) {
                System.out.println("User not found.");
                return;
            }

        } catch (IOException e) {
            System.err.println("Error removing user: " + e.getMessage());
            return;
        }

        // Close the files and delete/rename them
        boolean deleteSuccess = originalFile.delete();
        if (!deleteSuccess) {
            System.err.println("Failed to delete original file.");
            return;
        }
        
        boolean renameSuccess = tempFile.renameTo(originalFile);
        if (!renameSuccess) {
            System.err.println("Failed to rename temporary file.");
            return;
        }

        System.out.println("User removed successfully.");
    }
    public static void updateProducts() {
        System.out.println("Enter the Product ID of the product you want to update:");
        int productId = input.nextInt();
        input.nextLine(); // Consume the newline
    
        File originalFile = new File("Products.txt");
        File tempFile = new File("tempProducts.txt");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
            String line;
            boolean productFound = false;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Assuming the delimiter is "\\|"
                if (parts.length >= 5) {
                    int currentProductId = Integer.parseInt(parts[0].trim());
                    if (currentProductId == productId) {
                        productFound = true;
    
                        // Prompt the seller for new product details
                        System.out.println("Enter new product name:");
                        String newName = input.nextLine();
                        System.out.println("Enter new price:");
                        double newPrice = input.nextDouble();
                        System.out.println("Enter new seller ID:");
                        int newSellerId = input.nextInt();
                        System.out.println("Enter new category ID:");
                        int newCategoryId = input.nextInt();
                        input.nextLine(); // Consume the newline
    
                        // Write the updated product details to the temporary file
                        writer.write(currentProductId + "\\|" + newName + "\\|" + newPrice + "\\|" + newSellerId + "\\|" + newCategoryId + System.lineSeparator());
                    } else {
                        // Write the existing product details to the temporary file
                        writer.write(line + System.lineSeparator());
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Products.txt: " + line);
                    writer.write(line + System.lineSeparator());
                }
            }
    
            if (!productFound) {
                System.out.println("Product not found.");
            }
    
        } catch (IOException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return;
        }
    
        // Close the files and delete/rename them
        boolean deleteSuccess = originalFile.delete();
        if (!deleteSuccess) {
            System.err.println("Failed to delete original file.");
            return;
        }
    
        boolean renameSuccess = tempFile.renameTo(originalFile);
        if (!renameSuccess) {
            System.err.println("Failed to rename temporary file.");
            return;
        }
    
        System.out.println("Product updated successfully.");
    }
    public static void removeProducts() {
        System.out.println("Enter the Product ID of the product you want to remove:");
        int productId = input.nextInt();
        input.nextLine(); // Consume the newline
    
        File originalFile = new File("Products.txt");
        File tempFile = new File("tempProducts.txt");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
            String line;
            boolean productFound = false;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Assuming the delimiter is "\\|"
                if (parts.length >= 5) {
                    int currentProductId = Integer.parseInt(parts[0].trim());
                    if (currentProductId == productId) {
                        productFound = true;
                        // Skip writing this line to remove the product
                        continue;
                    }
                    // Write the existing product details to the temporary file
                    writer.write(line + System.lineSeparator());
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Products.txt: " + line);
                    writer.write(line + System.lineSeparator());
                }
            }
    
            if (!productFound) {
                System.out.println("Product not found.");
            }
    
        } catch (IOException e) {
            System.err.println("Error removing product: " + e.getMessage());
            return;
        }
    
        // Close the files and delete/rename them
        boolean deleteSuccess = originalFile.delete();
        if (!deleteSuccess) {
            System.err.println("Failed to delete original file.");
            return;
        }
    
        boolean renameSuccess = tempFile.renameTo(originalFile);
        if (!renameSuccess) {
            System.err.println("Failed to rename temporary file.");
            return;
        }
    
        System.out.println("Product removed successfully.");
    }
    public static void approveSellers() {
        File originalFile = new File("Users.txt");
        File tempFile = new File("tempUsers.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    writer.write(line + System.lineSeparator()); // Write header
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String userType = parts[1].trim();
                    String approvalStatus = parts[5].trim();

                    if (userType.equals("seller") && approvalStatus.equals("pending")) {
                        System.out.println("Pending Seller: " + line);
                        System.out.println("Do you want to approve this seller? (yes/no)");
                        String response = input.nextLine().trim().toLowerCase();

                        if (response.equals("yes")) {
                            parts[5] = "approved";
                            System.out.println("Seller approved.");
                        } else {
                            System.out.println("Seller not approved.");
                        }
                    }

                    writer.write(String.join("\\|", parts) + System.lineSeparator());
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Users.txt: " + line);
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error processing sellers: " + e.getMessage());
            return;
        }
        boolean deleteSuccess = originalFile.delete();
        if (!deleteSuccess) {
            System.err.println("Failed to delete original file.");
            return;
        }

        boolean renameSuccess = tempFile.renameTo(originalFile);
        if (!renameSuccess) {
            System.err.println("Failed to rename temporary file.");
            return;
        }

        System.out.println("Seller approval process completed.");
    }
    public static void searchUsers() {
        String userType = "";
        boolean validUserType = false;

        // Loop until a valid user type is entered
        while (!validUserType) {
            try {
                System.out.println("Enter the type of user you want to search (buyer/seller): ");
                userType = input.nextLine().trim().toLowerCase();

                if (userType.equals("buyer") || userType.equals("seller")) {
                    validUserType = true;
                } else {
                    System.out.println("Invalid user type. Please enter 'buyer' or 'seller'.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        System.out.println("Enter the name of the user you want to search: ");
        String searchName = input.nextLine().trim().toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    // Skip the first line (header)
                    firstLineSkipped = true;
                    continue;
                }

                String[] parts = line.split("\\|"); // Assuming the delimiter is ","
                if (parts.length >= 6) { // Ensure parts has at least 6 elements
                    String storedUserType = parts[1].trim().toLowerCase();
                    String fullName = parts[2].trim().toLowerCase() + " " + parts[3].trim().toLowerCase();

                    if (storedUserType.equals(userType) && fullName.contains(searchName)) {
                        found = true;
                        System.out.println("User found: " + line);
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Users.txt: " + line);
                }
            }

            if (!found) {
                System.out.println("No matching users found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewProductsByCategories(int categoryID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Products.txt"));
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) { // Ensure parts has at least 5 elements
                    int currentCategoryID;
                    try {
                        currentCategoryID = Integer.parseInt(parts[4].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid category ID format: " + parts[4]);
                        continue;
                    }
                    if (currentCategoryID == categoryID) {
                        System.out.println("Product ID: " + parts[0]);
                        System.out.println("Name: " + parts[1]);
                        System.out.println("Price: PKR" + parts[2]);
                        System.out.println("Seller ID: " + parts[3]);
                        System.out.println("Category ID: " + parts[4]);
                        System.out.println("---------------------------------");
                    }
                } else {
                    // Handle case where line doesn't have enough elements
                    System.err.println("Invalid format in Products.txt: " + line);
                }
            }
            System.out.println("Do you want to add something to cart or return to homepage?\n1. Add to Cart\n2. Return to Homepage");

        } catch (IOException ex) {
            System.err.println("Error reading file: " + ex.getMessage());
        }
    }
    public static void update(int ID, String newName, String fileName, String type) {
        
        
        File inputFile = new File(fileName);
        List<String> lines = new ArrayList<>();
        boolean idFound = false;

        // Read the file into a list of strings
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            boolean firstLineSkipped = false;
            while ((currentLine = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    lines.add(currentLine); // add header line as it is
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = currentLine.split("\\|");
                if (parts.length >= 2) {
                    int currentID = Integer.parseInt(parts[0].trim());
                    if (currentID == ID) {
                        currentLine = ID + "| " + newName;
                        idFound = true;
                    }
                }
                lines.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing the " + type +" ID: " + e.getMessage());
            return;
        }

        if (!idFound) {
            System.out.println(type + " with ID " + ID + " not found.");
            return;
        }

        // Write the list of strings back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println(type + " updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void remove(int categoryId, String inputFile, String type) {
        File originalFile = new File(inputFile);
        List<String> lines = new ArrayList<>();
        boolean categoryFound = false;
        boolean firstLineSkipped = false;
    
        // Read the file into a list of strings
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    lines.add(currentLine); // Add the header line as is
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = currentLine.split("\\|");
                if (parts.length >= 2) {
                    int currentId = Integer.parseInt(parts[0].trim());
                    // If the ID matches, skip this line
                    if (currentId == categoryId) {
                        categoryFound = true;
                        continue;
                    }
                }
                lines.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing the category ID: " + e.getMessage());
            return;
        }
    
        // If category was not found, print a message
        if (!categoryFound) {
            System.out.println(type + " with ID " + categoryId + " not found.");
            return;
        }
    
        // Write the list of strings back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Category removed successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    
    
}