package com.mycompany.pharmacy.GUI;

import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.model.Customer;
import com.mycompany.pharmacy.model.Prescription;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(129, 152, 218);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final Customer customer;
    private final JTextArea prescriptionsArea;
    private final PrescriptionManager prescriptionManager;
    private final String PRESCRIPTIONS_FILE = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";

    public PrescriptionsPanel(Customer customer, MedicineHandler medicineHandler) {
        this.customer = customer;
        this.prescriptionManager = new PrescriptionManager(medicineHandler);

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Your Prescriptions", JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        add(title, BorderLayout.NORTH);

        // Prescriptions Area
        prescriptionsArea = new JTextArea();
        prescriptionsArea.setEditable(false);
        prescriptionsArea.setFont(TEXT_FONT);
        prescriptionsArea.setBackground(new Color(245, 245, 245));
        prescriptionsArea.setForeground(Color.BLACK);
        prescriptionsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(prescriptionsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane, BorderLayout.CENTER);

        // Refresh Button
        JButton refreshBtn = new JButton("Refresh Prescriptions");
        refreshBtn.setFont(TEXT_FONT);
        refreshBtn.setBackground(PRIMARY_COLOR);
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> loadPrescriptions());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(refreshBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        loadPrescriptions();
    }

    private void loadPrescriptions() {
        prescriptionsArea.setText("");
        List<String[]> prescriptions = new ArrayList<>(); // Store doctor and medicine as pairs

        try (BufferedReader reader = new BufferedReader(new FileReader(PRESCRIPTIONS_FILE))) {
            String line;
            String currentDoctor = null;
            String currentMedicine = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("MED:")) {
                    currentMedicine = line.substring(4).trim(); // Remove "MED:" prefix
                } else if (line.equals("END")) {
                    // If we have both doctor and medicine, add them as a pair
                    if (currentDoctor != null && currentMedicine != null) {
                        prescriptions.add(new String[]{currentDoctor, currentMedicine});
                    }
                    // Reset for next prescription
                    currentDoctor = null;
                    currentMedicine = null;
                } else {
                    // This is the prescription header line
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[2].equals(customer.getEmail())) {
                        currentDoctor = parts[1];
                    }
                }
            }
        } catch (IOException e) {
            prescriptionsArea.append("❌ Error loading prescriptions: " + e.getMessage());
            return;
        }

        if (prescriptions.isEmpty()) {
            prescriptionsArea.append("❌ No prescriptions found for your account.");
        } else {
            prescriptionsArea.append("=== Your Prescriptions ===\n\n");
            for (String[] prescription : prescriptions) {
                prescriptionsArea.append("Doctor: " + prescription[0] + "\n");
                prescriptionsArea.append("Medicine: " + prescription[1] + "\n");
                prescriptionsArea.append("----------------------------------------\n\n");
            }
        }
    }
}
