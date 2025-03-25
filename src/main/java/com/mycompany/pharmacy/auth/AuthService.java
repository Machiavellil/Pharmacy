package com.mycompany.pharmacy.auth;

import com.mycompany.pharmacy.model.User;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // Converts each byte to hex
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found");
        }
    }

    public static String login(String email, String password) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mycompany/pharmacy/database/users.txt"));
        String line;
        String hashedInputPassword = hashPassword(password); // Hash the entered password

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 3) {
                String emailf = data[0];
                String passwordf = data[1]; // stored hashed password
                String rolef = data[2];

                if (emailf.equals(email)) {
                    if (passwordf.equals(hashedInputPassword)) {
                        br.close();
                        System.out.println("✅ Welcome " + rolef);
                        return rolef;
                    } else {
                        br.close();
                        System.out.println("❌ Incorrect password");
                        return null;
                    }
                }
            }
        }
        br.close();
        System.out.println("❌ This user does not exist");
        return null;
    }

    public void register(User user, String role) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/mycompany/pharmacy/database/users.txt", true)); // Append mode
        String hashedPassword = hashPassword(user.getPassword());
        bw.write(user.getEmail() + "," + hashedPassword + "," + role);
        bw.newLine();
        bw.close();
        System.out.println("✅ User registered successfully as " + role);
    }

    public static void logout(User user) {
        System.out.println("✅ " + user.getEmail() + " logged out successfully");
    }

    public void resetPassword(String email) {
    }
}
