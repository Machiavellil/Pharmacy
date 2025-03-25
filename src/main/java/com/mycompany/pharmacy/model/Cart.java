package com.mycompany.pharmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(Medicine medicine, int quantity) {
        items.add(new CartItem(medicine, quantity));
        System.out.println("✅ Added " + quantity + " x " + medicine.getName() + " to cart.");
    }

    public void removeItem(Medicine medicine) {
        items.removeIf(item -> item.getMedicine().equals(medicine));
        System.out.println("❌ Removed " + medicine.getName() + " from cart.");
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(item -> item.getMedicine().getPrice() * item.getQuantity())
                .sum();
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("🛒 Cart is empty.");
            return;
        }
        System.out.println("\n🛒 Your Cart:");
        items.forEach(item -> System.out.println("➡️ " + item.getMedicine().getName() + " | Qty: " + item.getQuantity()));
        System.out.println("💰 Total: $" + calculateTotal());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}
