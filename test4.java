import java.io.*;
import java.util.*;
import java.text.*;
public class test4{

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the eCommerce Store");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //SIGN UP FUNCTION TO CREATE A NEW ACCOUNT
    public static void signup() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            int lastId = 0;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                lastId = Integer.parseInt(userData[0]);
            }
            br.close();

            Scanner sc = new Scanner(System.in);
            int userId = lastId + 1;
            System.out.print("Enter first name: ");
            String firstName = sc.nextLine();
            System.out.print("Enter last name: ");
            String lastName = sc.nextLine();
            System.out.print("Are you a buyer or seller? (buyer/seller): ");
            String userType = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();

            if (emailExists(email)) {
                System.out.println("Email already exists. Please login.");
                return;
            }

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            System.out.print("Confirm password: ");
            String confirmPassword = sc.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match.");
                return;
            }

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            String userInfo = userId + "," + firstName + "," + lastName + "," + email + "," + password + "," + userType + "," + address + ",pending\n";
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
            bw.write(userInfo);
            bw.close();

            System.out.println("Sign up successful!");

        } catch (IOException e) {
            System.out.println("An error occurred during signup: " + e.getMessage());
        }
    }

    //LOGIN FUNCTION
    //enter correct email and password to login into respective account
    public static void login() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            boolean loginSuccess = false;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[3].equals(email) && userData[4].equals(password)) {
                    System.out.println("Welcome " + userData[1] + "!");
                    String userType = userData[5].trim();
                    switch (userType) {
                        case "buyer":
                            buyerHomepage(userData);
                            break;
                        case "seller":
                            sellerHomepage(userData);
                            break;
                        case "admin":
                            adminHomepage(userData);
                            break;
                        default:
                            System.out.println("Invalid user type.");
                    }
                    loginSuccess = true;
                    break;
                }
            }
            br.close();
            

            if (!loginSuccess) {
                System.out.println("Invalid email or password.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    //FUNCTION TO CHECK IF EMAIL EXISTS --> called out in sign up function to check if account with same email exits or not
    public static boolean emailExists(String email) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[3].equals(email)) {
                    br.close();
                    return true;
                }
            }
            br.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return false;
    }

    //BUYER HOME PAGE
    public static void buyerHomepage(String[] userData) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nBuyer Homepage");
            System.out.println("1. View Categories");
            System.out.println("2. Search Products");
            System.out.println("3. View Products");
            System.out.println("4. View Profile");
            System.out.println("5. Order Product");
            System.out.println("6. View Cart");
            System.out.println("7. Confirm Order and Checkout");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            

            switch (choice) {
                case 1:
                    viewCategories();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    viewProducts();
                    break;
                case 4:
                    viewProfile(userData);
                    break;
                case 5:
                    addToCart(userData);
                    break;
                case 6:
                    viewCart(userData);
                    break;
                case 7:
                    checkout(userData);
                    break;
                case 8:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //SELLER HOME PAGE
    public static void sellerHomepage(String[] userData) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nSeller Homepage");
            System.out.println("1. View My Products");
            System.out.println("2. Remove My Products");
            System.out.println("3. View My Orders");
            System.out.println("4. Execute My Orders");
            System.out.println("5. View Profile");
            System.out.println("6. View My Status");
            System.out.println("7. Add New Product");
            System.out.println("8. View Sales Analytics");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            

            switch (choice) {
                case 1:
                    viewMyProducts(userData);
                    break;
                case 2:
                    removeMyProducts(userData);
                    break;
                case 3:
                    viewMyOrders(userData);
                    break;
                case 4:
                    executeMyOrders(userData);
                    break;
                case 5:
                    viewProfile(userData);
                    break;
                case 6:
                    viewMyStatus(userData);
                    break;
                case 7:
                    addProduct(userData);
                    break;
                case 8:
                    viewSalesAnalytics(userData);
                    return;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //ADMIN HOME PAGE 
    public static void adminHomepage(String[] userData) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Homepage");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("4. Remove Category");
            System.out.println("5. Approve Seller Status");
            System.out.println("6. Search Users");
            System.out.println("7. Remove Users");
            System.out.println("8. View Products");
            System.out.println("9. View Profile");
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    viewCategories();
                    break;
                case 3:
                    removeCategory();
                    break;
                case 4:
                    approveSellerStatus();
                    break;
                case 5:
                    searchUsers();
                    break;
                case 6:
                    removeUsers();
                    break;
                case 7:
                    viewProducts();
                    break;
                case 8:
                    viewProfile(userData);
                    break;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //BUYER AND ADMIN FUNCTION TO VIEW ALL CATEGORIES
    public static void viewCategories() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("categories.txt"));
            String line;
            System.out.println("Categories:");
            while ((line = br.readLine()) != null) {
                System.out.println("* "+line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //BUYER FUNCTION TO SEARCH FOR A PRODUCT
    public static void searchProducts() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter product name to search: ");
            String searchQuery = sc.nextLine().toLowerCase();
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line;
            boolean found = false;
            System.out.println("Search Results:");
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(searchQuery)) {
                    String[] productDetails = line.split(",");
                    if (productDetails.length == 6) {
                        System.out.println("Product ID: " + productDetails[0]);
                        System.out.println("Seller ID: " + productDetails[1]);
                        System.out.println("Product Name: " + productDetails[2]);
                        System.out.println("Category: " + productDetails[3]);
                        System.out.println("Price: " + productDetails[4]);
                        System.out.println("Quantity: " + productDetails[5]);
                    } else {
                        System.out.println("Invalid product data format.");
                    }
                    found = true;
                }
            }
            br.close();
            
            if (!found) {
                System.out.println("No products found.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

   //FUNCTION FOR BUYER AND ADMIN TO VIEW ALL PRODUCTS
   public static void viewProducts() {
    try {
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        String line;
        System.out.println("Products:");
        while ((line = br.readLine()) != null) {
            String[] productDetails = line.split(",");
                if (productDetails.length == 6) {  // Adjust based on the actual number of fields in your products.txt
                    System.out.println("Product ID: " + productDetails[0]);
                    System.out.println("Seller ID: " + productDetails[1]);
                    System.out.println("Product Name: " + productDetails[2]);
                    System.out.println("Category: " + productDetails[3]);
                    System.out.println("Price: " + productDetails[4]);
                    System.out.println("Quantity: " + productDetails[5]);
                    System.out.println("-------------------------");
                } else {
                    System.out.println("Invalid product data format.");
                }
        }
        br.close();
    } catch (IOException e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}

    //SELLER FUNCTION TO VIEW THEIR OWN PRODUCTS
    public static void viewMyProducts(String[] userData) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line;
            System.out.println("My Products:");
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split(",");
                if (productDetails.length == 6 && productDetails[1].equals(userData[0])) { 
                    System.out.println("Product ID: " + productDetails[0]);
                    System.out.println("Seller ID: " + productDetails[1]);
                    System.out.println("Product Name: " + productDetails[2]);
                    System.out.println("Category: " + productDetails[3]);
                    System.out.println("Price: " + productDetails[4]);
                    System.out.println("Quantity: " + productDetails[5]);
                    System.out.println("-------------------------");
                    found = true;
                } 
                }
            
            if (!found) 
                System.out.println("No products found for the user.");
            
            br.close();
        }
         catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

    }

    //SELLER FUNCTION TO REMOVE A PRODUCT
    public static void removeMyProducts(String[] userData) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter product name to remove: ");
            String productName = sc.nextLine();

            File inputFile = new File("products.txt");
            File tempFile = new File("products_temp.txt");
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean productFound = false;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(",");
                if (productData[2].equals(productName) && productData[1].equals(userData[0])) {
                    productFound = true;
                } else {
                    writer.write(line + "\n");
                }
            }
            reader.close();
            writer.close();

            if (!productFound) {
                System.out.println("No product found or you don't have permission to remove it.");
                tempFile.delete();
                return;
            }

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Could not update product file.");
            } else {
                System.out.println("Product removed successfully.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    //SELLER FUNCTION TO VIEW THEIR ORDERS
    public static void viewMyOrders(String[] userData) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("orders.txt"));
            String line;
            boolean foundOrders = false;
            System.out.println("My Orders:");
            while ((line = br.readLine()) != null) {
                String[] orderData = line.split(",");
                if (orderData.length >= 3 && orderData[1].equals(userData[0])) {
                    if (orderData.length >= 3) {
                        System.out.println("Order ID: " + orderData[0]);
                        System.out.println("Quantity: " + orderData[2]);
                        System.out.println("-------------------------");
                    } else 
                        System.out.println("Invalid order data format.");

                    foundOrders = true;
                }
            }
            br.close();
            if (!foundOrders) {
                System.out.println("No orders found.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //SELLER TO EXECUTE ORDER
    public static void executeMyOrders(String[] userData) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter order ID to execute: ");
            String orderId = sc.nextLine();
    
            File file = new File("orders.txt");
            File tempFile = new File("orders_temp.txt");
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
    
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    
            String line;
            boolean orderFound = false;
            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split(",");
                if (orderData.length >= 3 && orderData[0].equals(orderId) && orderData[1].equals(userData[0])) {
                    // Update order status if the seller ID matches
                    orderFound = true;
                    orderData[2] = "approved"; // Assuming index 2 is for order status
                    line = String.join(",", orderData);
                }
                writer.write(line + "\n");
            }
            writer.close();
            reader.close();
            sc.close();
    
            if (!orderFound) {
                System.out.println("Order not found or you don't have permission to execute it.");
                tempFile.delete(); // Delete temporary file if no order was executed
                return;
            }
    
            // Replace the original file with the updated one
            if (!file.delete() || !tempFile.renameTo(file)) {
                System.out.println("Could not update order file.");
            } else {
                System.out.println("Order executed successfully.");
            }
    
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    

    //SELLER FUNCTION TO VIEW THEIR PROFILE STATUS
    public static void viewMyStatus(String[] userData) {
        System.out.println("Your status is: " + userData[7]);
    }

    //SELLER FUNCTION TO ADD A NEW PRODUCT by appending products file
    public static void addProduct(String[] userData) {
        try {
            Scanner sc = new Scanner(System.in);

            int nextProductId = 1; // Default starting ID if file is empty
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] productData = line.split(",");
                int productId = Integer.parseInt(productData[0]);
                if (productId >= nextProductId) {
                    nextProductId = productId + 1;
                }
            }
            br.close();

            System.out.print("Enter product name: ");
            String productName = sc.nextLine();
            System.out.print("Enter category: ");
            String category = sc.nextLine();
            System.out.print("Enter price: ");
            String price = sc.nextLine();
            System.out.print("Enter quantity: ");
            String quantity = sc.nextLine();

            FileWriter fw = new FileWriter("products.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            String newProduct = nextProductId + "," + userData[0] + "," + productName + "," + category + "," + price + "," + quantity +"\n";
            writer.write(newProduct);
            writer.close();
            fw.close();
            

            System.out.println("Product added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //SALES ANALYTICS
    public static void viewSalesAnalytics(String[] userData) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("\nAnalyze Sales");
            System.out.println("1. Daily");
            System.out.println("2. Weekly");
            System.out.println("3. Monthly");
            System.out.println("4. Yearly");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();

            BufferedReader br = new BufferedReader(new FileReader("orders.txt"));
            String line;
            boolean foundOrders = false;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String todayStr = sdf.format(new Date());
            String[] todayParts = todayStr.split("-");
            int todayYear = Integer.parseInt(todayParts[0]);
            int todayMonth = Integer.parseInt(todayParts[1]);
            int todayDay = Integer.parseInt(todayParts[2]);

            switch (choice) {
                case 1:
                    System.out.println("Daily Sales:");
                    while ((line = br.readLine()) != null) {
                        String[] orderData = line.split(",");
                        if (orderData.length >= 3 && orderData[1].equals(userData[0])) {
                            String[] orderDateParts = orderData[3].split("-");
                            if (orderDateParts[0].equals(todayParts[0]) && orderDateParts[1].equals(todayParts[1])
                                    && orderDateParts[2].equals(todayParts[2])) {
                                displayOrder(orderData);
                                foundOrders = true;
                            }
                        }
                    }
                    break;
                case 2:
                    System.out.println("Weekly Sales:");
                    while ((line = br.readLine()) != null) {
                        String[] orderData = line.split(",");
                        if (orderData.length >= 3 && orderData[1].equals(userData[0])) {
                            String[] orderDateParts = orderData[3].split("-");
                            int orderYear = Integer.parseInt(orderDateParts[0]);
                            int orderMonth = Integer.parseInt(orderDateParts[1]);
                            int orderDay = Integer.parseInt(orderDateParts[2]);
                            if (isSameWeek(todayYear, todayMonth, todayDay, orderYear, orderMonth, orderDay)) {
                                displayOrder(orderData);
                                foundOrders = true;
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Monthly Sales:");
                    while ((line = br.readLine()) != null) {
                        String[] orderData = line.split(",");
                        if (orderData.length >= 3 && orderData[1].equals(userData[0])) {
                            String[] orderDateParts = orderData[3].split("-");
                            if (orderDateParts[0].equals(todayParts[0]) && orderDateParts[1].equals(todayParts[1])) {
                                displayOrder(orderData);
                                foundOrders = true;
                            }
                        }
                    }
                    break;
                case 4:
                    System.out.println("Yearly Sales:");
                    while ((line = br.readLine()) != null) {
                        String[] orderData = line.split(",");
                        if (orderData.length >= 3 && orderData[1].equals(userData[0])) {
                            String[] orderDateParts = orderData[3].split("-");
                            if (orderDateParts[0].equals(todayParts[0])) {
                                displayOrder(orderData);
                                foundOrders = true;
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (!foundOrders) {
                System.out.println("No sales found for the selected period.");
            }

            br.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static boolean isSameWeek(int todayYear, int todayMonth, int todayDay, int orderYear, int orderMonth,
            int orderDay) {
        int todayTotalDays = todayYear * 365 + todayMonth * 30 + todayDay;
        int orderTotalDays = orderYear * 365 + orderMonth * 30 + orderDay;
        return Math.abs(todayTotalDays - orderTotalDays) <= 7;
    }

    private static void displayOrder(String[] orderData) {
        System.out.println("Order ID: " + orderData[0]);
        System.out.println("Product ID: " + orderData[2]);
        System.out.println("Quantity: " + orderData[3]);
        System.out.println("Order Date: " + orderData[4]);
        System.out.println("-------------------------");
    }


    //BUYER, SELLER AND ADMIN FUNCTION TO VIEW THEIR OWN PROFILE 
    public static void viewProfile(String[] userData) {
        System.out.println("Profile:");
        System.out.println("ID: " + userData[0]);
        System.out.println("First Name: " + userData[1]);
        System.out.println("Last Name: " + userData[2]);
        System.out.println("Email: " + userData[3]);
        System.out.println("Address: " + userData[6]);
        System.out.println("Status: " + userData[7]);
    }

    //BUYER FUNCTION TO ADD PRODUCT TO CART
    public static void addToCart(String[] userData) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter product ID to add to cart: ");
            int productId = sc.nextInt();
            System.out.print("Enter quantity to add to cart: ");
            int quantity = sc.nextInt();

            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line;
            boolean productFound = false;
            while ((line = br.readLine())!= null) {
                String[] productData = line.split(",");
                if (productData[0].equals(String.valueOf(productId))) {
                    productFound = true;
                    int currentQuantity = Integer.parseInt(productData[5]);
                    if (currentQuantity < quantity) {
                        System.out.println("Error: Insufficient product quantity.");
                        return;
                    }
                }
            }
            br.close();
            if (!productFound) {
                System.out.println("Error: Product not found.");
                return;
            }
            FileWriter fw = new FileWriter("cart.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            String newCartItem = userData[0] + "," + productId + "," + quantity + "\n";
            writer.write(newCartItem);
            writer.close();
            fw.close();
            System.out.println("Product added to cart successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //BUYER FUNCTION TO VIEW CART
    public static void viewCart(String[] userData) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("cart.txt"));
            String line;
            System.out.println("My Cart:");
            while ((line = br.readLine())!= null) {
                String[] cartData = line.split(",");
                if (cartData[0].equals(userData[0])) {
                    System.out.println("Product ID: " + cartData[1]);
                    System.out.println("Quantity: " + cartData[2]);
                    System.out.println("--------");
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("An error occurred: ");
        }
    }

    //METHOD TO FINALIZE ORDER
    public static void checkout(String[] userData) {
        try {
            String[] cart = new String[10];
            int cartIndex = 0;
            BufferedReader br = new BufferedReader(new FileReader("cart.txt"));
            String line;
            while ((line = br.readLine())!= null) {
                cart[cartIndex++] = line;
            }
            br.close();
    
            double totalBill = 0;
            for (int i = 0; i < cartIndex; i++) {
                String[] cartData = cart[i].split(",");
                int productId = Integer.parseInt(cartData[1]);
                int quantity = Integer.parseInt(cartData[2]);
                BufferedReader br2 = new BufferedReader(new FileReader("products.txt"));
                String line2;
                while ((line2 = br2.readLine())!= null) {
                    String[] productData = line2.split(",");
                    if (productData[0].equals(String.valueOf(productId))) {
                        double price = Double.parseDouble(productData[4]);
                        totalBill += price * quantity;
                        break;
                    }
                }
                br2.close();
            }
    
            FileWriter fw = new FileWriter("orders.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            for (int i = 0; i < cartIndex; i++) {
                String[] cartData = cart[i].split(",");
                writer.write(userData[0] + "," + cartData[1] + "," + cartData[2] + "," + totalBill + "\n");
            }
            writer.close();
            fw.close();

            System.out.println("Your Total bill is: " + totalBill);
            System.out.println("Order Placed Successfully.");
    
            FileWriter fw2 = new FileWriter("cart.txt", false);
            BufferedWriter writer2 = new BufferedWriter(fw2);
            writer2.write("");
            writer2.close();
            fw2.close();
    
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    //ADMIN FUNCTION TO ADD A NEW CATEGORY
    public static void addCategory() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter new category name: ");
            String categoryName = sc.nextLine();

            BufferedWriter bw = new BufferedWriter(new FileWriter("categories.txt", true));
            bw.write(categoryName + "\n");
            bw.close();
            

            System.out.println("Category added successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //ASK FOR HELPP!!!!!!!!!!!!!!
    //ADMIN FUNCTION TO REMOVE A CATEGORY
    public static void removeCategory() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter category name to remove: ");
            String categoryName = sc.nextLine();

            File inputFile = new File("categories.txt");
            File tempFile = new File("categories_temp.txt");
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equalsIgnoreCase(categoryName.trim())) {
                    writer.write(line + "\n");
                }
            }
            writer.close();
            reader.close();
            

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Could not update category file.");
            } else {
                System.out.println("Category removed successfully.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //ADMIN FUNCTION TO APPROVE STATUS OF SELLERS
    public static void approveSellerStatus() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter seller ID to approve: ");
            String sellerId = sc.nextLine();

            File inputFile = new File("users.txt");
            File tempFile = new File("users_temp.txt");
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(sellerId) && userData[5].trim().equals("seller")) {
                    userData[7] = "approved";
                    line = String.join(",", userData);
                }
                writer.write(line + "\n");
            }
            writer.close();
            reader.close();
           

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Could not update user file.");
            } else {
                System.out.println("Seller approved successfully.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //ADMIN FUNCTION TO SEARCH USERS
    public static void searchUsers() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter user name to search: ");
            String search = sc.nextLine().toLowerCase();
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            boolean found = false;
            System.out.println("Search Results:");
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(search)) {
                    String[] user = line.split(",");
                    System.out.println("Profile:");
                    System.out.println("ID: " + user[0]);
                    System.out.println("First Name: " + user[1]);
                    System.out.println("Last Name: " + user[2]);
                    System.out.println("Email: " + user[3]);
                    System.out.println("Address: " + user[6]);
                    System.out.println("Status: " + user[7]);
                    found = true;
                }
            }
            br.close();
            
            if (!found) {
                System.out.println("No users found.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //ADMIN FUNCTION TO REMOVE USERS
    public static void removeUsers() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter user ID to remove: ");
            String userId = sc.nextLine();

            File inputFile = new File("users.txt");
            File tempFile = new File("users_temp.txt");
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (!userData[0].equals(userId)) {
                    writer.write(line + "\n");
                }
            }
            writer.close();
            reader.close();

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Could not update user file.");
            } else {
                System.out.println("User removed successfully.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    

    //LOGOUT --> DIRECTED TO HOME 
    public static void logout() {
        System.out.println("Logged out successfully.");
    }
}