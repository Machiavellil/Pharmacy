package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.model.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminDashboard extends DashboardFrame {
    private Admin admin;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private List<User> allUsers;

    public AdminDashboard(Admin admin) {
        super("Admin Dashboard", "Admin");
        this.admin = admin;

        JPanel dashboardPanel = new JPanel(new BorderLayout(20, 20));
        dashboardPanel.setBackground(BACKGROUND_COLOR);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + admin.getEmail(), JLabel.CENTER);
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(TEXT_COLOR);
        dashboardPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBackground(BACKGROUND_COLOR);

        // Chart Panel
        ChartPanel chartPanel = new ChartPanel(createUserChart());
        chartPanel.setPreferredSize(new Dimension(600, 300));
        chartPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(chartPanel, BorderLayout.NORTH);

        // User Table
        tableModel = new DefaultTableModel(new Object[]{"Email", "Password", "Role"}, 0);
        userTable = new JTable(tableModel);
        styleTable(userTable);
        JScrollPane scrollPane = new JScrollPane(userTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Load users
        loadUsers();

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        buttonPanel.add(createButton("Add User", e -> addUser()));
        buttonPanel.add(createButton("Update User", e -> updateUser()));
        buttonPanel.add(createButton("Delete User", e -> deleteUser()));
        buttonPanel.add(createButton("Update Profile", e -> updateProfile()));
        buttonPanel.add(createButton("Reset Password", e -> resetPassword()));
        buttonPanel.add(createButton("Logout", e -> logout()));

        dashboardPanel.add(centerPanel, BorderLayout.CENTER);
        dashboardPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(dashboardPanel, BorderLayout.CENTER);
    }

    private JFreeChart createUserChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            List<User> users = AuthService.getAllUsers();
            Map<String, Long> roleCount = users.stream()
                    .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
            for (Map.Entry<String, Long> entry : roleCount.entrySet()) {
                dataset.addValue(entry.getValue(), "Users", entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart("Users by Role", "Role", "Count", dataset);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(BACKGROUND_COLOR);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, PRIMARY_COLOR);

        return chart;
    }

    private void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setBackground(BACKGROUND_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void loadUsers() {
        try {
            allUsers = AuthService.getAllUsers();
            tableModel.setRowCount(0);
            for (User user : allUsers) {
                tableModel.addRow(new Object[]{user.getEmail(), user.getPassword(), user.getRole()});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 40));
        button.addActionListener(action);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void addUser() {
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"admin", "doctor", "pharmacist", "customer", "delivery"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                User newUser = createUserObject(emailField.getText(), passwordField.getText(), (String) roleBox.getSelectedItem());
                AuthService authService = new AuthService();
                authService.register(newUser, newUser.getRole());
                loadUsers();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            String oldEmail = (String) tableModel.getValueAt(selectedRow, 0);
            JTextField emailField = new JTextField(oldEmail);
            JTextField passwordField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
            JComboBox<String> roleBox = new JComboBox<>(new String[]{"admin", "doctor", "pharmacist", "customer", "delivery"});
            roleBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2));

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Role:"));
            panel.add(roleBox);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update User", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    AuthService.updateUserInFile(oldEmail, emailField.getText(), passwordField.getText(), (String) roleBox.getSelectedItem());
                    loadUsers();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a user to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            String email = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete user " + email + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    AuthService.deleteUser(email);
                    loadUsers();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a user to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateProfile() {
        JPanel profilePanel = setupProfilePanel(admin.getEmail());
        
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

                if (!admin.verifyPassword(currentPassword)) {
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
                    admin.updateProfile(null, newPassword, "admin");
                    JOptionPane.showMessageDialog(this, "Password updated successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error updating password: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        changePasswordBtn.setPreferredSize(new Dimension(200, 40));
        buttonPanel.add(changePasswordBtn);
        
        // Add button panel to the main panel
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Create a dialog to show the profile
        JDialog profileDialog = new JDialog(this, "Profile Management", true);
        profileDialog.setSize(600, 500);
        profileDialog.setLocationRelativeTo(this);
        profileDialog.add(profilePanel);
        profileDialog.setVisible(true);
    }

    private void resetPassword() {
        JOptionPane.showMessageDialog(this, "Reset Password clicked.");
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }

    private User createUserObject(String email, String password, String role) {
        return switch (role.toLowerCase()) {
            case "admin" -> new Admin(email, password);
            case "doctor" -> new Doctor(email, password);
            case "pharmacist" -> new Pharmacist(email, password, null);
            case "customer" -> new Customer(email, password);
            case "delivery" -> new DeliveryAgent(email, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
