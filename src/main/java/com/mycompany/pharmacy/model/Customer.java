package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.handler.PrescriptionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User {
    private final Cart cart = new Cart();
    private final List<Order> orderHistory = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();

    public Customer(String email, String password) {
        super(email, password);
    }

    public void loadPrescriptions(PrescriptionManager prescriptionManager) {
        // Clear existing prescriptions
        prescriptions.clear();
        // Load all prescriptions
        List<Prescription> allPrescriptions = prescriptionManager.getPrescriptions();
        // Filter prescriptions for this customer
        allPrescriptions.stream()
                .filter(p -> p.getPatientName().toLowerCase().equals(this.getEmail().split("@")[0].toLowerCase()))
                .forEach(prescriptions::add);
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

        for (CartItem item : cart.getItems()) {
            Medicine med = item.getMedicine();
            if (med.isPrescriptionRequired() && !hasValidPrescriptionFor(med)) {
                System.out.println("🚫 Cannot place order. Medicine '" + med.getName() + "' requires a valid prescription.");
                return;
            }
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

    public void cancelOrder() {
        if (orderHistory.isEmpty()) {
            System.out.println("❌ No orders found to cancel.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Cancel Order ===");
        for (int i = 0; i < orderHistory.size(); i++) {
            System.out.println((i + 1) + ". " + orderHistory.get(i).getOrderId() + " - Status: " + orderHistory.get(i).getStatus());
        }

        System.out.print("➡️ Enter the number of the order you want to cancel (0 to exit): ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice > 0 && choice <= orderHistory.size()) {
            Order orderToCancel = orderHistory.get(choice - 1);
            if (orderToCancel.getStatus().equalsIgnoreCase("Cancelled")) {
                System.out.println("⚠️ Order is already cancelled.");
            } else {
                orderToCancel.updateStatus("Cancelled");
                System.out.println("✅ Order " + orderToCancel.getOrderId() + " has been cancelled.");
            }
        } else if (choice == 0) {
            System.out.println("❌ Cancel operation aborted.");
        } else {
            System.out.println("❌ Invalid choice.");
        }
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        System.out.println("✅ Prescription added successfully.");
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public boolean hasValidPrescriptionFor(Medicine selected) {
        return prescriptions.stream()
                .anyMatch(p -> p.isMedicineAllowed(selected));
    }

    public void viewPrescriptions() {
        if (prescriptions.isEmpty()) {
            System.out.println("📄 No prescriptions found.");
            return;
        }
        System.out.println("\n=== Your Prescriptions ===");
        prescriptions.forEach(Prescription::displayPrescription);
    }
}
