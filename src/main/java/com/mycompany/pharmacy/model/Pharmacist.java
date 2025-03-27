
package com.mycompany.pharmacy.model;
import com.mycompany.pharmacy.model.Medicine;
import com.mycompany.pharmacy.model.doctor;

import java.util.*;


public class Pharmacist extends User {
    private List<Medicine> managedMedicines;

    public Pharmacist(String email, String password) {
        super(email, password);
        this.managedMedicines = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        managedMedicines.add(medicine);
    }

    public void removeMedicine(String medicineId) {
        managedMedicines.removeIf(medicine -> medicine.getId().equals(medicineId));
    }

    public void updateMedicineDetails(String medicineId, Medicine updatedData) {
        for (int i = 0; i < managedMedicines.size(); i++) {
            if (managedMedicines.get(i).getId().equals(medicineId)) {
                managedMedicines.set(i, updatedData);
                break;
            }
        }
    }

    public void generateMedicineReport(String medicineName) {
        for (Medicine medicine : managedMedicines) {
            if (medicine.getName().equalsIgnoreCase(medicineName)) {
                System.out.println(medicine.toString());
                return;
            }
        }
        System.out.println("Medicine not found in inventory.");
    }

    public void respondToDoctorQuery(Doctor doctor, String response) {
        System.out.println("Response to Doctor " + doctor.getEmail() + ": " + response);
    }
}
  
}
