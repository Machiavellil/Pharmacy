package com.mycompany.pharmacy.handler;

import com.mycompany.pharmacy.model.*;
import java.util.Scanner;

public class AdminHandler {
    final private Admin admin;
    final private Scanner scanner;

    public AdminHandler(Admin admin) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    // Add User with role
    public void handleAddUser() {
        System.out.println("=== Add New User ===");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Select Role (Doctor/Pharmacist/Supplier/DeliveryAgent): ");
        String role = scanner.nextLine();

        User newUser = null;
        switch (role) {
            case "Admin":
                newUser = new Admin( email,password);
                break;
            default:
                System.out.println("❌ Invalid role.");
                return;
        }

        admin.addUser(newUser);
    }

    public void handleUpdateUser() {
        System.out.println("=== Update User Password ===");
        System.out.print("Enter Email of the user to update: ");
        String email = scanner.nextLine();

        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();

        admin.updateUser(email, newPassword);
    }

    public void handleDeleteUser() {
        System.out.println("=== Delete User ===");
        System.out.print("Enter Email of the user to delete: ");
        String email = scanner.nextLine();

        admin.deleteUser(email);
    }

    public void handleViewUsers() {
        admin.viewAllUsers();
    }

    public void handleManageSettings() {
        admin.manageSettings();
    }
}
