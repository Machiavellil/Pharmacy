package com.mycompany.pharmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private static int counter = 1;
    private final int prescriptionId;
    private String doctorName;
    private String patientName;
    private final List<Medicine> prescribedMedicines;
    private String medicineName;
    private String dosage;

    public Prescription(String medicineName, String dosage) {
        this.prescriptionId = counter++;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.doctorName = "Dr. Smith"; // Default doctor name
        this.patientName = "Patient"; // Default patient name
        this.prescribedMedicines = new ArrayList<>();
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void addMedicine(Medicine medicine) {
        prescribedMedicines.add(medicine);
    }

    public boolean isMedicineAllowed(Medicine medicine) {
        return prescribedMedicines.contains(medicine);
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public List<Medicine> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void displayPrescription() {
        System.out.println("\n📄 Prescription ID: " + prescriptionId);
        System.out.println("👨‍⚕️ Doctor: " + doctorName);
        System.out.println("🧑‍🤝‍🧑 Patient: " + patientName);
        if (medicineName != null && dosage != null) {
            System.out.println("💊 Medicine: " + medicineName);
            System.out.println("📏 Dosage: " + dosage);
        } else {
            System.out.println("💊 Prescribed Medicines:");
            prescribedMedicines.forEach(med -> System.out.println("➡️ " + med.getName()));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("📄 Prescription ID: ").append(prescriptionId).append("\n");
        sb.append("👨‍⚕️ Doctor: ").append(doctorName).append("\n");
        sb.append("🧑‍🤝‍🧑 Patient: ").append(patientName).append("\n");
        if (medicineName != null && dosage != null) {
            sb.append("💊 Medicine: ").append(medicineName).append("\n");
            sb.append("📏 Dosage: ").append(dosage).append("\n");
        } else {
            sb.append("💊 Prescribed Medicines:\n");
            prescribedMedicines.forEach(med -> sb.append("➡️ ").append(med.getName()).append("\n"));
        }
        return sb.toString();
    }
}
