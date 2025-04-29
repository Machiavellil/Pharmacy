package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.auth.AuthService;

import java.io.*;
import java.util.*;

public class Admin extends User {

    private final String userFilePath = "src/main/java/com/mycompany/pharmacy/database/users.txt";

    public Admin(String email, String password) {
        super(email, password);
    }

    @Override
    public String getRole() {
        return "admin";
    }


    // Add user with role
    public void addUser(User user) {
        AuthService authService = new AuthService();
        try {
            authService.register(user, user.getClass().getSimpleName());
        } catch (IOException e) {
            System.out.println("❌ Error adding user.: " + e.getMessage());
        }
    }

    // Update user by email
    public void updateUser(String email, String newPassword) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        AuthService authService = new AuthService();

        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    if (data[0].equals(email)) {
                        // 🔒 Hash the new password
                        AuthService authService1 = new AuthService();
                        String hashedPassword = authService.hashPassword(newPassword);
                        lines.add(data[0] + "," + hashedPassword + "," + data[2]);
                        found = true;
                    } else {
                        lines.add(line);
                    }
                } else {
                    System.out.println("⚠️ Skipping corrupted user entry: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            return;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath, false))) {
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
                System.out.println("✅ User updated successfully.");
            } catch (IOException e) {
                System.out.println("❌ Error writing file: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ User with email '" + email + "' not found.");
        }
    }

    // Delete user by email
    public void deleteUser(String email) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(email)) {
                    found = true;
                    continue; // Skip this user
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            return;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath))) {
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
                System.out.println("✅ User deleted.");
            } catch (IOException e) {
                System.out.println("❌ Error writing file: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ User not found.");
        }
    }

    // View all users
    public void viewAllUsers() {
        System.out.println("\n--- All Registered Users ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Email: " + data[0] + " | Role: " + data[2]);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    public void manageSettings() {
        System.out.println("⚙️ Admin managing system settings...");
    }
}
