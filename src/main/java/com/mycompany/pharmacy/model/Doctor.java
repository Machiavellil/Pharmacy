package com.mycompany.pharmacy.model;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.handler.MedicineHandler;
import java.util.*;
import java.io.*; //Exception Handling.
import java.nio.file.*;

public class Doctor extends User {
    private String specialisation;
    private int number;
    private String condition;
    private String prescriptionFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt";
    private String medicinesFile = "src/main/java/com/mycompany/pharmacy/database/Drugs.txt";
    private ArrayList<String> consultations = new ArrayList<>();
    private final PrescriptionManager prescriptionManager;
    private final MedicineHandler medicineHandler;
    private final List<MedicalRecord> patientRecords = new ArrayList<>(); //List change to ArrayList.

    //A constructor that creates an object in Doctor and initializes it with a given email and password.
    public Doctor(String email, String password) {
        super(email, password);
        this.medicineHandler = new MedicineHandler();
        this.prescriptionManager = new PrescriptionManager(medicineHandler);
    }

    @Override
    public String getRole() {
        return "doctor";
    }

    //A function that lets the doctor view patient history by email.
    public List<MedicalRecord> viewPatientHistory(String patientEmail) {
        List<MedicalRecord> history = new ArrayList<>();

        /*This loop checks if the found medical records' patient email is the same as the entered patient email.
        If it is, it adds the record to an array of medical records which holds the patient's medical history.*/
        for (MedicalRecord record : patientRecords) {
            if (record.getPatientEmail().equals(patientEmail)) {
                history.add(record);
            }
        }
        if (history.isEmpty()) {
            System.out.println("📄 No medical records found for " + patientEmail);
        } else {
            System.out.println("\n=== Medical History for " + patientEmail + " ===");
            for (MedicalRecord record : history) {
                System.out.println("\nRecord #" + record.getRecordNumber());
                System.out.println("Conditions: " + record.getCondition());
                System.out.println("Prescriptions: " + record.getPrescription());
                System.out.println("Allergies: " + record.getAllergies());
                System.out.println("---");
            }
        }
        return history;
    }

    //Write a prescription for a patient. Writes prescription to an external text file.
    public void writePrescription(User patient, String doctorName, String medicinesInput) {
        try {
            List<Medicine> prescribedMedicines = new ArrayList<>();
            String[] medicines = medicinesInput.split(",");

            /*This loops through an array of strings, this array contains medicine names. Inside the body of the loop,
            a medicine object is created. This object searches for the medicine specified in the medicine array.
            If the medicine is valid, it adds and prescribes it to the patient, otherwise display an error message.*/
            for (String medName : medicines) {
                Medicine medicine = medicineHandler.searchMedicineByName(medName.trim());
                if (medicine != null) {
                    prescribedMedicines.add(medicine);
                } else {
                    System.out.println("⚠️ Warning: Medicine '" + medName.trim() + "' not found in inventory.");
                }
            }
            if (!prescribedMedicines.isEmpty()) {
                Prescription prescription = prescriptionManager.createPrescription(doctorName, patient.getEmail(), prescribedMedicines);
                System.out.println("✅ Prescription written successfully!");
                System.out.println("📄 Prescription ID: " + prescription.getPrescriptionId());
                System.out.println("👨‍⚕️ Doctor: " + prescription.getDoctorName());
                System.out.println("🧑‍🤝‍🧑 Patient: " + prescription.getPatientName());
                System.out.println("💊 Prescribed Medicines:");
                prescribedMedicines.forEach(med -> System.out.println("➡️ " + med.getName()));
            } else {
                System.out.println("❌ No valid medicines found to write prescription.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error writing prescription: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Add a medical record for a patient.
    public void addMedicalRecord(User patient, MedicalRecord record) {
        try {
            int recordNumber = patientRecords.size() + 1;
            record.setRecordNumber(recordNumber);
            record.setPatientEmail(patient.getEmail()); //Ensures the email is stored in each record.
            patientRecords.add(record);

            System.out.println("✅ Medical record added successfully!");
            System.out.println("📄 Record #" + recordNumber);
        } catch (Exception e) {
            System.out.println("❌ Error adding medical record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Consults a pharmacy by sending a query to a pharmacist.
    public void consultPharmacy(Pharmacist pharmacist, String query) {
        System.out.println("📝 Your consultation has been sent to the pharmacy.");
        System.out.println("👨‍⚕️ A pharmacist will review and respond to your query shortly.");
        consultations.add(query);
    }

    //Allows doctor to view consultations/queries sent to him.
    public void viewConsultations() {
        if (consultations.isEmpty()) {
            System.out.println("📄 No consultations available.");
        } else {
            System.out.println("\n=== Your Consultations ===");
            for (int i = 0; i < consultations.size(); i++) {
                System.out.println("\nConsultation #" + (i + 1));
                System.out.println(consultations.get(i));
            }
        }
    }

    //Allows the doctor to respond to consultations sent by patients.
    public void respondToConsultation(Customer patient, String response) {
        System.out.println("📝 Response sent to patient: " + patient.getEmail());
        System.out.println("Response: " + response);
    }
}
