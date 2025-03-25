package com.mycompany.pharmacy.ui;

import com.mycompany.pharmacy.handler.AdminHandler;
import com.mycompany.pharmacy.model.Admin;
import java.util.Scanner;

public class MenuManager {

    private final Scanner scanner = new Scanner(System.in);

    public void showAdminMenu(Admin admin) {
        AdminHandler adminHandler = new AdminHandler(admin);
        int choice;

        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. View All Users");
            System.out.println("5. Manage Settings");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> adminHandler.handleAddUser();
                case 2 -> adminHandler.handleUpdateUser();
                case 3 -> adminHandler.handleDeleteUser();
                case 4 -> adminHandler.handleViewUsers();
                case 5 -> adminHandler.handleManageSettings();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("❌ Invalid choice. Try again.");
            }

        } while (choice != 0);
    }
}
