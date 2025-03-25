package com.mycompany.pharmacy.ui;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.mycompany.pharmacy.handler.MedicineHandler;
import com.mycompany.pharmacy.model.Medicine;

public class MedicineSearchCLI {

    private final MedicineHandler medicineHandler;

    public MedicineSearchCLI(MedicineHandler handler) {
        this.medicineHandler = handler;
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
                suggestions.forEach(m -> System.out.println("➡️ " + m.getName()));
            }
        }
    }
}
