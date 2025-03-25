package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.handler.MedicineHandler;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final Cart cart = new Cart();
    private final List<Order> orderHistory = new ArrayList<>();

    public Customer(String email, String password) {
    }

    public Cart getCart() {
        return cart;
    }

    public void browseMedicines(MedicineHandler medicineHandler) {
        medicineHandler.displayAllMedicines();
    }

    public void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("❌ Cart is empty. Cannot place order.");
            return;
        }

        String orderId = "ORD" + System.currentTimeMillis();
        Order order = new Order(orderId, this);

        cart.getItems().forEach(cartItem -> order.addItem(cartItem.toOrderItem()));

        order.updateStatus("Confirmed");
        System.out.println("✅ Order Placed Successfully!");
        System.out.println(order);

        orderHistory.add(order);
        cart.clearCart();
    }

    public void viewOrderHistory() {
        System.out.println("\n=== Order History ===");
        if (orderHistory.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orderHistory) {
                System.out.println(order);
            }
        }
    }
}
