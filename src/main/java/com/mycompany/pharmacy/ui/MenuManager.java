package com.mycompany.pharmacy.ui;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.handler.AdminHandler;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.handler.PharmSystem;
import com.mycompany.pharmacy.model.Admin;
import com.mycompany.pharmacy.model.Customer;
import com.mycompany.pharmacy.model.Doctor;
import com.mycompany.pharmacy.model.Pharmacist;
import com.mycompany.pharmacy.model.Prescription;

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
     public void showDoctorMenu(Doctor doc) {
        String E = " ", P = " ", Q = " ";
        String rec = " ", res = " ";
        String docName = " ", patName = " ";
        PharmSystem pharmacy = new PharmSystem(); //Object in PharmSystem.
        Prescription pres =  new Prescription(docName, patName); //Object in Prescription.
        Customer patient = new Customer(E, P); //Object in Customer.
        Pharmacist pharmacist = new Pharmacist(); //Object in Pharmacist.
        int choice;
        int choice1;
        int choice2;
        Scanner input = new Scanner(System.in);
        choice = input.nextInt();
        while (choice != 0) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Profile.");
            System.out.println("2. View Consultations.");
            System.out.println("3. Consult Pharmacy.");
            System.out.println("0. Logout.");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            /*This part displays the options in the doctor's menu, and asks the doctor
            to enter the choice.*/
            switch  (choice) {
                case 1:
                    System.out.println("Please enter the patient's email: ");
                    String e = input.nextLine();
                    pharmacy.findPatient(e);
                    //Patient's email is entered to get their profile.
                    System.out.println("\n=== Patient's Profile ===");
                    System.out.println("1. View Patient History.");
                    System.out.println("2. Add Medical Record.");
                    System.out.println("3. Write Prescription.");
                    System.out.println("Enter your choice: ");
                    choice1 = input.nextInt();
                    /*This part displays the things a doctor can access in the patient's profile,
                    and asks the doctor to enter a choice.*/
                    switch (choice1) {
                        case 1 -> doc.viewPatientHistory(patient);
                        case 2 -> doc.addMedicalRecord(patient, rec);
                        case 3 -> doc.writePrescription(patient, pres);
                        default -> System.out.println("Invalid Choice.");
                        /*After the choice is entered, respective functions from the
                        Doctor Class are called. 'Invalid Choice' is printed when a number
                        that is not from the above is entered.*/
                    }
                case 2:
                    System.out.println("\n=== Consultations ===");
                    System.out.println("1. View all Consultations.");
                    System.out.println("2. Respond to Consultation.");
                    System.out.println("Enter your choice: ");
                    choice2 = input.nextInt();
                    /*This part displays the doctor's availability to view all consultations
                    sent to him, and to respond to any of them.*/
                    switch (choice2) {
                        case 1 -> doc.viewConsultations();
                        case 2 -> doc.respondToConsultation(patient, res);
                        default -> System.out.println("Invalid Choice");
                        /*After the choice is entered, respective functions from the
                        Doctor Class are called. 'Invalid Choice' is printed when a number
                        that is not from the above is entered.*/
                    }
                case 3:
                    System.out.println("\n=== Consult the Pharmacy ===");
                    doc.consultPharmacy(pharmacist, Q);
                    /*This part lets the doctor send a consultation to the pharmacist,
                    calling the respective function from the Doctor Class.*/
                case 0:
                    AuthService.logout(doc);
                    System.out.println("You have successfully logged out!");
                    /*This part logs out the doctor from the system, calling the
                    respective function from the AuthService Class.*/
                default: System.out.println("Invalid Choice.");
                /*If the choice entered is not from the numbers shown above,
                'Invalid Choice' will be printed.*/
            }
        }
        
    }
}
