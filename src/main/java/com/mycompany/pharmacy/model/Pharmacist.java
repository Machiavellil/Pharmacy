package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.Medicine;
import com.mycompany.pharmacy.model.Doctor;

public class Pharmacist extends User {
    private MedicineHandler medicineHandler;

    public Pharmacist(String email, String password, MedicineHandler medicineHandler) {
        super(email, password);
        this.medicineHandler = medicineHandler;
    }

    public void addMedicine(Medicine medicine) {
        medicineHandler.addMedicine(medicine);
    }

    public void removeMedicine(String medicineId) {
        medicineHandler.removeMedicine(medicineId);
    }

    public void updateMedicineDetails(String medicineId, Medicine updatedData) {
        Medicine existingMedicine = medicineHandler.searchMedicineById(medicineId);
        if (existingMedicine != null) {
            medicineHandler.removeMedicine(medicineId); // Remove old entry
            medicineHandler.addMedicine(updatedData);   // Add updated entry
            System.out.println("Medicine details updated successfully.");
        } else {
            System.out.println("Medicine not found.");
        }
    }


    public void generateMedicineReport(String medicineName) {
        Medicine medicine = medicineHandler.searchMedicineByName(medicineName);
        if (medicine != null) {
            System.out.println(medicine.toString());
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    public void respondToDoctorQuery(Doctor doctor, String response) {
        System.out.println("Response to Doctor " + doctor.getEmail() + ": " + response);
    }
}
