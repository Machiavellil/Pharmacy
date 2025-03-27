package com.mycompany.pharmacy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryAgent extends User {
    private final List<Order> assignedDeliveries = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public DeliveryAgent(String email, String password) {
        super(email, password);
    }

    public void viewAssignedDeliveries() {
        System.out.println("\n\ud83d\udce6 Assigned Deliveries:");
        if (assignedDeliveries.isEmpty()) {
            System.out.println("\u274c No deliveries assigned.");
        } else {
            for (Order order : assignedDeliveries) {
                System.out.println("Order ID: " + order.getOrderId() + " | Status: " + order.getStatus());
            }
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        for (Order order : assignedDeliveries) {
            if (order.getOrderId().equals("ORD" + orderId)) {
                order.updateStatus(status);
                System.out.println("\u2705 Order " + orderId + " status updated to: " + status);
                return;
            }
        }
        System.out.println("\u274c Order ID " + orderId + " not found in assigned deliveries.");
    }

    public void trackDeliveryLocation(int orderId) {
        for (Order order : assignedDeliveries) {
            if (order.getOrderId().equals("ORD" + orderId)) {
                System.out.println("\ud83d\udccd Tracking Order " + orderId + "...");
                System.out.println("\ud83d\ude97 Order is currently en route.");
                return;
            }
        }
        System.out.println("\u274c Order ID " + orderId + " not found in assigned deliveries.");
    }

    public void manageDeliverySchedule() {
        System.out.println("\n\ud83d\udcc5 Managing Delivery Schedule...");
        System.out.println("\u2705 Schedule updated successfully.");
    }

    public void assignDelivery(Order order) {
        assignedDeliveries.add(order);
        System.out.println("\u2705 Order " + order.getOrderId() + " assigned for delivery.");
    }

    public void estimatedDeliveryTime(int orderId, boolean isSuccessful, String failureReason) {
        for (Order order : assignedDeliveries) {
            if (order.getOrderId().equals("ORD" + orderId)) {
                if (isSuccessful) {
                    int estimatedTime = (int) (Math.random() * 30) + 10;
                    System.out.println("\u23f3 Estimated delivery time for Order " + orderId + " is " + estimatedTime + " minutes.");
                    order.updateStatus("Delivered");
                } else {
                    order.updateStatus("Failed Delivery");
                    System.out.println("\u274c Order " + orderId + " marked as 'Failed Delivery'. Reason: " + failureReason);
                }
                return;
            }
        }
        System.out.println("\u274c Order ID " + orderId + " not found in assigned deliveries.");
    }
}