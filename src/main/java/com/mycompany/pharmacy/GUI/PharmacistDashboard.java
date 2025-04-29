package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class PharmacistDashboard extends DashboardFrame {
    private final Pharmacist pharmacist;
    private final MedicineHandler medicineHandler;
    private JPanel mainContent;
    private CardLayout cardLayout;
    private DefaultTableModel medicineTableModel;
    private JTable medicineTable;

    public PharmacistDashboard(Pharmacist pharmacist) {
        super("Pharmacist Dashboard", "Pharmacist");
        this.pharmacist = pharmacist;
        this.medicineHandler = new MedicineHandler();

        // Setup main content with CardLayout
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(BACKGROUND_COLOR);
        contentPanel.add(mainContent, BorderLayout.CENTER);

        // Add panels
        setupMedicineManagementPanel();
        setupInventoryPanel();
        setupDoctorQueriesPanel();
        setupProfilePanel();

        setupBottomBar();
    }

    private void setupMedicineManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Medicine Management", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);

        // Table
        medicineTableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Manufacturer", "Price", "Stock", "Type"}, 0
        );
        medicineTable = new JTable(medicineTableModel);
        styleTable(medicineTable);
        JScrollPane scrollPane = new JScrollPane(medicineTable);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        buttonPanel.add(createButton("Add Medicine", this::addMedicine));
        buttonPanel.add(createButton("Update Medicine", this::updateMedicine));
        buttonPanel.add(createButton("Remove Medicine", this::removeMedicine));
        buttonPanel.add(createButton("Refresh", this::loadMedicines));

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainContent.add(panel, "medicines");
        loadMedicines();
    }

    private void setupInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Inventory Management", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);

        JTextArea inventoryArea = new JTextArea();
        inventoryArea.setEditable(false);
        inventoryArea.setFont(TEXT_FONT);
        JScrollPane scrollPane = new JScrollPane(inventoryArea);

        JButton generateReportBtn = createButton("Generate Report", () -> {
            inventoryArea.setText("");
            List<Medicine> medicines = medicineHandler.getAllMedicines();
            inventoryArea.append("=== Inventory Report ===\n\n");
            for (Medicine med : medicines) {
                inventoryArea.append(String.format("Medicine: %s\n", med.getName()));
                inventoryArea.append(String.format("Stock: %d\n", med.getStockQuantity()));
                inventoryArea.append(String.format("Price: $%.2f\n", med.getPrice()));
                inventoryArea.append("------------------------\n");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(generateReportBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainContent.add(panel, "inventory");
    }

    private void setupDoctorQueriesPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Doctor Queries", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);

        JTextArea queriesArea = new JTextArea();
        queriesArea.setEditable(false);
        queriesArea.setFont(TEXT_FONT);
        JScrollPane scrollPane = new JScrollPane(queriesArea);

        // Load queries from file
        loadDoctorQueries(queriesArea);

        JButton refreshBtn = createButton("Refresh Queries", () -> loadDoctorQueries(queriesArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(refreshBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainContent.add(panel, "queries");
    }

    private void setupProfilePanel() {
        JPanel panel = setupProfilePanel(pharmacist.getEmail());
        
        // Add password change button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        JButton changePasswordBtn = createButton("Change Password", this::changePassword);
        changePasswordBtn.setPreferredSize(new Dimension(200, 40));
        buttonPanel.add(changePasswordBtn);
        
        // Add button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainContent.add(panel, "profile");
    }

    private void setupBottomBar() {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(new Color(245, 245, 245));
        bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        bottomBar.add(createToolbarButton("Medicines", () -> showPanel("medicines")));
        bottomBar.add(createToolbarButton("Inventory", () -> showPanel("inventory")));
        bottomBar.add(createToolbarButton("Doctor Queries", () -> showPanel("queries")));
        bottomBar.add(createToolbarButton("Profile", () -> showPanel("profile")));
        bottomBar.add(createToolbarButton("Logout", this::logout));

        contentPanel.add(bottomBar, BorderLayout.SOUTH);
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

    private JButton createToolbarButton(String text, Runnable action) {
        JButton button = createButton(text, action);
        button.setPreferredSize(new Dimension(160, 40));
        return button;
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
    }

    private void loadMedicines() {
        medicineTableModel.setRowCount(0);
        List<Medicine> medicines = medicineHandler.getAllMedicines();
        for (Medicine med : medicines) {
            medicineTableModel.addRow(new Object[]{
                med.getId(),
                med.getName(),
                med.getManufacturer(),
                med.getPrice(),
                med.getStockQuantity(),
                med.getType()
            });
        }
    }

    private void addMedicine() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField manufacturerField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"OTC", "Prescription"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Manufacturer:"));
        panel.add(manufacturerField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);
        panel.add(new JLabel("Type:"));
        panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Medicine", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Medicine newMedicine = new Medicine(
                    idField.getText(),
                    nameField.getText(),
                    manufacturerField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText()),
                    (String) typeBox.getSelectedItem()
                );
                medicineHandler.addMedicine(newMedicine);
                loadMedicines();
                JOptionPane.showMessageDialog(this, "Medicine added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateMedicine() {
        int selectedRow = medicineTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to update.", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) medicineTable.getValueAt(selectedRow, 0);
        Medicine medicine = medicineHandler.searchMedicineById(id);
        if (medicine == null) {
            JOptionPane.showMessageDialog(this, "Medicine not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(medicine.getName());
        JTextField manufacturerField = new JTextField(medicine.getManufacturer());
        JTextField priceField = new JTextField(String.valueOf(medicine.getPrice()));
        JTextField stockField = new JTextField(String.valueOf(medicine.getStockQuantity()));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"OTC", "Prescription"});
        typeBox.setSelectedItem(medicine.getType());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Manufacturer:"));
        panel.add(manufacturerField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);
        panel.add(new JLabel("Type:"));
        panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Medicine", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Medicine updatedMedicine = new Medicine(
                    id,
                    nameField.getText(),
                    manufacturerField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText()),
                    (String) typeBox.getSelectedItem()
                );
                medicineHandler.removeMedicine(id);
                medicineHandler.addMedicine(updatedMedicine);
                loadMedicines();
                JOptionPane.showMessageDialog(this, "Medicine updated successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeMedicine() {
        int selectedRow = medicineTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to remove.", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) medicineTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to remove this medicine?", 
            "Confirm Removal", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            medicineHandler.removeMedicine(id);
            loadMedicines();
            JOptionPane.showMessageDialog(this, "Medicine removed successfully!");
        }
    }

    private void loadDoctorQueries(JTextArea queriesArea) {
        queriesArea.setText("");
        String queriesFile = "src/main/java/com/mycompany/pharmacy/database/doctor_queries.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(queriesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queriesArea.append(line + "\n");
            }
            if (queriesArea.getText().isEmpty()) {
                queriesArea.append("No queries found.");
            }
        } catch (IOException e) {
            queriesArea.append("Error loading queries: " + e.getMessage());
        }
    }

    private void changePassword() {
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

            if (!pharmacist.verifyPassword(currentPassword)) {
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
                pharmacist.updateProfile(null, newPassword, "pharmacist");
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating password: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showPanel(String panelName) {
        cardLayout.show(mainContent, panelName);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
