package com.mycompany.pharmacy.model;

import java.io.*;
import java.util.*;

public class Admin extends User {

    private final String userFilePath = "src/main/java/com/mycompany/pharmacy/database/users.txt";

    public Admin(String id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Add user with role
    public void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath, true))) {
            writer.write(user.getEmail() + "," + user.getPassword() + "," + user.getClass().getSimpleName());
            writer.newLine();
            System.out.println("✅ User " + user.getEmail() + " added as " + user.getClass().getSimpleName());
        } catch (IOException e) {
            System.out.println("❌ Error adding user: " + e.getMessage());
        }
    }

    // Update user by email
    public void updateUser(String email, String newPassword) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(email)) {
                    lines.add(email + "," + newPassword + "," + data[2]); // update password only
                    found = true;
                } else {
                    lines.add(line);
                }
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
                System.out.println("✅ User updated.");
            } catch (IOException e) {
                System.out.println("❌ Error writing file: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ User not found.");
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
