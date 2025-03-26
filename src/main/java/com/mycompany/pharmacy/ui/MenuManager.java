package com.mycompany.pharmacy.ui;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.handler.AdminHandler;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.model.Admin;
import com.mycompany.pharmacy.model.Customer;

import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner = new Scanner(System.in);
    private final MedicineHandler medicineHandler;
    private final PrescriptionManager prescriptionManager;

    public MenuManager(MedicineHandler medicineHandler) {
        this.medicineHandler = medicineHandler;
        this.prescriptionManager = new PrescriptionManager(medicineHandler);
    }

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
            System.out.println("6. Update Profile");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> adminHandler.handleAddUser();
                case 2 -> adminHandler.handleUpdateUser();
                case 3 -> adminHandler.handleDeleteUser();
                case 4 -> adminHandler.handleViewUsers();
                case 5 -> adminHandler.handleManageSettings();
                case 6 -> updateProfile(admin, "admin");
                case 0 -> {
                    AuthService.logout(admin);
                    System.out.println("✅ You have been logged out.");
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    public void showCustomerMenu(Customer customer) {
        customer.loadPrescriptions(prescriptionManager);
        
        int choice;
        do {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Browse & Add Medicines");
            System.out.println("2. View Cart");
            System.out.println("3. Place Order");
            System.out.println("4. View Order History");
            System.out.println("5. Cancel Order");
            System.out.println("6. Update Profile");
            System.out.println("7. View Prescriptions");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    MedicineSearchCLI searchCLI = new MedicineSearchCLI(medicineHandler, customer);
                    searchCLI.search();
                }
                case 2 -> customer.getCart().viewCart();
                case 3 -> {
                    if (customer.getCart().isEmpty()) {
                        System.out.println("❌ Your cart is empty!");
                    } else {
                        customer.placeOrder();
                    }
                }
                case 4 -> customer.viewOrderHistory();
                case 5 -> customer.cancelOrder();
                case 6 -> updateProfile(customer, "customer");
                case 7 -> customer.viewPrescriptions();
                case 0 -> {
                    AuthService.logout(customer);
                    System.out.println("✅ You have been logged out. Your cart will remain saved.");
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void updateProfile(com.mycompany.pharmacy.model.User user, String role) {
        try {
            System.out.print("Enter new email (leave blank to keep current): ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter new password (leave blank to keep current): ");
            String newPassword = scanner.nextLine();

            newEmail = newEmail.isBlank() ? null : newEmail;
            newPassword = newPassword.isBlank() ? null : newPassword;

            user.updateProfile(newEmail, newPassword, role);
            System.out.println("✅ Profile updated successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error updating profile.");
            e.printStackTrace();
        }
    }
}
