package com.mycompany.pharmacy.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Medicine {
    private String id;
    private String name;
    private String manufacturer;
    private double price;
    private int stockQuantity;
    private String type;
    private boolean prescriptionRequired;

    public Medicine(String id, String name, String manufacturer, double price, int stockQuantity, String type) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.type = type;
        this.prescriptionRequired = !type.equalsIgnoreCase("OTC");

    }

    public Medicine() {}

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void applyDiscount(double percentage) {
        this.price -= this.price * (percentage / 100);
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Manufacturer: " + manufacturer +
                ", Price: $" + price + ", Stock: " + stockQuantity + ", Type: " + type);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Manufacturer: " + manufacturer +
                ", Price: $" + price +
                ", Stock: " + stockQuantity +
                ", Type: " + type;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getManufacturer() { return manufacturer; }
    public int getStockQuantity() { return stockQuantity; }
    public String getType() { return type; }

    public static List<Medicine> loadMedicinesFromFile() {
        List<Medicine> medicines = new ArrayList<>();
        String fileName = "src/main/java/com/mycompany/pharmacy/database/Drugs.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String id = data[0];
                    String name = data[1];
                    String manufacturer = data[2];
                    double price = Double.parseDouble(data[3]);
                    int stock = Integer.parseInt(data[4]);
                    String type = data[5];

                    Medicine med = new Medicine(id, name, manufacturer, price, stock, type);
                    medicines.add(med);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading medicine file: " + e.getMessage());
        }

        return medicines;
    }

    public boolean isPrescriptionRequired() { return prescriptionRequired; }

}
