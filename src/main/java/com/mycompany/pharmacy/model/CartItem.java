package com.mycompany.pharmacy.model;

public class CartItem {
    private Medicine medicine;
    private int quantity;

    public CartItem(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem toOrderItem() {
        return new OrderItem(medicine.getName(), quantity, medicine.getPrice());
    }
}
