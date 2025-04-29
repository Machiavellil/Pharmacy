package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.model.Customer;
import javax.swing.*;
import java.awt.*;

public class UpdateProfilePanel extends JPanel {
    private final Customer customer;

    public UpdateProfilePanel(Customer customer) {
        this.customer = customer;
        setLayout(new BorderLayout());
        setBackground(DashboardFrame.BACKGROUND_COLOR);

        // Get the profile panel from DashboardFrame
        JPanel profilePanel = new DashboardFrame("", "Customer") {}.setupProfilePanel(customer.getEmail());
        
        // Add password change button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton changePasswordBtn = createButton("Change Password", () -> {
            JPasswordField currentPasswordField = new JPasswordField();
            JPasswordField newPasswordField = new JPasswordField();
            JPasswordField confirmPasswordField = new JPasswordField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Current Password:"));
            panel.add(currentPasswordField);
            panel.add(new JLabel("New Password:"));
            panel.add(newPasswordField);
            panel.add(new JLabel("Confirm Password:"));
            panel.add(confirmPasswordField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Change Password", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!customer.verifyPassword(currentPassword)) {
                    JOptionPane.showMessageDialog(this, "Current password is incorrect!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(this, "New passwords do not match!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    customer.updateProfile(null, newPassword, "customer");
                    JOptionPane.showMessageDialog(this, "Password updated successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error updating password: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        changePasswordBtn.setPreferredSize(new Dimension(200, 40));
        buttonPanel.add(changePasswordBtn);
        
        // Add button panel to the profile panel
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the profile panel to this panel
        add(profilePanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(DashboardFrame.TEXT_FONT);
        button.setForeground(DashboardFrame.TEXT_COLOR);
        button.setBackground(DashboardFrame.PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addActionListener(e -> action.run());

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(DashboardFrame.HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DashboardFrame.PRIMARY_COLOR);
            }
        });

        return button;
    }
} 