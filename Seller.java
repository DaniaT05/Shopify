import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Seller {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> products = new ArrayList<>();
        
        int choice = 1;
        while (choice!=0)
            System.out.println("Enter 1 to add product/s, 2 to update product/s, 3 to remove product/s, 4 to view orders and 5 to view today's, this week's and this month's statistics.");
            System.out.println("Enter 0 to exit");
        switch (choice) {
            case 1: {
                // Seller can add products
                while (true) {
                    System.out.print("Enter a product (or type 'exit' to finish adding products): ");
                    String product = scanner.nextLine();
                    if (product.equalsIgnoreCase("exit")) {
                        break; // Exit the loop if the admin types 'exit'
                    }
                    products.add(product);
                }

                // Write products to a file
                try (FileWriter file = new FileWriter("Seller.txt")) {
                    for (String product : products) {
                        file.write(product + System.lineSeparator()); // Add line separator after each category
                    }
                    System.out.println("Product have been saved to Seller.txt.");
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
                break;
            }
            case 0: {
                break;
            }
            default:
                break;
        }
        scanner.close();
    }
}
