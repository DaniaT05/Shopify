                                                                              Programming Fundamentals
                                                                              E-COMMERCE STORE PROJECT
Group Members:
Ayesha Mansoor: FA23-BDS-006
Dania Taj: FA23-BDS-007
Sakina Abbas: FA23-BDS-055
Naqiya Ezzy: FA23-BDS-064
SUBMISSION DATE 29-MAY-202
                                                                          E-commerce Management System Report
Introduction
The E-commerce Management System is an extensive platform designed to manage the interactions between buyers, sellers, and administrators in an online marketplace. The system allows for efficient handling of product listings, order processing, and user account management, ensuring smooth operations and enhanced user experience. It leverages a text-based file storage system to keep track of data, which simplifies deployment and maintenance while making the codebase easy to understand and extend.
Benefits/Scope of the Project
The E-commerce Management System offers several benefits, including:
1. Centralized Management: Efficiently manage products, orders, and user accounts from a single platform.
2. User-Friendly Interface: Simplified and clear prompts for users to interact with the system.
3. Scalability: The system can be extended with additional functionalities as needed.
4. Security: Ensures that only authorized users can perform specific actions.
5. Transparency: Provides clear messages and logs for user actions and system status.
Limitations of the Project
1. File-Based Storage: Using text files for data storage can lead to issues with data integrity and scalability.
2. Concurrency Issues: Lack of file-locking mechanisms can lead to data corruption when accessed concurrently by multiple users.
3. Basic Error Handling: Error handling is basic, mainly printing messages to the console.
4. No User Authentication: The system lacks authentication and authorization mechanisms.
5. Manual Data Entry: Data entry through the console can be error-prone and less user-friendly.
Module Done by:
Seller Module: Dania Taj
Buyer Module: Sakina Abbas
Admin Module: Naqiya Ezzy and Ayesha Mansoor
Buyer Module
This module allows the buyer to view product categories, search the product they want to purchase, view their personal information.
1. viewCategories()
Purpose: To display all product categories available in the store.
Input: No input is required from the user.
Output: Displays categories list. If an error occurs while reading the file, it outputs an error message.
Functionality:
Reads the `Category.txt` file.
Prints each line as a category.
Handles any `IOException` by printing an error message.
Reason for Use: To allow buyers to see all the categories of products available for purchase.
2. searchProducts()
Purpose: To search for a product by name.
Input: Prompts the user to enter a product name to search.
Output: Prints the details of the products matching the search query. If no products are found, it prints "No products found." If an error occurs, it prints an error message.
Functionality: Prompts the user to enter a product name.
Reads the `Products.txt` file.
Checks each line to see if it contains the search query (case-insensitive).
Prints the details of matching products.
Reason for Use: To help buyers find specific products they are interested in.
3. viewProducts()
Purpose: To display all products available in the system.
Input: No specific input is required from the user.
Output: Prints the list of products. If an error occurs while reading the file, it prints an error message.
Functionality:
Reads the `Products.txt` file.
Prints the details of each product.
Reason for Use: To allow buyers to browse all available products.
4. viewProfile()
Purpose:To display the buyer's profile information.
Input: No input.
Output: Prints the buyer's profile information.
Functionality: Prints the ID, first name, last name, email, address, and status from the userData array.
Reason for Use: To provide buyers with a quick way to view their profile information.
5. addToCart()
Purpose:To add a product to the buyer's cart.
Input: Prompts the user to enter the product ID and quantity to add to the cart.
Output: Prints a message indicating whether the product was successfully added to the cart or if an error occurred.
Functionality: Prompts the user to enter a product ID and quantity.
Reads the `Products.txt` file to verify product availability.
Writes the product and quantity to the cart.txt file if available.
Reason for Use: To enable buyers to add products to their cart for future checkout.
6. viewCart()
Purpose: To display the contents of the buyer's cart.
Input: No input.
Output: Prints the contents of the buyer's cart. If an error occurs while reading the file, it prints an error message.
Functionality: Reads the `Cart.txt` file.
Prints the details of each item in the cart that belongs to the logged-in buyer.
Reason for Use: To allow buyers to view the items they have added to their cart.
7. checkout()
Purpose: To finalize the order and clear the cart.
Input: No input.
Output: Prints the total bill amount and a message indicating whether the order was successfully placed. If an error occurs, it prints an error message.
Functionality: Reads the `Cart.txt` file to get the items in the cart.
Prompts that the order has been placed successfully..
Writes the order details to the `Orders.txt` file.
Clears the `Cart.txt` file after placing the order.
Reason for Use: To enable buyers to place an order for the items in their cart and process the payment.
8. logout()
Purpose: To log the buyer out of the system.
Input: No specific input is required from the user.
Output: Prints a message indicating successful logout.
Functionality: Simply prints a logout confirmation message.
Reason for Use: To allow buyers to securely log out of their account.
Conclusion
Each function in this file is designed to assist buyers in managing their shopping activities effectively. The functions perform file I/O operations to read from and write to various text files, handle exceptions gracefully, and provide meaningful feedback to the user
