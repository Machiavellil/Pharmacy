package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.auth.AuthService;
import com.mycompany.pharmacy.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final AuthService authService;
    private final AnimatedLogoPanel logoPanel;

    private static final Color PRIMARY_COLOR = new Color(129, 152, 218); // Codewars red #B1361E
    private static final Color HOVER_COLOR = new Color(120, 123, 181); // Lighter red
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color ACCENT_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public LoginFrame() {
        setTitle("Pharmacy Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        authService = new AuthService();

        // Main panel with white background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Left panel with logo and animation
        logoPanel = new AnimatedLogoPanel();
        mainPanel.add(logoPanel, BorderLayout.WEST);

        // Right panel with login form
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Title Section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Pharmko", JLabel.LEFT);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel subtitleLabel = new JLabel("Pharmacy Management System", JLabel.LEFT);
        subtitleLabel.setFont(SUBTITLE_FONT);
        subtitleLabel.setForeground(TEXT_COLOR);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.CENTER);

        rightPanel.add(titlePanel, BorderLayout.NORTH);

        // Form Section
        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

// Create and add input fields
        emailField = createTextField("Email");
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = createPasswordField("Password");
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

// Button Section
        loginButton = createButton("Login");
        registerButton = createButton("Register");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // << VERY IMPORTANT

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        formPanel.add(buttonPanel);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> showRegistrationDialog());

        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(TEXT_FONT);
        textField.setPreferredSize(new Dimension(400, 50));
        textField.setMaximumSize(new Dimension(400, 50));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        textField.setBackground(BACKGROUND_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add placeholder
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
            }
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });

        return textField;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(TEXT_FONT);
        passwordField.setPreferredSize(new Dimension(400, 50));
        passwordField.setMaximumSize(new Dimension(400, 50));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        passwordField.setBackground(BACKGROUND_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add placeholder
        passwordField.setEchoChar((char)0);
        passwordField.setText(placeholder);
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent evt) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char)0);
                    passwordField.setText(placeholder);
                }
            }
        });

        return passwordField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 45));
        button.setMaximumSize(new Dimension(180, 45));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            String role = AuthService.login(email, password);
            if (role != null) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

                // Open the correct dashboard
                switch (role.toLowerCase()) {
                    case "admin" -> new AdminDashboard(new Admin(email, password)).setVisible(true);
                    case "customer" -> new CustomerDashboard(new Customer(email, password)).setVisible(true);
//                    case "doctor" -> new DoctorDashboard(new Doctor(email, password)).setVisible(true);
                    case "pharmacist" -> new PharmacistDashboard(new Pharmacist(email, password, null)).setVisible(true);
//                    case "delivery" -> new DeliveryAgentDashboard(new DeliveryAgent(email, password)).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error during login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showRegistrationDialog() {
        JDialog registerDialog = new JDialog(this, "User Registration", true);
        registerDialog.setSize(500, 500);
        registerDialog.setLocationRelativeTo(this);
        registerDialog.getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField emailField = createTextField("Email");
        JPasswordField passwordField = createPasswordField("Password");

        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "customer", "doctor", "pharmacist", "delivery"});
        roleComboBox.setFont(TEXT_FONT);
        roleComboBox.setBackground(BACKGROUND_COLOR);
        roleComboBox.setForeground(TEXT_COLOR);
        roleComboBox.setMaximumSize(new Dimension(400, 50));
        roleComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        roleComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JButton registerBtn = createButton("Register");
        registerBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(roleComboBox);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(registerBtn);

        registerBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            User newUser = createUser(email, password, role);
            if (newUser != null) {
                try {
                    authService.register(newUser, role);
                    JOptionPane.showMessageDialog(registerDialog, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    registerDialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(registerDialog, "Error during registration: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerDialog.add(panel);
        registerDialog.setVisible(true);
    }

    private User createUser(String email, String password, String role) {
        return switch (role) {
            case "admin" -> new Admin(email, password);
            case "customer" -> new Customer(email, password);
            case "doctor" -> new Doctor(email, password);
            case "pharmacist" -> new Pharmacist(email, password, null);
            case "delivery" -> new DeliveryAgent(email, password);
            default -> null;
        };
    }

//    private void showUserDashboard(String email, String password, String role) {
//        switch (role.toLowerCase()) {
//            case "admin" -> {
//                Admin admin = new Admin(email, password);
//                new AdminDashboard(admin).setVisible(true);
//            }
//            case "customer" -> {
//                Customer customer = new Customer(email, password);
//                new CustomerDashboard(customer).setVisible(true);
//            }
////            case "doctor" -> {
////                Doctor doctor = new Doctor(email, password);
////                new DoctorDashboard(doctor).setVisible(true);
////            }
////            case "pharmacist" -> {
////                Pharmacist pharmacist = new Pharmacist(email, password, null);
////                new PharmacistDashboard(pharmacist).setVisible(true);
////            }
////            case "delivery" -> {
////                DeliveryAgent deliveryAgent = new DeliveryAgent(email, password);
////                new DeliveryAgentDashboard(deliveryAgent).setVisible(true);
////            }
//        }
  //  }

    // Inner class for animated logo
    private class AnimatedLogoPanel extends JPanel {
        private double angle = 0;
        private Timer animationTimer;

        public AnimatedLogoPanel() {
            setBackground(PRIMARY_COLOR);
            setPreferredSize(new Dimension(400, getHeight()));

            // Start animation timer
            animationTimer = new Timer(20, e -> {
                angle += 0.02;
                if (angle >= 2 * Math.PI) {
                    angle = 0;
                }
                repaint();
            });
            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 4;

            // Draw animated pill shape
            g2d.setColor(Color.WHITE);
            g2d.rotate(angle, centerX, centerY);

            // Draw pill body
            g2d.fill(new RoundRectangle2D.Double(
                centerX - radius, centerY - radius/2,
                radius * 2, radius,
                radius/2, radius/2
            ));

            // Draw pill ends
            g2d.fill(new Ellipse2D.Double(
                centerX - radius, centerY - radius/2,
                radius, radius
            ));
            g2d.fill(new Ellipse2D.Double(
                centerX, centerY - radius/2,
                radius, radius
            ));

            // Draw cross
            g2d.setColor(PRIMARY_COLOR);
            g2d.setStroke(new BasicStroke(radius/4));
            g2d.drawLine(centerX - radius/2, centerY, centerX + radius/2, centerY);
            g2d.drawLine(centerX, centerY - radius/2, centerX, centerY + radius/2);
        }
    }
}


