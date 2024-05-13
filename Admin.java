import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public static void main(String[] args) {
        ArrayList<String> categories = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        // Admin can add categories
        while (true) {
            System.out.print("Enter a category (or type 'exit' to finish adding categories): ");
            String category = scanner.nextLine();
            if (category.equalsIgnoreCase("exit")) {
                break; // Exit the loop if the admin types 'exit'
            }
            categories.add(category);
        }

        // Write categories to a file
        try (FileWriter file = new FileWriter("Admin.txt")) {
            for (String category : categories) {
                file.write(category + System.lineSeparator()); // Add line separator after each category
            }
            System.out.println("Categories added by the admin have been saved to Admin.txt.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        scanner.close();
    }
}
