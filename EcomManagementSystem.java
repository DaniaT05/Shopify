import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class EcomManagementSystem {
    public static void main(String[] args) {

        FileWriter file = new FileWriter("Users.txt");
        
        // Start system
        while (true) {
            System.out.println("Welcome to Ecommerce Management System!");
            System.out.println("Do you want to log in or sign up?");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");

            int choice = Integer.parseInt(System.console().readLine("Enter your choice: "));

            switch (choice) {
                case 1:
                    
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
