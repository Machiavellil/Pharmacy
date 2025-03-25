package com.mycompany.pharmacy.handler;

import com.mycompany.pharmacy.model.Medicine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineHandler {
    private final List<Medicine> medicines;

    public MedicineHandler() {
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
            saveMedicinesToFile(); // Save changes
        }
    }

    // Apply discount to a specific medicine
    public void applyDiscount(String id, double percentage) {
        Medicine med = searchMedicineById(id);
        if (med != null) {
            med.applyDiscount(percentage);
            System.out.println("Discount applied. New price: $" + med.getPrice());
            saveMedicinesToFile(); // Save changes
        }
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
        saveMedicinesToFile();
        System.out.println("Medicine added successfully.");
    }

    public void removeMedicine(String id) {
        Medicine med = searchMedicineById(id);
        if (med != null) {
            medicines.remove(med);
            saveMedicinesToFile();
            System.out.println("Medicine removed successfully.");
        }
    }

    private void saveMedicinesToFile() {

        String filePath = "src/main/java/com/mycompany/pharmacy/database/Drugs.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Medicine med : medicines) {
                String line = med.getId() + "," + med.getName() + "," + med.getManufacturer() + "," +
                        med.getPrice() + "," + med.getStockQuantity() + "," + med.getType();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving medicines to file: " + e.getMessage());
        }
    }

    public List<Medicine> getAllMedicines() {
        return medicines;
    }
}
