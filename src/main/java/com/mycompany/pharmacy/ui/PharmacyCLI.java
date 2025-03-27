/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacy.ui;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.*;
import com.mycompany.pharmacy.util.SoundManager;
import java.io.IOException;
import java.util.Scanner;

public class PharmacyCLI {
    private final Scanner scanner;
    private final MedicineHandler medicineHandler;
    private final MenuManager menuManager;
    private final AuthService authService;

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

    private static final String LOGO = """
            \u001B[36m
            ██████╗ ██╗  ██╗ █████╗ ██████╗ ███╗   ███╗ █████╗ ██████╗ ██╗   ██╗
            ██╔══██╗██║ ██╔╝██╔══██╗██╔══██╗████╗ ████║██╔══██╗██╔══██╗╚██╗ ██╔╝
            ██████╔╝█████╔╝ ███████║██████╔╝██╔████╔██║███████║██████╔╝ ╚████╔╝ 
            ██╔═══╝ ██╔═██╗ ██╔══██║██╔══██╗██║╚██╔╝██║██╔══██║██╔══██╗  ╚██╔╝  
            ██║     ██║  ██║██║  ██║██║  ██║██║ ╚═╝ ██║██║  ██║██║  ██║   ██║   
            ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   
            \u001B[33m
            ╔════════════════════════════════════════════════════════════════════════════╗
            ║                     Welcome to Pharmacy Management System                  ║
            ║                     Your Trusted Healthcare Partner                        ║
            ╚════════════════════════════════════════════════════════════════════════════╝
            """;

    public PharmacyCLI() {
        this.scanner = new Scanner(System.in);
        this.medicineHandler = new MedicineHandler();
        this.menuManager = new MenuManager(medicineHandler);
        this.authService = new AuthService();
    }

    public void start() throws IOException {
        printLogo();
        // Start background music
        SoundManager.playBackgroundMusic();
        showMainMenu();
    }

    private void printLogo() {
        System.out.println(LOGO);
        System.out.println(RESET);
    }

    private void showMainMenu() throws IOException {
        while (true) {
            System.out.println(CYAN + "\n=== Main Menu ===" + RESET);
            System.out.println(BOLD + "1. " + RESET + "Login");
            System.out.println(BOLD + "2. " + RESET + "Register");
            System.out.println(BOLD + "3. " + RESET + "Toggle Background Music");
            System.out.println(BOLD + "0. " + RESET + "Exit");
            System.out.print(YELLOW + "Choose an option: " + RESET);

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> handleLogin();
                case "2" -> handleRegistration();
                case "3" -> {
                    SoundManager.toggleBackgroundMusic();
                    System.out.println(GREEN + "✅ Background music toggled!" + RESET);
                }
                case "0" -> {
                    SoundManager.stopBackgroundMusic();
                    System.out.println(GREEN + "\nThank you for using Pharmacy Management System!" + RESET);
                    return;
                }
                default -> System.out.println(RED + "❌ Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void handleLogin() throws IOException {
        System.out.println(PURPLE + "\n=== Login ===" + RESET);
        System.out.print(YELLOW + "Enter Email: " + RESET);
        String email = scanner.nextLine();
        System.out.print(YELLOW + "Enter Password: " + RESET);
        String password = scanner.nextLine();

        try {
            String role = AuthService.login(email, password);
            if (role != null) {
                System.out.println(GREEN + "✅ Login successful!" + RESET);
                handleUserMenu(email, password, role);
            } else {
                System.out.println(RED + "❌ Login failed. Please check your credentials." + RESET);
            }
        } catch (IOException e) {
            System.out.println(RED + "❌ An error occurred during login: " + e.getMessage() + RESET);
        }
    }

    private void handleRegistration() throws IOException {
        System.out.println(PURPLE + "\n=== User Registration ===" + RESET);
        System.out.print(YELLOW + "Enter Email: " + RESET);
        String email = scanner.nextLine();
        System.out.print(YELLOW + "Enter Password: " + RESET);
        String password = scanner.nextLine();
        System.out.print(YELLOW + "Enter Role (admin, customer, doctor, pharmacist, delivery): " + RESET);
        String role = scanner.nextLine().toLowerCase();

        User newUser = createUser(email, password, role);
        if (newUser != null) {
            authService.register(newUser, role);
            System.out.println(GREEN + "✅ Registration successful! You can now login." + RESET);
        }
    }

    private User createUser(String email, String password, String role) {
        return switch (role) {
            case "admin" -> new Admin(email, password);
            case "customer" -> new Customer(email, password);
            case "doctor" -> new Doctor(email, password);
            case "pharmacist" -> new Pharmacist(email, password, medicineHandler);
            case "delivery" -> new DeliveryAgent(email, password);
            default -> {
                System.out.println(RED + "❌ Invalid role! Registration failed." + RESET);
                yield null;
            }
        };
    }

    private void handleUserMenu(String email, String password, String role) throws IOException {
        switch (role.toLowerCase()) {
            case "admin" -> {
                Admin admin = new Admin(email, password);
                menuManager.showAdminMenu(admin);
            }
            case "customer" -> {
                Customer customer = new Customer(email, password);
                menuManager.showCustomerMenu(customer);
            }
            case "doctor" -> {
                Doctor doctor = new Doctor(email, password);
                menuManager.showDoctorMenu(doctor);
            }
            case "pharmacist" -> {
                Pharmacist pharmacist = new Pharmacist(email, password, medicineHandler);
                menuManager.showPharmacistMenu(pharmacist);
            }
            case "delivery" -> {
                DeliveryAgent deliveryAgent = new DeliveryAgent(email, password);
                menuManager.showDeliveryAgentMenu(deliveryAgent);
            }
            default -> System.out.println(YELLOW + "✅ Login successful as " + role + ". No specific menu implemented yet." + RESET);
        }
    }
}
