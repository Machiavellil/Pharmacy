package com.mycompany.pharmacy;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.*;
import com.mycompany.pharmacy.ui.MenuManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        MedicineHandler medicineHandler = new MedicineHandler();
        MenuManager menuManager = new MenuManager(medicineHandler);
        AuthService authService = new AuthService();

        System.out.println("=== Pharmacy Management System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        if (option.equals("2")) {
            System.out.println("\n=== User Registration ===");
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Role (admin, customer, doctor, pharmacist, supplier, delivery): ");
            String role = scanner.nextLine().toLowerCase();

            // Create the correct user object based on the role
            User newUser;
            switch (role) {
                case "admin" -> newUser = new Admin(email, password);
                case "customer" -> newUser = new Customer(email, password);
//                case "doctor" -> newUser = new Doctor(email, password);
//                case "pharmacist" -> newUser = new Pharmacist(email, password);
//                case "supplier" -> newUser = new Supplier(email, password);
//                case "delivery" -> newUser = new DeliveryAgent(email, password);
                default -> {
                    System.out.println("❌ Invalid role! Registration failed.");
                    scanner.close();
                    return; // Exit registration
                }
            }

            authService.register(newUser, role);
        } else if (option.equals("1")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            try {
                String role = AuthService.login(email, password);
                if (role != null) {
                    if (role.equalsIgnoreCase("admin")) {
                        Admin admin = new Admin(email, password);
                        menuManager.showAdminMenu(admin);
                    } else if (role.equalsIgnoreCase("customer")) {
                        Customer customer = new Customer(email, password);
                        menuManager.showCustomerMenu(customer);
                    } else {
                        System.out.println("✅ Login successful as " + role + ". No specific menu implemented yet.");
                    }
                } else {
                    System.out.println("❌ Login failed. Please check your credentials.");
                }
            } catch (IOException e) {
                System.out.println("❌ An error occurred during login: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Invalid choice. Exiting...");
        }

        scanner.close();
    }
}
