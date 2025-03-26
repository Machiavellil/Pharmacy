/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacy.auth;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author young
 */
public class PasswordReset {
    private String userEmail;

    //grant permission to reset password
    public void requestReset(String email)
    {
        System.out.println("Permission granted");

        userEmail = email;
    }

    //update password to the new password
    public void updatePassword(String newPassword)
    {
        final String userFilePath = "src/main/java/com/mycompany/pharmacy/database/users.txt";

        List<String> lines = new ArrayList<>();

        boolean found = false;

        AuthService authService = new AuthService();

        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length >= 3) {

                    if (data[0].equals(userEmail)) {

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

            System.out.println("⚠️ User with email '" + userEmail + "' not found.");

        }
    }
}
