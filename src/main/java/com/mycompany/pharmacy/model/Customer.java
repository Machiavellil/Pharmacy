package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.ui.MedicineSearchCLI;
import com.mycompany.pharmacy.handler.MedicineHandler;

public class Customer extends User {

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void browseMedicines(MedicineHandler handler) {
        System.out.println("🛒 Browsing available medicines...");
        MedicineSearchCLI searchCLI = new MedicineSearchCLI(handler);
        searchCLI.search();
    }
}
