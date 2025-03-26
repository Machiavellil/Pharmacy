package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.auth.AuthService;
import java.io.IOException;

public abstract class User {

    protected String email;
    protected String password;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // General profile update with file persistence
    public void updateProfile(String newEmail, String newPassword, String role) {
        String oldEmail = this.email;

        // Update object data
        if (newEmail != null && !newEmail.isBlank()) {
            this.email = newEmail;
        }
        if (newPassword != null && !newPassword.isBlank()) {
            this.password = newPassword;
        }

        // Update the users.txt file
        try {
            AuthService.updateUserInFile(oldEmail, this.email, this.password, role);
        } catch (IOException e) {
            System.out.println("❌ Failed to update user profile in the database.");
            e.printStackTrace();
        }
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
