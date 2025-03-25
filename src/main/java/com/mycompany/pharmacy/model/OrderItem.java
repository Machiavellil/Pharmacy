package com.mycompany.pharmacy.model;

public class OrderItem {
    private String medicineName;
    private int quantity;
    private double price;

    public OrderItem(String medicineName, int quantity, double price) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return medicineName + " | Qty: " + quantity + " | Unit Price: $" + price + " | Total: $" + getTotalPrice();
    }
}
