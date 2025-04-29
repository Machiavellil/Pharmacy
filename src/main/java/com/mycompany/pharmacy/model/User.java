package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.auth.PasswordReset;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class User {

    protected String email;
    protected String password;
    private LocalDateTime registrationDate;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.registrationDate = LocalDateTime.now();
    }

    public void updateProfile(String newEmail, String newPassword, String role) {
        String oldEmail = this.email;

        if (newEmail != null && !newEmail.isBlank()) {
            this.email = newEmail;
        }
        if (newPassword != null && !newPassword.isBlank()) {
            this.password = newPassword;
        }

        try {
            AuthService.updateUserInFile(oldEmail, this.email, newPassword, role);
        } catch (IOException e) {
            System.out.println("❌ Failed to update user profile in the database.");
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void resetPassword(String newPassword) {
        try {
            AuthService.updateUserInFile(this.email, this.email, newPassword, getRole());
            this.password = newPassword;
            System.out.println("✅ Password reset successful");
        } catch (IOException e) {
            System.out.println("❌ Failed to reset password");
            e.printStackTrace();
        }
    }

    public boolean verifyPassword(String password) {
        try {
            String role = AuthService.login(this.email, password);
            return role != null;
        } catch (IOException e) {
            System.out.println("❌ Failed to verify password");
            e.printStackTrace();
            return false;
        }
    }
}
