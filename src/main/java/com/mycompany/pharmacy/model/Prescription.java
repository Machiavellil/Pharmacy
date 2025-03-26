package com.mycompany.pharmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private static int counter = 1;
    private final int prescriptionId;
    private final String doctorName;
    private final String patientName;
    private final List<Medicine> prescribedMedicines;

    public Prescription(String doctorName, String patientName) {
        this.prescriptionId = counter++;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.prescribedMedicines = new ArrayList<>();
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

    public void displayPrescription() {
        System.out.println("\n📄 Prescription ID: " + prescriptionId);
        System.out.println("👨‍⚕️ Doctor: " + doctorName);
        System.out.println("🧑‍🤝‍🧑 Patient: " + patientName);
        System.out.println("💊 Prescribed Medicines:");
        prescribedMedicines.forEach(med -> System.out.println("➡️ " + med.getName()));
    }
}
