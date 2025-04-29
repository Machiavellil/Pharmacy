package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.Customer;
import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends DashboardFrame {
    private final Customer customer;
    private JPanel mainContent;
    private CardLayout cardLayout;

    public CustomerDashboard(Customer customer) {
        super("Customer Dashboard", "Customer");
        this.customer = customer;

        MedicineHandler medicineHandler = new MedicineHandler();

        // Setup main content with CardLayout
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(BACKGROUND_COLOR);
        contentPanel.add(mainContent, BorderLayout.CENTER);

        // Add pages
        mainContent.add(new BrowseMedicinesPanel(customer, medicineHandler), "browse");
        mainContent.add(new CartPanel(customer.getCart(), customer), "cart");
        mainContent.add(new OrderHistoryPanel(customer), "orders");
        mainContent.add(new PrescriptionsPanel(customer, medicineHandler), "prescriptions");
        mainContent.add(new UpdateProfilePanel(customer), "profile");

        setupBottomBar(); // Toolbar at the bottom
    }

    private void setupBottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(new Color(245, 245, 245));
        bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        bottomBar.add(createToolbarButton("Browse Medicines", () -> showPanel("browse")));
        bottomBar.add(createToolbarButton("View Cart", () -> showPanel("cart")));
        bottomBar.add(createToolbarButton("View Orders", () -> showPanel("orders")));
        bottomBar.add(createToolbarButton("View Prescriptions", () -> showPanel("prescriptions")));
        bottomBar.add(createToolbarButton("Update Profile", this::updateProfile));
        bottomBar.add(createToolbarButton("Logout", this::logout));

        contentPanel.add(bottomBar, BorderLayout.SOUTH);    
    }

    private JButton createToolbarButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setForeground(TEXT_COLOR); // TEXT_COLOR is black (51,51,51)
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addActionListener(e -> action.run());

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void showPanel(String panelName) {
        cardLayout.show(mainContent, panelName);
    }

    private void placeOrder() {
        customer.placeOrder();
        JOptionPane.showMessageDialog(this, "✅ Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        showPanel("orders");
    }

    private void cancelOrder() {
        String orderNumber = JOptionPane.showInputDialog(this, 
            "Please enter the order number to cancel:", 
            "Cancel Order", 
            JOptionPane.QUESTION_MESSAGE);
            
        if (orderNumber != null && !orderNumber.trim().isEmpty()) {
            boolean success = customer.cancelOrder(orderNumber);
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Order #" + orderNumber + " has been canceled successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Unable to cancel order #" + orderNumber + ". It may not exist or is not cancellable.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        showPanel("orders");
    }

    private void updateProfile() {
        showPanel("profile");
    }

    private void resetPassword() {
        customer.resetPassword("newPassword123"); // Placeholder
        JOptionPane.showMessageDialog(this, "🔒 Password reset requested.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
