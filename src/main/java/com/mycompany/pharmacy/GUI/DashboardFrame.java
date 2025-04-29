package com.mycompany.pharmacy.GUI;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    protected static final Color PRIMARY_COLOR = new Color(129, 152, 218); // Codewars red #B1361E
    protected static final Color HOVER_COLOR = new Color(120, 123, 181);
    protected static final Color BACKGROUND_COLOR = Color.WHITE;
    protected static final Color TEXT_COLOR = new Color(51, 51, 51);
    protected static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    protected static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    protected JPanel sidebarPanel;
    protected JPanel contentPanel;
    protected JLabel userLabel;
    protected String userEmail;
    protected String userRole;

    public DashboardFrame(String title, String userRole) {
        this.userRole = userRole;
        setTitle(title);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Content
        contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    protected JPanel setupProfilePanel(String userEmail) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(BACKGROUND_COLOR);
        JLabel title = new JLabel("Profile Management", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        titlePanel.add(title);

        // Profile Card Panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Profile Icon Panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(Color.WHITE);
        
        // Create circular panel for profile icon
        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(230, 230, 250)); // Light lavender background
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        circlePanel.setPreferredSize(new Dimension(80, 80));
        circlePanel.setLayout(new GridBagLayout());
        
        // Add emoji to circle panel
        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        circlePanel.add(iconLabel);
        
        iconPanel.add(circlePanel);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Role Label
        JLabel roleLabel = new JLabel(userRole);
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        roleLabel.setForeground(PRIMARY_COLOR);
        infoPanel.add(roleLabel, gbc);

        // Email
        JLabel emailLabel = new JLabel(userEmail);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(100, 100, 100));
        infoPanel.add(emailLabel, gbc);

        // Add components to card panel
        cardPanel.add(iconPanel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(infoPanel);
        cardPanel.add(Box.createVerticalStrut(30));

        // Center the card panel
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setBackground(BACKGROUND_COLOR);
        centeringPanel.add(cardPanel);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(centeringPanel, BorderLayout.CENTER);

        return panel;
    }

    protected JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setForeground(TEXT_COLOR);
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
}
