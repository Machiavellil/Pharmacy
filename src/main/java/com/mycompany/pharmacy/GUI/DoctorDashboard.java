package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DoctorDashboard extends DashboardFrame {
    private final Doctor doctor;
    private JPanel mainContent;
    private CardLayout cardLayout;
    private DefaultTableModel prescriptionTableModel;
    private JTable prescriptionTable;

    public DoctorDashboard(Doctor doctor) {
        super("Doctor Dashboard", "Doctor");
        this.doctor = doctor;

        // Setup main content with CardLayout
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(BACKGROUND_COLOR);
        contentPanel.add(mainContent, BorderLayout.CENTER);

        // Add panels
        setupPrescriptionPanel();
        setupQueryPanel();
        setupPatientHistoryPanel();
        setupProfilePanel();

        setupBottomBar();
    }

    private void setupPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Prescription Management", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);

        // Table
        prescriptionTableModel = new DefaultTableModel(
            new Object[]{"Patient Email", "Medicine", "Dosage", "Date"}, 0
        );
        prescriptionTable = new JTable(prescriptionTableModel);
        styleTable(prescriptionTable);
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        buttonPanel.add(createButton("Add Prescription", this::addPrescription));
        buttonPanel.add(createButton("Update Prescription", this::updatePrescription));
        buttonPanel.add(createButton("Remove Prescription", this::removePrescription));
        buttonPanel.add(createButton("Refresh", this::loadPrescriptions));

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainContent.add(panel, "prescriptions");
        loadPrescriptions();
    }

    private void setupQueryPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Send Query to Pharmacist", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);

        // Query input area
        JTextArea queryArea = new JTextArea();
        queryArea.setFont(TEXT_FONT);
        queryArea.setLineWrap(true);
        queryArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(queryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enter your query"));

        // Previous queries area
        JTextArea previousQueries = new JTextArea();
        previousQueries.setEditable(false);
        previousQueries.setFont(TEXT_FONT);
        JScrollPane previousScrollPane = new JScrollPane(previousQueries);
        previousScrollPane.setBorder(BorderFactory.createTitledBorder("Previous Queries"));

        // Split pane for query input and previous queries
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, previousScrollPane);
        splitPane.setResizeWeight(0.3);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton sendButton = createButton("Send Query", () -> {
            String query = queryArea.getText().trim();
            if (!query.isEmpty()) {
                sendQuery(query);
                queryArea.setText("");
                loadPreviousQueries(previousQueries);
            }
        });
        
        JButton refreshButton = createButton("Refresh", () -> loadPreviousQueries(previousQueries));
        
        buttonPanel.add(sendButton);
        buttonPanel.add(refreshButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainContent.add(panel, "queries");
        loadPreviousQueries(previousQueries);
    }

    private void setupPatientHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Patient History", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);

        // Patient selection
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JTextField patientEmailField = new JTextField(20);
        JButton searchButton = createButton("Search", () -> {
            loadPatientHistory(patientEmailField.getText().trim());
        });
        searchPanel.add(new JLabel("Patient Email:"));
        searchPanel.add(patientEmailField);
        searchPanel.add(searchButton);

        // History display
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(TEXT_FONT);
        JScrollPane scrollPane = new JScrollPane(historyArea);

        panel.add(title, BorderLayout.NORTH);
        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        mainContent.add(panel, "history");
    }

    private void setupProfilePanel() {
        JPanel panel = setupProfilePanel(doctor.getEmail());
        
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

        bottomBar.add(createToolbarButton("Prescriptions", () -> showPanel("prescriptions")));
        bottomBar.add(createToolbarButton("Send Query", () -> showPanel("queries")));
        bottomBar.add(createToolbarButton("Patient History", () -> showPanel("history")));
        bottomBar.add(createToolbarButton("Profile", () -> showPanel("profile")));
        bottomBar.add(createToolbarButton("Logout", this::logout));

        contentPanel.add(bottomBar, BorderLayout.SOUTH);
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

    private void loadPrescriptions() {
        prescriptionTableModel.setRowCount(0);
        String prescriptionsFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(prescriptionsFile))) {
            String line;
            String currentPatient = "";
            String currentDoctor = "";
            String currentDosage = "";
            String currentDate = "";
            StringBuilder medicines = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                if (line.startsWith("MED:")) {
                    // Add medicine to the list
                    if (medicines.length() > 0) {
                        medicines.append(", ");
                    }
                    medicines.append(line.substring(4).trim());
                } else if (line.equals("END")) {
                    // Add the complete prescription to the table
                    if (!currentPatient.isEmpty()) {
                        prescriptionTableModel.addRow(new Object[]{
                            currentPatient,
                            medicines.toString(),
                            currentDosage,
                            currentDate
                        });
                    }
                    // Reset for next prescription
                    medicines = new StringBuilder();
                } else if (!line.startsWith("END")) {
                    // This is the prescription header line
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        currentPatient = parts[0].trim();
                        currentDoctor = parts[1].trim();
                        currentDosage = parts[2].trim();
                        currentDate = parts.length > 3 ? parts[3].trim() : "N/A";
                        
                        // If this is a simple format line (no MED: and END markers)
                        if (parts.length == 4 && !currentDoctor.startsWith("Dr.")) {
                            prescriptionTableModel.addRow(new Object[]{
                                currentPatient,
                                currentDoctor, // In this case, the "doctor" field is actually the medicine
                                currentDosage,
                                currentDate
                            });
                            // Reset variables
                            currentPatient = "";
                            medicines = new StringBuilder();
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading prescriptions: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPrescription() {
        JTextField patientIdField = new JTextField();
        JTextField patientEmailField = new JTextField();
        JTextField medicineField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Patient Email:"));
        panel.add(patientEmailField);
        panel.add(new JLabel("Medicine:"));
        panel.add(medicineField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Prescription",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String patientId = patientIdField.getText().trim();
            String patientEmail = patientEmailField.getText().trim();
            String medicine = medicineField.getText().trim();

            if (patientId.isEmpty() || patientEmail.isEmpty() || medicine.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String prescriptionsFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(prescriptionsFile, true))) {
                    // Write in the exact format: patientId,doctorName,patientEmail
                    writer.write(String.format("%s,%s,%s%n", patientId, "doc", patientEmail));
                    writer.write("MED:" + medicine + "\n");
                    writer.write("END\n");
                }
                loadPrescriptions();
                JOptionPane.showMessageDialog(this, "Prescription added successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error adding prescription: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a prescription to update.",
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField patientField = new JTextField((String) prescriptionTable.getValueAt(selectedRow, 0));
        JTextField medicineField = new JTextField((String) prescriptionTable.getValueAt(selectedRow, 1));
        JTextField dosageField = new JTextField((String) prescriptionTable.getValueAt(selectedRow, 2));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Patient Email:"));
        panel.add(patientField);
        panel.add(new JLabel("Medicine:"));
        panel.add(medicineField);
        panel.add(new JLabel("Dosage:"));
        panel.add(dosageField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Prescription",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String patient = patientField.getText().trim();
            String medicine = medicineField.getText().trim();
            String dosage = dosageField.getText().trim();

            if (patient.isEmpty() || medicine.isEmpty() || dosage.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String prescriptionsFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";
                List<String> allLines = Files.readAllLines(Paths.get(prescriptionsFile));
                List<String> newLines = new ArrayList<>();
                boolean found = false;
                
                for (int i = 0; i < allLines.size(); i++) {
                    String line = allLines.get(i);
                    String[] parts = line.split(",");
                    
                    if (parts.length >= 3 && parts[0].equals(prescriptionTable.getValueAt(selectedRow, 0))) {
                        // Skip old prescription
                        found = true;
                        while (i < allLines.size() && !allLines.get(i).equals("END")) {
                            i++;
                        }
                    } else {
                        newLines.add(line);
                    }
                }
                
                // Add updated prescription
                newLines.add(String.format("%s,%s,%s", patient, doctor.getEmail(), dosage));
                newLines.add("MED:" + medicine);
                newLines.add("END");
                
                Files.write(Paths.get(prescriptionsFile), newLines);
                loadPrescriptions();
                JOptionPane.showMessageDialog(this, "Prescription updated successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error updating prescription: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removePrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a prescription to remove.",
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to remove this prescription?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String prescriptionsFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";
                List<String> allLines = Files.readAllLines(Paths.get(prescriptionsFile));
                List<String> newLines = new ArrayList<>();
                boolean found = false;
                
                for (int i = 0; i < allLines.size(); i++) {
                    String line = allLines.get(i);
                    String[] parts = line.split(",");
                    
                    if (parts.length >= 3 && parts[0].equals(prescriptionTable.getValueAt(selectedRow, 0))) {
                        // Skip prescription to be removed
                        found = true;
                        while (i < allLines.size() && !allLines.get(i).equals("END")) {
                            i++;
                        }
                    } else {
                        newLines.add(line);
                    }
                }
                
                Files.write(Paths.get(prescriptionsFile), newLines);
                loadPrescriptions();
                JOptionPane.showMessageDialog(this, "Prescription removed successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error removing prescription: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sendQuery(String query) {
        String queriesFile = "src/main/java/com/mycompany/pharmacy/database/doctor_queries.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(queriesFile, true))) {
            writer.write(String.format("Dr. (%s): %s%n",
                doctor.getEmail(), query));
            JOptionPane.showMessageDialog(this, "Query sent successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error sending query: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPreviousQueries(JTextArea queryArea) {
        queryArea.setText("");
        String queriesFile = "src/main/java/com/mycompany/pharmacy/database/doctor_queries.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(queriesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(doctor.getEmail())) {
                    queryArea.append(line + "\n");
                }
            }
            if (queryArea.getText().isEmpty()) {
                queryArea.append("No previous queries found.");
            }
        } catch (IOException e) {
            queryArea.append("Error loading queries: " + e.getMessage());
        }
    }

    private void loadPatientHistory(String patientEmail) {
        // Implementation would involve reading from a patient history file
        // and displaying the relevant information
        JOptionPane.showMessageDialog(this, "Patient history loaded for: " + patientEmail);
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

            if (!doctor.verifyPassword(currentPassword)) {
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
                doctor.updateProfile(null, newPassword, "doctor");
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

    private JButton createToolbarButton(String text, Runnable action) {
        JButton button = createButton(text, action);
        button.setPreferredSize(new Dimension(160, 40));
        return button;
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
