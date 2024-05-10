public class EcomManagementSystem {
    public static void main(String[] args) {
        // Initialize system
        CustomerModule customerModule = new CustomerModule();
        SellerModule sellerModule = new SellerModule();
        AdminModule adminModule = new AdminModule();

        // Start system
        while (true) {
            System.out.println("Welcome to Ecommerce Management System!");
            System.out.println("1. Customer Module");
            System.out.println("2. Seller Module");
            System.out.println("3. Admin Module");
            System.out.println("4. Exit");

            int choice = Integer.parseInt(System.console().readLine("Enter your choice: "));

            switch (choice) {
                case 1:
                    customer.run();
                    break;
                case 2:
                    seller.run();
                    break;
                case 3:
                    admin.run();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
