package com.mycompany.pharmacy.handler;

import com.mycompany.pharmacy.model.Medicine;
import java.util.List;

public class MedicineHandler {
    private final List<Medicine> medicines;

    public MedicineHandler() {
        // Hardcoded file loading 🔥
        medicines = Medicine.loadMedicinesFromFile();
    }

    // Display all medicines
    public void displayAllMedicines() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines found.");
            return;
        }
        for (Medicine med : medicines) {
            med.displayInfo();
        }
    }

    // Search medicine by ID
    public Medicine searchMedicineById(String id) {
        for (Medicine med : medicines) {
            if (med.getId().equalsIgnoreCase(id)) {
                System.out.println("Medicine found:");
                med.displayInfo();
                return med;
            }
        }
        System.out.println("Medicine with ID " + id + " not found.");
        return null;
    }

    // Search medicine by Name
    public Medicine searchMedicineByName(String name) {
        for (Medicine med : medicines) {
            if (med.getName().equalsIgnoreCase(name)) {
                System.out.println("Medicine found:");
                med.displayInfo();
                return med;
            }
        }
        System.out.println("Medicine with Name '" + name + "' not found.");
        return null;
    }

    // Update stock for a medicine
    public void updateStock(String id, int quantity) {
        Medicine med = searchMedicineById(id);
        if (med != null) {
            med.updateStock(quantity);
            System.out.println("Stock updated. New stock: " + med.getStockQuantity());
        }
    }

    // Apply discount to a specific medicine
    public void applyDiscount(String id, double percentage) {
        Medicine med = searchMedicineById(id);
        if (med != null) {
            med.applyDiscount(percentage);
            System.out.println("Discount applied. New price: $" + med.getPrice());
        }
    }
}
