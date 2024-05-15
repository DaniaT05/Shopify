import java.util.Scanner;

public class AccountManagement {

    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    private static final int MAX_ACCOUNTS = 100;
    private static String[] userIds = new String[MAX_ACCOUNTS];
    private static String[] userPasswords = new String[MAX_ACCOUNTS];
    private static String[] sellerIds = new String[MAX_ACCOUNTS];
    private static String[] sellerPasswords = new String[MAX_ACCOUNTS];
    private static int userCount = 0;
    private static int sellerCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create an Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccountMenu(scanner);
                    break;
                case 2:
                    loginMenu(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccountMenu(Scanner scanner) {
        System.out.println("Choose account type to create:");
        System.out.println("1. Seller");
        System.out.println("2. Customer");

        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createAccount(scanner, true);
                break;
            case 2:
                createAccount(scanner, false);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void createAccount(Scanner scanner, boolean isSeller) {
        if (isSeller) {
            if (sellerCount >= MAX_ACCOUNTS) {
                System.out.println("Cannot create more seller accounts.");
                return;
            }
            System.out.print("Enter Seller ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            sellerIds[sellerCount] = id;
            sellerPasswords[sellerCount] = password;
            sellerCount++;
            System.out.println("Seller account created successfully!");
        } else {
            if (userCount >= MAX_ACCOUNTS) {
                System.out.println("Cannot create more customer accounts.");
                return;
            }
            System.out.print("Enter Customer ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            userIds[userCount] = id;
            userPasswords[userCount] = password;
            userCount++;
            System.out.println("Customer account created successfully!");
        }
    }

    private static void loginMenu(Scanner scanner) {
        System.out.println("Choose login type:");
        System.out.println("1. Admin");
        System.out.println("2. Seller");
        System.out.println("3. Customer");

        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                login(scanner, "admin");
                break;
            case 2:
                login(scanner, "seller");
                break;
            case 3:
                login(scanner, "customer");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void login(Scanner scanner, String loginType) {
        System.out.print("Enter " + loginType + " ID: ");
        String enteredId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String enteredPassword = scanner.nextLine();

        boolean authenticated = false;
        if ("admin".equals(loginType)) {
            authenticated = ADMIN_ID.equals(enteredId) && ADMIN_PASSWORD.equals(enteredPassword);
        } else if ("seller".equals(loginType)) {
            for (int i = 0; i < sellerCount; i++) {
                if (sellerIds[i].equals(enteredId) && sellerPasswords[i].equals(enteredPassword)) {
                    authenticated = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < userCount; i++) {
                if (userIds[i].equals(enteredId) && userPasswords[i].equals(enteredPassword)) {
                    authenticated = true;
                    break;
                }
            }
        }

        if (authenticated) {
            System.out.println("Login successful!");
            if ("admin".equals(loginType)) {
                showAdminOptions(scanner);
            } else {
                System.out.println("Logged in as " + loginType);
                // Add functionality for sellers and customers
            }
        } else {
            System.out.println("Invalid ID or Password. Access denied.");
        }
    }

    private static void showAdminOptions(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Options:");
            System.out.println("1. View Products by Category");
            System.out.println("2. View Registered Sellers");
            System.out.println("3. View Registered Users");
            System.out.println("4. Search User");
            System.out.println("5. Search Seller");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProductsByCategory(scanner);
                    break;
                case 2:
                    viewRegisteredSellers();
                    break;
                case 3:
                    viewRegisteredUsers();
                    break;
                case 4:
                    searchUser(scanner);
                    break;
                case 5:
                    searchSeller(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Placeholder methods for admin functionalities
    private static void viewProductsByCategory(Scanner scanner) {
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.println("Displaying products in category: " + category);
    }

    private static void viewRegisteredSellers() {
        System.out.println("Displaying registered sellers...");
        for (int i = 0; i < sellerCount; i++) {
            System.out.println("Seller ID: " + sellerIds[i]);
        }
    }

    private static void viewRegisteredUsers() {
        System.out.println("Displaying registered users...");
        for (int i = 0; i < userCount; i++) {
            System.out.println("User ID: " + userIds[i]);
        }
    }

    private static void searchUser(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < userCount; i++) {
            if (userIds[i].equals(userId)) {
                System.out.println("User found: " + userIds[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("User not found.");
        }
    }

    private static void searchSeller(Scanner scanner) {
        System.out.print("Enter Seller ID: ");
        String sellerId = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < sellerCount; i++) {
            if (sellerIds[i].equals(sellerId)) {
                System.out.println("Seller found: " + sellerIds[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Seller not found.");
        }
    }
}
