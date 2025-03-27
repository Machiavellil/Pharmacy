package com.mycompany.pharmacy;

import com.mycompany.pharmacy.ui.PharmacyCLI;
import java.io.IOException;

public class Pharmacy {
    public static void main(String[] args) throws IOException {
        PharmacyCLI cli = new PharmacyCLI();
        cli.start();
    }
}
