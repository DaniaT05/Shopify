import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ECommerceStore {

    // Constants for maximum entries
    private static final int MAX_USERS = 100;
    private static final int MAX_PRODUCTS = 100;
    private static final int MAX_ORDERS = 100;

    // User data
    private static String[] userIds = new String[MAX_USERS];
    private static String[] userNames = new String[MAX_USERS];
    private static String[] userPasswords = new String[MAX_USERS];
    private static int userCount = 0;

    // Product data
    private static String[] productIds = new String[MAX_PRODUCTS];
    private static String[] productNames = new String[MAX_PRODUCTS];
    private static double[] productPrices = new double[MAX_PRODUCTS];
    private static String[] productTypes = new String[MAX_PRODUCTS];
    private static int productCount = 0;

    // Order data
    private static String[] orderIds = new String[MAX_ORDERS];
    private static String[] orderProductIds = new String[MAX_ORDERS];
    private static int[] orderQuantities = new int[MAX_ORDERS];
    private static LocalDate[] orderDates = new LocalDate[MAX_ORDERS];
    private static int orderCount = 0;

    // File paths
    private static final String USERS_FILE = "users.txt";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String ORDERS_FILE = "orders.txt";

    public static void main(String[] args) {
        loadUsers();
        loadProducts();
        loadOrders();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to E-Commerce Store");
            System.out.println("1. Register User");
            System.out.println("2. Register Seller");
            System.out.println("3. Seller Login");
            System.out.println("4. Consumer Login");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser(scanner, "consumer");
                    break;
                case 2:
                    registerUser(scanner, "seller");
                    break;
                case 3:
                    sellerLogin(scanner);
                    break;
                case 4:
                    consumerLogin(scanner);
                    break;
                case 5:
                    saveUsers();
                    saveProducts();
                    saveOrders();
                    scanner.close();
                    System.out.println("Thank you for using E-Commerce Store. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to register a user
    private static void registerUser(Scanner scanner, String userType) {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        userIds[userCount] = userId;
        userNames[userCount] = userName;
        userPasswords[userCount] = password;
        userCount++;

        System.out.println(userType + " registered successfully.");
    }

    // Method for seller login and actions
    private static void sellerLogin(Scanner scanner) {
        System.out.print("Enter seller ID: ");
        String sellerId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (validateUser(sellerId, password)) {
            System.out.println("Seller logged in successfully.");
            sellerActions(scanner);
        } else {
            System.out.println("Invalid seller ID or password.");
        }
    }

    // Method for consumer login and actions
    private static void consumerLogin(Scanner scanner) {
        System.out.print("Enter consumer ID: ");
        String consumerId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (validateUser(consumerId, password)) {
            System.out.println("Consumer logged in successfully.");
            consumerActions(scanner);
        } else {
            System.out.println("Invalid consumer ID or password.");
        }
    }

    // Method to validate user credentials
    private static boolean validateUser(String userId, String password) {
        for (int i = 0; i < userCount; i++) {
            if (userIds[i].equals(userId) && userPasswords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Methods for seller actions
    private static void sellerActions(Scanner scanner) {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product");
            System.out.println("5. View Sales Analytics");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    removeProduct(scanner);
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    searchProduct(scanner);
                    break;
                case 5:
                    viewSalesAnalytics(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Methods for consumer actions
    private static void consumerActions(Scanner scanner) {
        while (true) {
            System.out.println("1. View Products");
            System.out.println("2. Search Products by Price");
            System.out.println("3. Search Products by Type");
            System.out.println("4. Order Product");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProductsByPrice(scanner);
                    break;
                case 3:
                    searchProductsByType(scanner);
                    break;
                case 4:
                    orderProduct(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a product
    private static void addProduct(Scanner scanner) {
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product price: ");
        double productPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product type: ");
        String productType = scanner.nextLine();

        productIds[productCount] = productId;
        productNames[productCount] = productName;
        productPrices[productCount] = productPrice;
        productTypes[productCount] = productType;
        productCount++;

        System.out.println("Product added successfully.");
    }

    // Method to remove a product
    private static void removeProduct(Scanner scanner) {
        System.out.print("Enter product ID to remove: ");
        String productId = scanner.nextLine();

        for (int i = 0; i < productCount; i++) {
            if (productIds[i].equals(productId)) {
                // Shift remaining products to the left
                for (int j = i; j < productCount - 1; j++) {
                    productIds[j] = productIds[j + 1];
                    productNames[j] = productNames[j + 1];
                    productPrices[j] = productPrices[j + 1];
                    productTypes[j] = productTypes[j + 1];
                }
                productCount--;
                System.out.println("Product removed successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // Method to update a product
    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter product ID to update: ");
        String productId = scanner.nextLine();

        for (int i = 0; i < productCount; i++) {
            if (productIds[i].equals(productId)) {
                System.out.print("Enter new product name: ");
                String productName = scanner.nextLine();
                System.out.print("Enter new product price: ");
                double productPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new product type: ");
                String productType = scanner.nextLine();

                productNames[i] = productName;
                productPrices[i] = productPrice;
                productTypes[i] = productType;

                System.out.println("Product updated successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // Method to search for a product
    private static void searchProduct(Scanner scanner) {
        System.out.print("Enter product name to search: ");
        String productName = scanner.nextLine();

        for (int i = 0; i < productCount; i++) {
            if (productNames[i].toLowerCase().contains(productName.toLowerCase())) {
                System.out.println("Product ID: " + productIds[i]);
                System.out.println("Product Name: " + productNames[i]);
                System.out.println("Product Price: $" + productPrices[i]);
                System.out.println("Product Type: " + productTypes[i]);
                System.out.println();
            }
        }
    }

    // Method to view sales analytics
    private static void viewSalesAnalytics(Scanner scanner) {
        System.out.print("Enter period (day/week/month/year): ");
        String period = scanner.nextLine();

        LocalDate now = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = now;

        switch (period.toLowerCase()) {
            case "day":
                startDate = now;
                break;
            case "week":
                startDate = now.minusWeeks(1);
                break;
            case "month":
                startDate = now.minusMonths(1);
                break;
            case "year":
                startDate = now.minusYears(1);
                break;
            default:
                System.out.println("Invalid period.");
                return;
        }

        double totalSales = 0;
        int totalQuantity = 0;

        for (int i = 0; i < orderCount; i++) {
            if ((orderDates[i].isEqual(startDate) || orderDates[i].isAfter(startDate))
                    && orderDates[i].isBefore(endDate.plusDays(1))) {
                totalSales += orderQuantities[i] * getProductPrice(orderProductIds[i]);
                totalQuantity += orderQuantities[i];
            }
        }

        System.out.println("Sales Analytics from " + startDate + " to " + endDate);
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Quantity Sold: " + totalQuantity);
    }

    // Method to view products for consumers
    private static void viewProducts() {
        for (int i = 0; i < productCount; i++) {
            System.out.println("Product ID: " + productIds[i]);
            System.out.println("Product Name: " + productNames[i]);
            System.out.println("Product Price: $" + productPrices[i]);
            System.out.println("Product Type: " + productTypes[i]);
            System.out.println();
        }
    }

    // Method to search products by price for consumers
    private static void searchProductsByPrice(Scanner scanner) {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < productCount; i++) {
            if (productPrices[i] >= minPrice && productPrices[i] <= maxPrice) {
                System.out.println("Product ID: " + productIds[i]);
                System.out.println("Product Name: " + productNames[i]);
                System.out.println("Product Price: $" + productPrices[i]);
                System.out.println("Product Type: " + productTypes[i]);
                System.out.println();
            }
        }
    }

    // Method to search products by type for consumers
    private static void searchProductsByType(Scanner scanner) {
        System.out.print("Enter product type to search: ");
        String productType = scanner.nextLine();

        for (int i = 0; i < productCount; i++) {
            if (productTypes[i].toLowerCase().contains(productType.toLowerCase())) {
                System.out.println("Product ID: " + productIds[i]);
                System.out.println("Product Name: " + productNames[i]);
                System.out.println("Product Price: $" + productPrices[i]);
                System.out.println("Product Type: " + productTypes[i]);
                System.out.println();
            }
        }
    }

    // Method to order a product for consumers
    private static void orderProduct(Scanner scanner) {
        System.out.print("Enter product ID to order: ");
        String productId = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < productCount; i++) {
            if (productIds[i].equals(productId)) {
                String orderId = "O" + (orderCount + 1);
                LocalDate orderDate = LocalDate.now();

                orderIds[orderCount] = orderId;
                orderProductIds[orderCount] = productId;
                orderQuantities[orderCount] = quantity;
                orderDates[orderCount] = orderDate;
                orderCount++;

                System.out.println("Order placed successfully. Order ID: " + orderId);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // Method to get product price by product ID
    private static double getProductPrice(String productId) {
        for (int i = 0; i < productCount; i++) {
            if (productIds[i].equals(productId)) {
                return productPrices[i];
            }
        }
        return 0;
    }

    // Methods to load data from files
    private static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                userIds[userCount] = values[0];
                userNames[userCount] = values[1];
                userPasswords[userCount] = values[2];
                userCount++;
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private static void loadProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                productIds[productCount] = values[0];
                productNames[productCount] = values[1];
                productPrices[productCount] = Double.parseDouble(values[2]);
                productTypes[productCount] = values[3];
                productCount++;
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private static void loadOrders() {
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                orderIds[orderCount] = values[0];
                orderProductIds[orderCount] = values[1];
                orderQuantities[orderCount] = Integer.parseInt(values[2]);
                orderDates[orderCount] = LocalDate.parse(values[3], formatter);
                orderCount++;
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    // Methods to save data to files
    private static void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (int i = 0; i < userCount; i++) {
                pw.println(userIds[i] + "," + userNames[i] + "," + userPasswords[i]);
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void saveProducts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
            for (int i = 0; i < productCount; i++) {
                pw.println(productIds[i] + "," + productNames[i] + "," + productPrices[i] + "," + productTypes[i]);
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    private static void saveOrders() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ORDERS_FILE))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 0; i < orderCount; i++) {
                pw.println(orderIds[i] + "," + orderProductIds[i] + "," + orderQuantities[i] + ","
                        + orderDates[i].format(formatter));
            }
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }
}
