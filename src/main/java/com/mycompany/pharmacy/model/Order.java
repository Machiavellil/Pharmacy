package com.mycompany.pharmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private String status;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.status = "Pending";
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    // ✅ Added Getter for Order ID
    public String getOrderId() {
        return orderId;
    }

    // ✅ Added Getter for Status
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n")
                .append("Status: ").append(status).append("\n")
                .append("Items:\n");
        for (OrderItem item : items) {
            sb.append("  - ").append(item).append("\n");
        }
        sb.append("Total: $").append(calculateTotal()).append("\n");
        return sb.toString();
    }
}
