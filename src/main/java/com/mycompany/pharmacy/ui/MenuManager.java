package com.mycompany.pharmacy.ui;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.handler.AdminHandler;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.handler.PharmSystem;
import com.mycompany.pharmacy.model.*;
import com.mycompany.pharmacy.model.DeliveryAgent;
import java.io.IOException;
import java.util.*;

import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner = new Scanner(System.in);
    private final MedicineHandler medicineHandler;
    private final PrescriptionManager prescriptionManager;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    private static final String UNDERLINE = "\u001B[4m";

    public MenuManager(MedicineHandler medicineHandler) {
        this.medicineHandler = medicineHandler;
        this.prescriptionManager = new PrescriptionManager(medicineHandler);
    }

    public void showAdminMenu(Admin admin) {
        AdminHandler adminHandler = new AdminHandler(admin);
        int choice;

        do {
            System.out.println(CYAN + "\n=== Admin Menu ===" + RESET);
            System.out.println(BOLD + "1. " + RESET + "Add User");
            System.out.println(BOLD + "2. " + RESET + "Update User");
            System.out.println(BOLD + "3. " + RESET + "Delete User");
            System.out.println(BOLD + "4. " + RESET + "View All Users");
            System.out.println(BOLD + "5. " + RESET + "Manage Settings");
            System.out.println(BOLD + "6. " + RESET + "Update Profile");
            System.out.println(BOLD + "7. " + RESET + "Reset Password");
            System.out.println(BOLD + "0. " + RESET + "Logout");
            System.out.print(YELLOW + "Enter choice: " + RESET);

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> adminHandler.handleAddUser();
                case 2 -> adminHandler.handleUpdateUser();
                case 3 -> adminHandler.handleDeleteUser();
                case 4 -> adminHandler.handleViewUsers();
                case 5 -> adminHandler.handleManageSettings();
                case 6 -> updateProfile(admin, "admin");
                case 7 -> {
                    System.out.print(YELLOW + "Enter your new password: " + RESET);
                    String newPassword = scanner.nextLine();
                    admin.resetPassword(newPassword);
                    System.out.println(GREEN + "✅ Password updated!" + RESET);
                }
                case 0 -> {
                    AuthService.logout(admin);
                    System.out.println(GREEN + "✅ You have been logged out." + RESET);
                }
                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }

        } while (choice != 0);
    }

    public void showCustomerMenu(Customer customer) {
        customer.loadPrescriptions(prescriptionManager);
        
        int choice;
        do {
            System.out.println(CYAN + "\n=== Customer Menu ===" + RESET);
            System.out.println(BOLD + "1. " + RESET + "Browse & Add Medicines");
            System.out.println(BOLD + "2. " + RESET + "View Cart");
            System.out.println(BOLD + "3. " + RESET + "Place Order");
            System.out.println(BOLD + "4. " + RESET + "View Order History");
            System.out.println(BOLD + "5. " + RESET + "Cancel Order");
            System.out.println(BOLD + "6. " + RESET + "Update Profile");
            System.out.println(BOLD + "7. " + RESET + "View Prescriptions");
            System.out.println(BOLD + "8. " + RESET + "Reset Password");
            System.out.println(BOLD + "0. " + RESET + "Logout");
            System.out.print(YELLOW + "Enter choice: " + RESET);
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    MedicineSearchCLI searchCLI = new MedicineSearchCLI(medicineHandler, customer);
                    searchCLI.search();
                }
                case 2 -> customer.getCart().viewCart();
                case 3 -> {
                    if (customer.getCart().isEmpty()) {
                        System.out.println(RED + "❌ Your cart is empty!" + RESET);
                    } else {
                        customer.placeOrder();
                    }
                }
                case 4 -> customer.viewOrderHistory();
                case 5 -> customer.cancelOrder();
                case 6 -> updateProfile(customer, "customer");
                case 7 -> customer.viewPrescriptions();
                case 8 -> {
                    System.out.print(YELLOW + "Enter new password: " + RESET);
                    String newPassword = scanner.nextLine();
                    customer.resetPassword(newPassword);
                    System.out.println(GREEN + "✅ Password updated successfully!" + RESET);
                }
                case 0 -> {
                    AuthService.logout(customer);
                    System.out.println(GREEN + "✅ You have been logged out. Your cart will remain saved." + RESET);
                }
                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }
        } while (choice != 0);
    }

    private void updateProfile(com.mycompany.pharmacy.model.User user, String role) {
        try {
            System.out.print(YELLOW + "Enter new email (leave blank to keep current): " + RESET);
            String newEmail = scanner.nextLine();
            System.out.print(YELLOW + "Enter new password (leave blank to keep current): " + RESET);
            String newPassword = scanner.nextLine();

            newEmail = newEmail.isBlank() ? null : newEmail;
            newPassword = newPassword.isBlank() ? null : newPassword;

            user.updateProfile(newEmail, newPassword, role);
            System.out.println(GREEN + "✅ Profile updated successfully." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "❌ Error updating profile." + RESET);
            e.printStackTrace();
        }
    }

    public void showDoctorMenu(Doctor doc) throws IOException {
        int choice;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println(CYAN + "\n=== Doctor Menu ===" + RESET);
            System.out.println(BOLD + "1. " + RESET + "View Patient Profile");
            System.out.println(BOLD + "2. " + RESET + "View Consultations");
            System.out.println(BOLD + "3. " + RESET + "Consult Pharmacy");
            System.out.println(BOLD + "4. " + RESET + "Reset Password");
            System.out.println(BOLD + "0. " + RESET + "Logout");
            System.out.print(YELLOW + "Enter your choice: " + RESET);

            //If input is not a number, display an error message.
            while (!input.hasNextInt()) {
                System.out.println(RED + "❌ Invalid input. Please enter a number." + RESET);
                input.next();
            }

            choice = input.nextInt();
            input.nextLine(); // Clear newline from buffer

            switch (choice) {
                case 1 -> {
                    System.out.print(YELLOW + "Please enter the patient's email: " + RESET);
                    String patientEmail = input.nextLine();
                    
                    System.out.println(PURPLE + "\n=== Patient's Profile ===" + RESET);
                    System.out.println(BOLD + "1. " + RESET + "View Patient History");
                    System.out.println(BOLD + "2. " + RESET + "Add Medical Record");
                    System.out.println(BOLD + "3. " + RESET + "Write Prescription");
                    System.out.print(YELLOW + "Enter your choice: " + RESET);

                    //If input is not a number, display an error message.
                    while (!input.hasNextInt()) {
                        System.out.println(RED + "❌ Invalid input. Please enter a number." + RESET);
                        input.next();
                    }

                    int subChoice = input.nextInt();
                    input.nextLine(); // Clear buffer

                    switch (subChoice) {
                        case 1 -> doc.viewPatientHistory(patientEmail); //Display Patient's medical history.
                        case 2 -> {
                            System.out.print(YELLOW + "Enter condition: " + RESET);
                            String condition = input.nextLine();
                            System.out.print(YELLOW + "Enter prescriptions: " + RESET);
                            String prescriptions = input.nextLine();
                            System.out.print(YELLOW + "Enter allergies: " + RESET);
                            String allergies = input.nextLine();

                            //Add a record to a patient's medical records.
                            Customer patient = new Customer(patientEmail, "");
                            MedicalRecord record = new MedicalRecord(patientEmail, -1, condition, prescriptions, allergies);
                            doc.addMedicalRecord(patient, record);
                        }
                        case 3 -> {

                            //Write a prescription to a patient.
                            System.out.print(YELLOW + "Enter medicines (comma-separated): " + RESET);
                            String medicines = input.nextLine();
                            Customer patient = new Customer(patientEmail, "");
                            doc.writePrescription(patient, doc.getEmail(), medicines);
                        }
                        default -> System.out.println(RED + "❌ Invalid choice." + RESET);
                    }
                }
                case 2 -> doc.viewConsultations(); //Display queries sent to doctor.
                case 3 -> {

                    //Send a query to a pharmacy, to be received by a pharmacist.
                    System.out.print(YELLOW + "Enter your query for the pharmacy: " + RESET);
                    String query = input.nextLine();
                    doc.consultPharmacy(null, query);
                }
                case 4 -> {

                    //Reset password.
                    System.out.print(YELLOW + "Enter new password: " + RESET);
                    String newPassword = input.nextLine();
                    doc.resetPassword(newPassword);
                    System.out.println(GREEN + "✅ Password updated successfully!" + RESET);
                }
                case 0 -> {

                    //Logout
                    AuthService.logout(doc);
                    System.out.println(GREEN + "✅ You have been logged out." + RESET);
                }
                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }

        } while (choice != 0);
    }

    public void showPharmacistMenu(Pharmacist pharmacist) {
        int choice;
        do {
            System.out.println(CYAN + "\n=== Pharmacist Menu ===" + RESET);
            System.out.println(BOLD + "1. " + RESET + "Add Medicine");
            System.out.println(BOLD + "2. " + RESET + "Update Medicine Details");
            System.out.println(BOLD + "3. " + RESET + "Remove Medicine");
            System.out.println(BOLD + "4. " + RESET + "Generate Medicine Report");
            System.out.println(BOLD + "5. " + RESET + "Respond to Doctor Query");
            System.out.println(BOLD + "6. " + RESET + "Update Profile");
            System.out.println(BOLD + "7. " + RESET + "Reset Password");
            System.out.println(BOLD + "0. " + RESET + "Logout");
            System.out.print(YELLOW + "Enter choice: " + RESET);

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addMedicine(pharmacist);
                case 2 -> updateMedicine(pharmacist);
                case 3 -> removeMedicine(pharmacist);
                case 4 -> generateMedicineReport(pharmacist);
                case 5 -> respondToDoctorQuery(pharmacist);
                case 6 -> updateProfile(pharmacist, "pharmacist");
                case 7 -> {
                    System.out.print(YELLOW + "Enter new password: " + RESET);
                    String newPassword = scanner.nextLine();
                    pharmacist.resetPassword(newPassword);
                    System.out.println(GREEN + "✅ Password updated successfully!" + RESET);
                }
                case 0 -> {
                    AuthService.logout(pharmacist);
                    System.out.println(GREEN + "✅ You have been logged out." + RESET);
                }
                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }
        } while (choice != 0);
    }

    private void addMedicine(Pharmacist pharmacist) {
        System.out.print(YELLOW + "Enter Medicine ID: " + RESET);
        String id = scanner.nextLine();
        System.out.print(YELLOW + "Enter Medicine Name: " + RESET);
        String name = scanner.nextLine();
        System.out.print(YELLOW + "Enter Manufacturer: " + RESET);
        String manufacturer = scanner.nextLine();
        System.out.print(YELLOW + "Enter Price: " + RESET);
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print(YELLOW + "Enter Stock Quantity: " + RESET);
        int stockQuantity = Integer.parseInt(scanner.nextLine());
        System.out.print(YELLOW + "Enter Type (OTC/Prescription): " + RESET);
        String type = scanner.nextLine();

        Medicine medicine = new Medicine(id, name, manufacturer, price, stockQuantity, type);
        pharmacist.addMedicine(medicine);
        System.out.println(GREEN + "✅ Medicine added successfully!" + RESET);
    }

    private void updateMedicine(Pharmacist pharmacist) {
        System.out.print(YELLOW + "Enter Medicine ID to update: " + RESET);
        String id = scanner.nextLine();
        System.out.print(YELLOW + "Enter new Name: " + RESET);
        String name = scanner.nextLine();
        System.out.print(YELLOW + "Enter new Manufacturer: " + RESET);
        String manufacturer = scanner.nextLine();
        System.out.print(YELLOW + "Enter new Price: " + RESET);
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print(YELLOW + "Enter new Stock Quantity: " + RESET);
        int stockQuantity = Integer.parseInt(scanner.nextLine());
        System.out.print(YELLOW + "Enter new Type (OTC/Prescription): " + RESET);
        String type = scanner.nextLine();

        Medicine updatedMedicine = new Medicine(id, name, manufacturer, price, stockQuantity, type);
        pharmacist.updateMedicineDetails(id, updatedMedicine);
        System.out.println(GREEN + "✅ Medicine details updated successfully!" + RESET);
    }

    private void removeMedicine(Pharmacist pharmacist) {
        System.out.print(YELLOW + "Enter Medicine ID to remove: " + RESET);
        String id = scanner.nextLine();
        pharmacist.removeMedicine(id);
        System.out.println(GREEN + "✅ Medicine removed successfully!" + RESET);
    }

    private void generateMedicineReport(Pharmacist pharmacist) {
        System.out.print(YELLOW + "Enter Medicine Name: " + RESET);
        String name = scanner.nextLine();
        pharmacist.generateMedicineReport(name);
    }

    private void respondToDoctorQuery(Pharmacist pharmacist) {
        System.out.print(YELLOW + "Enter Doctor's Email: " + RESET);
        String doctorEmail = scanner.nextLine();
        System.out.print(YELLOW + "Enter your response: " + RESET);
        String response = scanner.nextLine();
        Doctor doctor = new Doctor(doctorEmail, "");
        pharmacist.respondToDoctorQuery(doctor, response);
    }

    public void showDeliveryAgentMenu(DeliveryAgent deliveryAgent) {
        int choice;
        do {
            System.out.println(CYAN + "\n=== Delivery Agent Menu ===" + RESET);
            System.out.println(BOLD + "1. " + RESET + "View Assigned Deliveries");
            System.out.println(BOLD + "2. " + RESET + "Update Order Status");
            System.out.println(BOLD + "3. " + RESET + "Track Delivery Location");
            System.out.println(BOLD + "4. " + RESET + "Manage Delivery Schedule");
            System.out.println(BOLD + "5. " + RESET + "Estimate Delivery Time");
            System.out.println(BOLD + "6. " + RESET + "Update Profile");
            System.out.println(BOLD + "7. " + RESET + "Reset Password");
            System.out.println(BOLD + "0. " + RESET + "Logout");
            System.out.print(YELLOW + "Enter choice: " + RESET);

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> deliveryAgent.viewAssignedDeliveries();
                case 2 -> {
                    System.out.print(YELLOW + "Enter Order ID: " + RESET);
                    int orderId = Integer.parseInt(scanner.nextLine());
                    System.out.print(YELLOW + "Enter new Status: " + RESET);
                    String status = scanner.nextLine();
                    deliveryAgent.updateOrderStatus(orderId, status);
                }
                case 3 -> {
                    System.out.print(YELLOW + "Enter Order ID: " + RESET);
                    int orderId = Integer.parseInt(scanner.nextLine());
                    deliveryAgent.trackDeliveryLocation(orderId);
                }
                case 4 -> deliveryAgent.manageDeliverySchedule();
                case 5 -> {
                    System.out.print(YELLOW + "Enter Order ID: " + RESET);
                    int orderId = Integer.parseInt(scanner.nextLine());
                    System.out.print(YELLOW + "Was the delivery successful? (true/false): " + RESET);
                    boolean isSuccessful = Boolean.parseBoolean(scanner.nextLine());
                    String failureReason = "";
                    if (!isSuccessful) {
                        System.out.print(YELLOW + "Enter failure reason: " + RESET);
                        failureReason = scanner.nextLine();
                    }
                    deliveryAgent.estimatedDeliveryTime(orderId, isSuccessful, failureReason);
                }
                case 6 -> updateProfile(deliveryAgent, "delivery");
                case 7 -> {
                    System.out.print(YELLOW + "Enter new password: " + RESET);
                    String newPassword = scanner.nextLine();
                    deliveryAgent.resetPassword(newPassword);
                    System.out.println(GREEN + "✅ Password updated successfully!" + RESET);
                }
                case 0 -> {
                    AuthService.logout(deliveryAgent);
                    System.out.println(GREEN + "✅ You have been logged out." + RESET);
                }
                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }
        } while (choice != 0);
    }
}





