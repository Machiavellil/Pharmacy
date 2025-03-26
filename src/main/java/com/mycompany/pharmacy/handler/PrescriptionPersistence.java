package com.mycompany.pharmacy.handler;

import com.mycompany.pharmacy.model.Medicine;
import com.mycompany.pharmacy.model.Prescription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionPersistence {
    private static final String PRESCRIPTION_FILE = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";
    private final MedicineHandler medicineHandler;

    public PrescriptionPersistence(MedicineHandler medicineHandler) {
        this.medicineHandler = medicineHandler;
        createDatabaseDirectory();
    }

    private void createDatabaseDirectory() {
        File databaseDir = new File("src/main/java/com/mycompany/pharmacy/database");
        if (!databaseDir.exists()) {
            databaseDir.mkdirs();
        }
    }

    public void savePrescriptions(List<Prescription> prescriptions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRESCRIPTION_FILE))) {
            for (Prescription prescription : prescriptions) {
                writer.println(prescription.getPrescriptionId() + "," +
                             prescription.getDoctorName() + "," + 
                             prescription.getPatientName());
                
                for (Medicine med : prescription.getPrescribedMedicines()) {
                    writer.println("MED:" + med.getName());
                }
                writer.println("END"); // End of prescription marker
            }
            System.out.println("✅ Prescriptions saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving prescriptions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Prescription> loadPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        File file = new File(PRESCRIPTION_FILE);
        
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("📄 Created new prescriptions database file.");
            } catch (IOException e) {
                System.out.println("❌ Error creating prescriptions file: " + e.getMessage());
                e.printStackTrace();
            }
            return prescriptions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Prescription currentPrescription = null;
            List<Medicine> currentMedicines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                // Skip comments
                if (line.startsWith("#")) {
                    continue;
                }

                if (line.equals("END")) {
                    currentMedicines.forEach(currentPrescription::addMedicine);
                    prescriptions.add(currentPrescription);
                    currentMedicines.clear();
                    continue;
                }

                if (line.startsWith("MED:")) {
                    String medicineName = line.substring(4);
                    Medicine medicine = medicineHandler.searchMedicineByName(medicineName);
                    if (medicine != null) {
                        currentMedicines.add(medicine);
                    } else {
                        System.out.println("⚠️ Warning: Medicine '" + medicineName + "' not found in inventory.");
                    }
                } else {
                    // Handle prescription header line
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        currentPrescription = new Prescription(parts[1], parts[2]);
                    }
                }
            }
            System.out.println("✅ Prescriptions loaded successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error loading prescriptions: " + e.getMessage());
            e.printStackTrace();
        }

        return prescriptions;
    }
} 