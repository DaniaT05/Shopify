# E-Commerce Management System

## Course Information
**Course**: Programming Fundamentals
**Project Type**: E-Commerce Project
**Group Members**
* Ayesha Mansoor – FA23-BDS-006
* Dania Taj – FA23-BDS-007
* Sakina Abbas – FA23-BDS-055
* Naqiya Ezzy – FA23-BDS-064
**Submission Date**: 29-May-2024

---

## Introduction
The **E-Commerce Management System** is a text-based Java project designed to manage interactions between buyers, sellers, and administrators in an online marketplace. It allows efficient handling of product listings, order processing, and user account management. Data is stored using simple text files, making the project lightweight and easy to understand.

---

## Benefits and Scope
1. Centralized management of products, orders, and user accounts.
2. User-friendly interface with clear console prompts.
3. Scalable design that can be extended with new features.
4. Basic security measures ensuring only authorized users perform restricted actions.
5. Transparency through clear system messages and logs.

---

## Limitations
1. File-based storage may cause data integrity and scalability issues.
2. No file-locking mechanism, leading to possible concurrency problems.
3. Basic error handling (console messages only).
4. No authentication or authorization implemented.
5. Manual data entry through the console, which can be error-prone.

---

## Module Responsibilities
* **Seller Module**: Dania Taj
* **Buyer Module**: Sakina Abbas
* **Admin Module**: Naqiya Ezzy and Ayesha Mansoor

---

## Buyer Module Functions
### 1. `viewCategories()`
* Displays all product categories by reading `Category.txt`.
* Helps buyers explore available categories.

### 2. `searchProducts()`
* Searches products by name in `Products.txt`.
* Prints details if matches are found, otherwise shows "No products found."

### 3. `viewProducts()`
* Displays all products listed in `Products.txt`.
* Allows buyers to browse the marketplace.

### 4. `viewProfile()`
* Displays buyer profile information (ID, name, email, address, status).

### 5. `addToCart()`
* Adds products to the cart by verifying product ID and quantity.
* Updates `Cart.txt` with the chosen products.

### 6. `viewCart()`
* Displays the contents of the buyer’s cart from `Cart.txt`.

### 7. `checkout()`
* Finalizes orders and writes details to `Orders.txt`.
* Clears `Cart.txt` after placing the order.

### 8. `logout()`
* Logs the buyer out with a confirmation message.

---

## Conclusion
This project demonstrates how basic file handling and console-based interactions can be combined to create an **E-Commerce Management System**. While limited in terms of scalability and security, the implementation showcases core programming concepts and provides a foundation for future enhancements such as authentication, database integration, and advanced error handling.

---
