import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
/*signUp
  logIn -> checkCredentials -> emailExists
*/
public class ECommerceProject {
    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        homePage();
    }
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
                            String userType = findUserDataByEmail(email,"Type");
                            
                            // System.out.println(userType);
                            if (userType.equals("seller")) {
                                String status = findUserDataByEmail(email,"Status");
                                // System.out.print(status);
                                if(status.equals("approved")) {
                                sellerHomePage(email);
                                }else{
                            System.out.println("Admin has not approved your request. Please contact admin for further details");

                                }
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
                    viewCategories();
                    break;
                case 2:
                    //searchProducts();
                    break;
                case 3: //works
                   viewProducts();
                    break;
                case 4: //works
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
            System.out.println("Welcome! What would you like to do:");
            System.out.println("1. View your products\n2. Add Products\n3. Update Products\n4. Remove a Product\n5. View Pending Orders\n6. View All Orders\n7. Execute Order\n8. View Stats\n9. View Profile\n10. Log Out");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline left-over
            switch (choice) {
                case 1: // Works, shows seller products
                    viewProducts(sellerIDByEmail(email));
                    break;
                case 2: { //Works , Adds products
                    int sellerId = -1;
                    sellerId = sellerIDByEmail(email);
                    viewProducts(sellerId);
                    boolean validProduct = false, validPrice = false, validCategory = false, status = false;
                    String newProduct = "", category = "";
                    int newPrice = 0, categoryID = -1;
                    while (!validProduct) {
                        try {
                            System.out.println("Enter the new product name: ");
                            newProduct = input.nextLine();
                            validProduct = true;
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid Input. Please enter a valid product name.");
                            input.nextLine(); // Clear the invalid input
                        }
                    }
                    while (!validPrice) {
                        try {
                            System.out.println("Enter the new product's price: ");
                            newPrice = input.nextInt();
                            if (newPrice <= 0) {
                                System.out.println("You cannot enter price less or equal to zero!");
                            } else {
                                validPrice = true;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid Input. Please enter a valid price.");
                            input.nextLine(); // Clear the invalid input
                        }
                    }
                    input.nextLine(); // Consume the remaining newline character
                    while (!validCategory) {
                        try {
                            viewCategories();
                            System.out.println("Enter the new product's category: ");
                            category = input.nextLine();
                            categoryID = categoryIDByName(category);
                            if (categoryID == -1) {
                                System.out.println("Invalid Input. Please enter a valid category.");
                            } else {
                                validCategory = true;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid Input. Please enter a valid category.");
                            input.nextLine(); // Clear the invalid input
                        }
                    }
                    if (sellerId == -1) {
                        System.out.println("Seller ID not found.");
                    } else {
                        int newID = getLastId("Products.txt") + 1;
                        status = addProduct(newID, newProduct, newPrice, sellerId, categoryID);
                    }
            
                    if (!status) {
                        System.out.println("Failed to add product.");
                    } else {
                        System.out.println("Product added successfully.");
                    }
                    break;
                }
                case 3:{ //Works, Updates Products
                    viewProducts(sellerIDByEmail(email));
                    boolean validInput = false;
                    while (!validInput) {
                        try {
                            System.out.println("Enter the Product ID of the product you want to update: ");
                            int productId = input.nextInt();
                            validInput = true;
                            if (isOwnedBySeller(productId, email))
                                updateProduct(productId, "Products.txt", "Product");
                            else
                                System.out.println("Product not found.");
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer for the Product ID.");
                            input.nextLine(); // consume the invalid input  
                        }
                    }
                    break;
                }
                case 4: { //Works, removes products
                    viewProducts(sellerIDByEmail(email));
                    boolean validInput = false;
                    while (!validInput) {
                        try {
                            System.out.println("Enter the Product ID of the product you want to remove: ");
                            int productId = input.nextInt();
                            validInput = true;
                            if (isOwnedBySeller(productId, email))
                                remove(productId, "Products.txt", "Product");
                            else
                                System.out.println("Product not found.");
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer for the Product ID.");
                            input.nextLine(); // consume the invalid input  
                        }
                    }
                    break;
                }
                case 5: // Works, View Orders
                    viewPendingOrders(sellerIDByEmail(email));
                    break;
                case 6: // Works, View All Orders
                    viewAllOrders(sellerIDByEmail(email));
                    break;
                case 7: // Works, executes order
                int orderID;
                    boolean orderIDExists = false;
                    int sellerID = sellerIDByEmail(email);
                    if (sellerID == -1) {
                        System.out.println("Seller ID not found.");
                    } else {
                        System.out.println("Enter the order ID you want to execute: ");
                        orderID = input.nextInt();
                        orderIDExists = orderIDBySellerIDAndPending(orderID, sellerID);
                        if (orderIDExists)
                            executeOrder(orderID);
                        else {
                            System.out.println("Order ID not found.");
                        }
                    }
                    break;
                case 8:
                    // viewStats(email);
                    break;
                case 9: // Works
                    viewProfile(email);
                    break;
                case 10: // Works
                    exit = true;
                    logOut();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 10.");
                    break;
            }
        }
    }

    public static void adminHomePage(String email) {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("Welcome! What would you like to do:");
                System.out.println("1. Add Categories\n2. View Categories\n3. Update Categories\n4. Remove Categories\n5. Approve Sellers\n6. Search a user (buyer/seller)\n7. Remove User\n8. View Products\n9. View Profile\n10. Log Out");
                int choice = input.nextInt();
                input.nextLine(); // Consume newline left-over
                switch (choice) {
                    case 1: //works
                        addCategories();
                        break;
                    case 2: //works
                        viewCategories();
                        break;
                    case 3: { //works, updates categories
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
                        updateCategory(categoryID, newCategoryName, "Category.txt", "Category");
                        break;
                    }
                    case 4: // works, removes categories
                        try {
                        System.out.println("Enter the ID of category you want to remove: ");
                        int categoryID = input.nextInt();
                        remove(categoryID, "Category.txt", "Category");
                        }
                        catch (InputMismatchException ex) {
                            System.out.println("Invalid Input");
                        }
                        break;
                    case 5: {
                        viewPendingSellers();
                        int sellerID = -1;
                        boolean validInput = false;
                        while (!validInput) {
                            try {
                                System.out.println("Enter the ID of seller you want to approve: ");
                                sellerID = input.nextInt();
                                validInput = true;
                            }
                            catch (InputMismatchException ex) {
                                System.out.println("Incorrect Input. Please input correct seller ID: ");
                                input.next(); // Clear the invalid input
                            }
                        }
                        String userType = findUserTypeByID(sellerID);
                            if (userType.equals("seller")) {
                                approveSellers(sellerID);
                            } else if (userType.equals("buyer")) {
                                System.out.println("You cannot enter buyer ID!");
                            } else if (userType.equals("Invalid")) {
                                System.out.println("Seller ID not found!");
                            } else {
                                System.out.println("You cannot enter your own ID!");
                            }
                        break;
                    }
                    case 6:
                        //searchUsers();
                        break;
                    case 7:
                        //removeUser();
                        break;
                    case 8: //works
                        viewProducts();
                        break;
                    case 9: //works
                        viewProfile(email);
                        break;
                    case 10: //works
                        exit = true;
                        //logOut();
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
    public static String findUserDataByEmail(String email,String column) {
        // UserID | Type | Name | Email | Password | Address | Status
        // System.out.print(column);
        HashMap<String, Integer> columns = new HashMap<>();
        columns.put("UserID", 0);
        columns.put("Type", 1);
        columns.put("Name", 2);
        columns.put("Email", 3);
        columns.put("Password", 4);
        columns.put("Address", 5);
        columns.put("Status", 6);
        int index=columns.get(column);
        // System.out.print(index);
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
                        return parts[index].trim();
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
                if (parts.length >= 5) {
                    String storedEmail = parts[3].trim();
                    if (storedEmail.equals(email)) {
                        System.out.println("Profile Information:");
                        System.out.println("User ID " + parts[0].trim());
                        System.out.println("Account Type " + parts[1].trim());
                        System.out.println("Full Name: " + parts[2].trim());
                        System.out.println("Address: " + parts[5].trim());
                        System.out.println("Email: " + parts[3].trim());
                        System.out.println("Password: " + parts[4].trim());
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
        try {
            int buyerID = 0;
            BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String buyerEmail = parts[3].trim();
                    if (buyerEmail.equals(email)) {
                        buyerID = Integer.parseInt(parts[0]);
                        reader.close();
                    }
                } else {
                    System.err.println("No email found ");
                }
            }
            BufferedReader reader2 = new BufferedReader(new FileReader("Order.txt"));
            String line2;
            boolean firstLineSkipped2 = false;
            int count = 1;
            while ((line2 = reader2.readLine()) != null) {
                if (!firstLineSkipped2) {
                    firstLineSkipped2 = true;
                    continue;
                }
                String[] parts = line2.split("\\|");
                if (parts.length >= 5) {
                    String buyerIDFromFile = parts[1].trim();
                    int buyerIDFromFileToInt = Integer.parseInt(buyerIDFromFile);
                    if (buyerIDFromFileToInt == buyerID) {
                        System.out.println("Order " + count + "Status:");
                        System.out.println("Order ID: " + parts[0].trim());
                        System.out.println("Seller ID: " + parts[2].trim());
                        System.out.println("Price: " + parts[3].trim());
                        System.out.println("Status: " + parts[4].trim());
                        System.out.println("Date of Placing Order: " + parts[5].trim());
                        System.out.println("Date of Execution: " + parts[6].trim());
                        count++;
                        break;
                    }
                } else {
                    System.err.println("No email found ");
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
    public static void updateCategory(int ID, String newName, String fileName, String type) {
        File inputFile = new File(fileName);
        List<String> lines = new ArrayList<>();
        boolean idFound = false;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println(type + " updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void updateProduct(int ID, String fileName, String type) {
        File inputFile = new File(fileName);
        List<String> lines = new ArrayList<>();
        boolean idFound = false;
        int choice = 0;
        int newPrice = 0;
        String newName = "";
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("What do you want to update?\n1. Name\n2. Price\nEnter 1 to update name and 2 to update price: ");
                choice = input.nextInt();
                input.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter new name: ");
                        newName = input.nextLine();
                        validInput = true;
                        break;
                    case 2:
                        System.out.println("Enter new price: ");
                        newPrice = input.nextInt();
                        input.nextLine(); // Consume the newline
                        validInput = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                input.nextLine(); // Consume the invalid input
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            boolean firstLineSkipped = false;

            while ((currentLine = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    lines.add(currentLine); // Add header line as it is
                    firstLineSkipped = true;
                    continue;
                }

                String[] parts = currentLine.split("\\|");
                if (parts.length >= 6) {
                    int currentID = Integer.parseInt(parts[0].trim());

                    if (currentID == ID) {
                        if (choice == 1) {
                            parts[1] = newName;
                        } else if (choice == 2) {
                            parts[2] = String.valueOf(newPrice);
                        }
                        currentLine = String.join(" | ", parts);
                        idFound = true;
                    }
                }
                lines.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing the " + type + " ID: " + e.getMessage());
            return;
        }

        if (!idFound) {
            System.out.println(type + " with ID " + ID + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println(type + " updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void remove(int ID, String inputFile, String type) {
        File originalFile = new File(inputFile);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        boolean firstLineSkipped = false;
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
                    if (currentId == ID) {
                        found = true;
                        continue;
                    }
                }
                lines.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing the " + type + " ID: " + e.getMessage());
            return;
        }
        if (!found) {
            System.out.println(type + " with ID " + ID + " not found.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println(type + " removed successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static boolean isOwnedBySeller(int productId, String sellerEmail) {
        int sellerId = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && parts[3].trim().equals(sellerEmail) && parts[1].trim().equals("seller")) {
                    sellerId = Integer.parseInt(parts[0].trim());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
        if (sellerId == -1) {
            System.out.println("Seller with the specified email not found.");
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    int storedProductId = Integer.parseInt(parts[0].trim());
                    int storedSellerId = Integer.parseInt(parts[3].trim());
                    if (storedProductId == productId && storedSellerId == sellerId) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }
    public static boolean addProduct(int productID, String productName, int productPrice, int sellerID, int categoryID) {
        String newProduct = productID + " | " + productName + " | " + productPrice + " | " + sellerID + " | " + categoryID;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Products.txt", true))) {
            File file = new File("Products.txt");
            if (file.length() > 0) {
                writer.newLine(); // Add a new line before writing the new product
            }
            writer.write(newProduct);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
    public static int sellerIDByEmail(String sellerEmail) {
        int sellerID = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && parts[3].trim().equals(sellerEmail) && parts[1].trim().equals("seller")) {
                    sellerID = Integer.parseInt(parts[0].trim());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        if (sellerID == -1) {
            System.out.println("Seller with the specified email not found.");
        }
        
        return sellerID;
    }
    public static int categoryIDByName(String categoryName) {
        int categoryID = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader("Category.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].trim().toLowerCase().equals(categoryName.toLowerCase())) {
                    categoryID = Integer.parseInt(parts[0].trim());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        if (categoryID == -1) {
            System.out.println("Category with the specified name not found.");
        }
        return categoryID;
    }
    public static void viewProducts(int sellerID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && Integer.parseInt(parts[3].trim()) == sellerID) {
                    System.out.println("Product ID: " + parts[0].trim() + ", Product Name: " + parts[1].trim() + ", Price: " + parts[2].trim() + ", Seller ID: " + parts[3].trim() + " , Category ID: " + parts[4].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void viewPendingOrders(int sellerID) {
        boolean ordersFound = false; // Flag to track if any orders are found
    
        try (BufferedReader reader = new BufferedReader(new FileReader("Orders.txt"))) {
            String line;
            boolean firstLineSkipped = false;
    
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
    
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && Integer.parseInt(parts[2].trim()) == sellerID && parts[4].trim().equalsIgnoreCase("Pending")) {
                    System.out.println("Order ID: " + parts[0].trim() + ", Buyer ID: " + parts[1].trim() + ", Price: " + parts[3].trim() + ", Status: " + parts[4].trim() + ", Date of Placing Order: " + parts[5].trim());
                    ordersFound = true; // Set the flag to true if an order is found
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    
        if (!ordersFound) {
            System.out.println("No orders found.");
        }
    }
    public static void viewAllOrders(int sellerID) {
        boolean ordersFound = false; 
        try (BufferedReader reader = new BufferedReader(new FileReader("Orders.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && Integer.parseInt(parts[2].trim()) == sellerID) {
                    System.out.println("Order ID: " + parts[0].trim() + ", Buyer ID: " + parts[1].trim() + ", Price: " + parts[3].trim() + ", Status: " + parts[4].trim() + ", Date of Placing Order: " + parts[5].trim() + ", Date of Executing Order: " + parts[6].trim());
                    ordersFound = true; // Set the flag to true if an order is found
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    
        if (!ordersFound) {
            System.out.println("No orders found.");
        }
    }
    public static boolean orderIDBySellerIDAndPending(int orderID, int sellerID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Orders.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && Integer.parseInt(parts[2].trim()) == sellerID && Integer.parseInt(parts[0].trim()) == orderID && parts[4].trim().equalsIgnoreCase("Pending")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }
    public static String todaysDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Updated format
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    public static void executeOrder(int orderID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Orders.txt"))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    fileContent.append(line).append(System.lineSeparator());
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && Integer.parseInt(parts[0].trim()) == orderID) {
                    String newLine = parts[0].trim() + "|" + parts[1].trim() + "|" + parts[2].trim() + "|" + parts[3].trim() + "|" + "Delivered" + "|" + parts[5].trim() + "|" + todaysDate();
                    fileContent.append(newLine).append(System.lineSeparator());
                    System.out.println("Order with ID " + orderID + " has been delivered successfully.");
                } else {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Orders.txt"))) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void approveSellers(int sellerID) { //works
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    lines.add(line); // Add header line as is
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && Integer.parseInt(parts[0].trim()) == sellerID) {
                    if (parts[6].trim().equalsIgnoreCase("pending")) {
                        parts[6] = "approved";
                        line = String.join("|", parts);
                    } else {
                        System.out.println("Seller is already approved");
                    }
                }
                lines.add(line); // Add modified or unmodified line
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt"))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
    public static void viewPendingSellers() {
        boolean sellerFound = false; // Flag to track if any sellers are found
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && parts[1].trim().equalsIgnoreCase("seller") && parts[6].trim().equalsIgnoreCase("pending")) {
                    System.out.println("User ID: " + parts[0].trim() + ", Seller Name " + parts[2].trim() + ", Email: " + parts[3].trim() + ", Address: " + parts[5].trim()+ ", Status: " + parts[6].trim() );
                    sellerFound = true; // Set the flag to true if a seller is found
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    
        if (!sellerFound) {
            System.out.println("No sellers with pending status found.");
        }
    }
    public static String findUserTypeByID(int ID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the header line
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && Integer.parseInt(parts[0].trim())==ID) {
                    return parts[1].trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return "Invalid";
    }
}