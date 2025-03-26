package com.mycompany.pharmacy.ui;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.Customer;
import com.mycompany.pharmacy.model.Medicine;

public class MedicineSearchCLI {
    private final MedicineHandler medicineHandler;
    private final Customer customer;

    public MedicineSearchCLI(MedicineHandler handler, Customer customer) {
        this.medicineHandler = handler;
        this.customer = customer;
    }

    public void search() {
        Scanner scanner = new Scanner(System.in);
        List<Medicine> allMedicines = medicineHandler.getAllMedicines();

        while (true) {
            System.out.print("🔎 Start typing medicine name (or type 'exit'): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            List<Medicine> suggestions = allMedicines.stream()
                    .filter(m -> m.getName().toLowerCase().startsWith(input.toLowerCase()))
                    .collect(Collectors.toList());

            if (suggestions.isEmpty()) {
                System.out.println("❌ No suggestions found.");
            } else {
                System.out.println("💡 Suggestions:");
                for (int i = 0; i < suggestions.size(); i++) {
                    System.out.println((i + 1) + ". " + suggestions.get(i).getName() + " - $" + suggestions.get(i).getPrice() +
                            (suggestions.get(i).isPrescriptionRequired() ? " (Prescription Required)" : ""));
                }

                System.out.print("➡️ Enter number to add to cart or 0 to cancel: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice > 0 && choice <= suggestions.size()) {
                    Medicine selected = suggestions.get(choice - 1);

                    // ✅ Prescription Check
                    if (selected.isPrescriptionRequired() && !customer.hasValidPrescriptionFor(selected)) {
                        System.out.println("❌ You need a valid prescription to purchase " + selected.getName() + ".");
                        continue; // Skip adding to cart
                    }

                    System.out.print("➡️ Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    customer.getCart().addItem(selected, quantity);
                }
            }
        }
    }

}
