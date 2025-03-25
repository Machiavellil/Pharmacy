package com.mycompany.pharmacy;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.Admin;
import com.mycompany.pharmacy.model.Customer;
import com.mycompany.pharmacy.ui.MenuManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        MenuManager menuManager = new MenuManager();

        MedicineHandler medicineHandler = new MedicineHandler();
        Customer customer = new Customer("Customer ID", "ds");
        customer.browseMedicines(medicineHandler);

//        System.out.println("=== Pharmacy Management System ===");
//        System.out.print("Enter Email: ");
//        String email = scanner.nextLine();
//        System.out.print("Enter Password: ");
//        String password = scanner.nextLine();
//
//        try {
//            String role = AuthService.login(email, password);
//            if (role != null) {
//                // Check if user is an admin
//                if (role.equalsIgnoreCase("admin")) {
//                    Admin admin1 = new Admin(email, password);
//                    menuManager.showAdminMenu(admin1);
//                } else {
//                    System.out.println("❌ Access denied. Only Admins can log in here.");
//                }
//            } else {
//                System.out.println("❌ Login failed. Please check your credentials.");
//            }
//        } catch (IOException e) {
//            System.out.println("❌ An error occurred during login: " + e.getMessage());
//        }
//
//        scanner.close();
    }
}
