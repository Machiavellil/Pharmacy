package com.mycompany.pharmacy.handler;

import com.mycompany.pharmacy.model.Medicine;
import com.mycompany.pharmacy.model.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionManager {
    private List<Prescription> prescriptions;
    private final PrescriptionPersistence persistence;
    private final MedicineHandler medicineHandler;

    public PrescriptionManager(MedicineHandler medicineHandler) {
        this.medicineHandler = medicineHandler;
        this.persistence = new PrescriptionPersistence(medicineHandler);
        this.prescriptions = persistence.loadPrescriptions();
    }

    public Prescription createPrescription(String doctorName, String patientName, List<Medicine> meds) {
        Prescription p = new Prescription(doctorName, patientName);
        meds.forEach(p::addMedicine);
        prescriptions.add(p);
        persistence.savePrescriptions(prescriptions);
        return p;
    }

    public Prescription getPrescriptionById(int id) {
        return prescriptions.stream()
                .filter(p -> p.getPrescriptionId() == id)
                .findFirst()
                .orElse(null);
    }

    public void showAllPrescriptions() {
        if (prescriptions.isEmpty()) {
            System.out.println("❌ No prescriptions available.");
            return;
        }
        prescriptions.forEach(Prescription::displayPrescription);
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void savePrescriptions() {
        persistence.savePrescriptions(prescriptions);
    }
}
